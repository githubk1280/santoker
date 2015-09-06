package com.xmlmapping;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by
 * on 15/8/26.
 * Description:
 */
public class Main {

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
//            object2Xml();//11536
            xml2Object();//12585
        }
//        object2Xml();
//        xml2Object();
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void object2Xml() {
        XStream xStream = new XStream();
        xStream.processAnnotations(new Class[]{PayRequest.class, Head.class, Body.class, RequestBody.class});
        RequestBody requestBody = new RequestBody();
        requestBody.setAcctNo("12323dfesfasfs");
        requestBody.setAcctName("test");
        requestBody.setAmount(new BigDecimal(12.45).setScale(2, BigDecimal.ROUND_HALF_UP));
        requestBody.setBankID("0603");
        requestBody.setBankName("长沙银行");
        requestBody.setCurrCode("01");
        requestBody.setFinaBranchID("1");
        requestBody.setFinaBranchName("理财");
        requestBody.setIfOnTime("0");
        requestBody.setPayType("01");
        PayRequest request = new PayRequest();
        Body body = new Body();
        body.setRequestBody(requestBody);
        request.setBody(body);
        Head head = new Head();
        head.setEncrypt(0);
        request.setHead(head);
        String xml = xStream.toXML(request);
        System.out.println(xml);
    }

    public static void xml2Object() throws IOException, ClassNotFoundException {
        String xml = "<Service>\n" +
                "  <Header>\n" +
                "    <requestType>0</requestType>\n" +
                "    <encrypt>0</encrypt>\n" +
                "  </Header>\n" +
                "  <Body>\n" +
                "    <Request>\n" +
                "      <finaBranchID>1</finaBranchID>\n" +
                "      <finaBranchName>理财</finaBranchName>\n" +
                "      <amount>12.45</amount>\n" +
                "      <bankName>长沙银行</bankName>\n" +
                "      <bankID>0603</bankID>\n" +
                "      <acctNo>12323dfesfasfs</acctNo>\n" +
                "      <acctName>test</acctName>\n" +
                "      <ifOnTime>0</ifOnTime>\n" +
                "      <currCode>01</currCode>\n" +
                "      <payType>01</payType>\n" +
                "    </Request>\n" +
                "  </Body>\n" +
                "</Service>";
        XStream xStream = new XStream();
        xStream.processAnnotations(new Class[]{PayRequest.class, Head.class, Body.class, RequestBody.class});
        PayRequest request = (PayRequest) xStream.fromXML(xml);
        System.out.println(request);
    }
}
