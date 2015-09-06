package com.xmlmapping;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by
 * on 15/8/28.
 * Description:
 */
@XStreamAlias("Service")
public class PayRequest {

    @XStreamAlias("Header")
    private Head head;

    @XStreamAlias("Body")
    private Body body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "PayRequest{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
