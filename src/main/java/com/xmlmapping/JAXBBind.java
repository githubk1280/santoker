package com.xmlmapping;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;

/**
 * Created by
 * on 15/8/26.
 * Description:
 */
public class JAXBBind {

    public static void main(String args[]) throws JAXBException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
//            object2Xml();//53828
            xml2Object();//56427
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void xml2Object() throws JAXBException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><request><AcctName>test</AcctName><AcctNo>12323dfesfasfs</AcctNo><Amount>12.45</Amount><BankID>0603</BankID><BankName>长沙银行</BankName><CurrCode>01</CurrCode><FinaBranchID>1</FinaBranchID><FinaBranchName>理财</FinaBranchName><IfOnTime>0</IfOnTime><PayType>01</PayType></request>";
        JAXBContext context = JAXBContext.newInstance(WithDrawRequest.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        WithDrawRequest request = (WithDrawRequest) unmarshaller.unmarshal(new StringReader(xml));
//        System.out.println(request);
    }

    public static void object2Xml() throws JAXBException {
        WithDrawRequest request = new WithDrawRequest();
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

        JAXBContext context = JAXBContext.newInstance(WithDrawRequest.class);
        Marshaller marshaller = context.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(request, writer);
    }
}
