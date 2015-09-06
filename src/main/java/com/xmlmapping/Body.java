package com.xmlmapping;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by
 * on 15/8/28.
 * Description:
 */
@XStreamAlias("Body")
public class Body {
    @XStreamAlias("Request")
    private RequestBody requestBody;

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        return "Body{" +
                "requestBody=" + requestBody +
                '}';
    }
}
