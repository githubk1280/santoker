package com.nio.learning.high;

import java.io.Serializable;

/**
 * Created by lefu on 15/8/12.
 * Description:
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 6200458369180805611L;


    private String msg;

    public Message(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
