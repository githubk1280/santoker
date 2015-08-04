package com.jsch.ssh.main;


import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.jcraft.jsch.*;
import com.wacai.simplefiler.RemoteName;
import com.wacai.simplefiler.SimpleFiler;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Test {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(Test.class);
    String user = "appweb";
    String password = "pay.wacai.com";
    String host = "192.168.3.171";
    //    String user = "appweb";
//    String password = "10@nl.zeza";
//    String host = "192.168.10.61";
    int port = 22;

    //        String dir = "/data/program/wac-payment-web/logs";
    //    String suffix = ".log";
    String dir = "/tmp";
    //    String dir = "/data/program/wacpay_agreement_pdf/wacpay/";
    String suffix = ".pdf";

    Session session = null;
    ChannelSftp sftpChannel = null;
    ChannelExec execChannel = null;

    ThreadLocal<ConcurrentHashMap> global = new ThreadLocal<ConcurrentHashMap>(){

        @Override
        protected ConcurrentHashMap initialValue() {
            return super.initialValue();
        }
    };

    ExecutorService es = null;

    class ValuePair {
        private final String dir;
        private final List<String> paths;

        public ValuePair(String dir, List<String> paths) {
            this.dir = dir;
            this.paths = paths;
        }

        public String getDir() {
            return dir;
        }

        public List<String> getPaths() {
            return paths;
        }
    }

    public static void main(String[] args) throws SftpException, FileNotFoundException, JSchException {
//        String user = "appweb";
//        String password = "10@nl.zeza";
//        String host = "192.168.10.61";
        Test t = new Test();
//        t.runExec(t);
        t.createThreadPool();
        t.runSftp(t);
        t.close();
        t.shutdownThreadPool();
    }

    private void shutdownThreadPool() {
        es.shutdown();
    }

    private void createThreadPool() {
        es = Executors.newFixedThreadPool(10);
    }

    public void runExec(Test ssh) throws JSchException, SftpException {
        ssh.initExec();
        ssh.initSftp();
        StopWatch stopWatch = new StopWatch("download");
        stopWatch.start();
        this.execChannel.setCommand("cd /data/program/wacpay_agreement_pdf/wacpay/  && ls -t >> /tmp/out.txt");
        this.execChannel.connect();
        ssh.close();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");
    }

    public void runSftp(final Test ssh) throws SftpException, FileNotFoundException {
        ssh.initSftp();
        StopWatch stopWatch = new StopWatch("download");
        stopWatch.start();
        ValuePair paths = ssh.getFilePath();
//        ssh.readFileStream(paths.getDir(), paths.getPaths().get(0));
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");
        stopWatch.start();
        final String dirPath = paths.getDir();
        int i = 0;
        List<Future> futures = Lists.newArrayList();
        for (final String path : paths.getPaths()) {
//            ssh.readFileAndUpload(paths.getDir(), path);
            if (i++ == 100) {
                i = 0;
                try {
                    Thread.currentThread().sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            futures.add(es.submit(new Runnable() {
                @Override
                public void run() {
                    ssh.readFileStream(dirPath, path);
                }
            }));
        }
        for (Future f : futures) {
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        stopWatch.stop();
        System.out.println("end " + stopWatch.getTotalTimeMillis() + "ms");
    }

    public void init(String type) {
        if ("sftp".equals(type)) {
            initSftp();
        }
    }

    public void initExec() {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("===============================Establishing Connection...");
            session.connect();
            System.out.println("===============================Connection established.");
            System.out.println("===============================Crating EXEC Channel.");
            execChannel = (ChannelExec) session.openChannel("exec");

            System.out.println("===============================EXEC Channel created.");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void initSftp() {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("===============================Establishing Connection...");
            session.connect();
            System.out.println("===============================Connection established.");
            System.out.println("===============================Crating SFTP Channel.");
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            System.out.println("===============================SFTP Channel created.");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (null != sftpChannel && !sftpChannel.isClosed()) {
            sftpChannel.disconnect();
        }
        if (null != execChannel && !execChannel.isClosed()) {
            execChannel.disconnect();
        }
        if (null != session && session.isConnected()) {
            session.disconnect();
        }
    }

    public ValuePair getFilePath() throws SftpException {
        final List<String> files = new ArrayList<String>();
        sftpChannel.cd(dir);
        sftpChannel.ls(".", new ChannelSftp.LsEntrySelector() {

            @Override
            public int select(ChannelSftp.LsEntry entry) {
                System.out.println(entry.getFilename() + "--" + entry.getAttrs().getAtimeString() + "--" + entry.getAttrs().getMtimeString());
                if (entry.getFilename().endsWith(suffix)) {
                    files.add(entry.getFilename());
                }
                return 0;
            }
        });
        System.out.println("==============all pdfs :");
        System.out.println(files);
        return new ValuePair(dir, files);
    }

    public void readFileAndUpload(String dir, String path) throws SftpException {
        System.out.println("=================read file " + path);
        ByteArrayOutputStream out = null;
        try {
            InputStream remoteFileStream = this.sftpChannel.get(dir + "/" + path);
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = remoteFileStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            System.out.println(new String(out.toByteArray()));
            SimpleFiler simpleFiler = new SimpleFiler(new InetSocketAddress[]{InetSocketAddress.createUnresolved("192.168.1.252", 22122)});
            simpleFiler.start();
            Optional<RemoteName> fileInfo = simpleFiler.upload(out.toByteArray());
            if (fileInfo.isPresent()) {
                System.out.println(fileInfo.get().group);
                System.out.println(fileInfo.get().name);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readFileStream(String dir, String path) {
        System.out.println("=================read file " + path);
        OutputStream out = null;
        try {
            InputStream remoteFileStream = this.sftpChannel.get(dir + "/" + path);
            out = new FileOutputStream(new File("D:\\pdfs\\" + path));
            byte[] buffer = new byte[100 * 1024];
            int read = 0;
            while ((read = remoteFileStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readFile(String dir, String path) throws SftpException {
        path = "tomcat_access.2015-05-28.log";
        OutputStream out = null;
        try {
            InputStream remoteFileStream = this.sftpChannel.get(dir + "/" + path);
            System.out.println(IOUtils.toString(remoteFileStream));
            out = new FileOutputStream(new File("D:\\" + path));
            IOUtils.copy(remoteFileStream, out);
            this.sftpChannel.get(dir + "/" + path, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void read(String host, String user, String password, String filePath) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");
            InputStream out = null;
            out = sftpChannel.get(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(out));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
            sftpChannel.disconnect();
            session.disconnect();
        } catch (JSchException | SftpException | IOException e) {
            System.out.println(e);
        }
    }
}
