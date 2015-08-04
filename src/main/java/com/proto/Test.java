package com.proto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by lefu on 2015/5/25.
 */
public class Test {

    public static void main(String[] args) throws IOException {

        // ���ն�������ݽṹ������һ��Person
        PersonOuterClass.Person.Builder personBuilder = PersonOuterClass.Person.newBuilder();
        personBuilder.setId(1);
        personBuilder.setEmail("xxg@163.com");
        personBuilder.addFriends("Friend A");
        personBuilder.addFriends("Friend B");
        PersonOuterClass.Person xxg = personBuilder.build();

        // ������д�����������������������������ByteArrayOutputStream������
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        xxg.writeTo(output);

        // -------------- �ָ��ߣ������Ƿ��ͷ������������л����� ---------------

        byte[] byteArray = output.toByteArray();

        // -------------- �ָ��ߣ������ǽ��շ��������ݽ��պ����л� ---------------

        // ���յ�������ȡ����������������������ByteArrayInputStream������
        ByteArrayInputStream input = new ByteArrayInputStream(byteArray);

        // �����л�
        PersonOuterClass.Person xxg2 = PersonOuterClass.Person.parseFrom(input);
        System.out.println("ID:" + xxg2.getId());
        System.out.println("email:" + xxg2.getEmail());
        System.out.println("friend:");
        List<String> friends = xxg2.getFriendsList();
        for (String friend : friends) {
            System.out.println(friend);
        }

    }
}
