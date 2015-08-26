package com.xmlmapping;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by
 * on 15/8/26.
 * Description:
 */
public class XStreamMain {

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
//            object2Xml();//11536
            xml2Object();//12585
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void object2Xml() {
        XStream xStream = new XStream();
        xStream.processAnnotations(WithDrawRequest2.class);
        WithDrawRequest2 request = new WithDrawRequest2();
        request.setAcctNo("12323dfesfasfs");
        request.setAcctName("test");
        request.setAmount(new BigDecimal(12.45).setScale(2, BigDecimal.ROUND_HALF_UP));
        request.setBankID("0603");
        request.setBankName("长沙银行");
        request.setCurrCode("01");
        request.setFinaBranchID("1");
        request.setFinaBranchName("理财");
        request.setIfOnTime("0");
        request.setPayType("01");
        String xml = xStream.toXML(request);
//        System.out.println(xml);
    }

    public static void xml2Object() throws IOException, ClassNotFoundException {
        String xml = "<request><finaBranchID>1</finaBranchID><finaBranchName>理财</finaBranchName><amount>12.45</amount><bankName>长沙银行</bankName><bankID>0603</bankID><acctNo>12323dfesfasfs</acctNo><acctName>test</acctName><ifOnTime>0</ifOnTime><currCode>01</currCode><payType>01</payType></request>";
        XStream xStream = new XStream();
        xStream.processAnnotations(WithDrawRequest2.class);
        WithDrawRequest2 request = (WithDrawRequest2) xStream.fromXML(xml);
//        System.out.println(request);
    }
}
