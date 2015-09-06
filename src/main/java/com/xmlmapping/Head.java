package com.xmlmapping;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by
 * on 15/8/28.
 * Description:
 */
@XStreamAlias("Head")
public class Head {
    private String serviceCode;

    private String channelId;

    private String version;

    private String requestTime;

    private String externalReference;

    private String requestBrancheCode;

    private String requestOperatorId;

    private String requestOeratorType;

    private String bankNoterBoxID;

    private String authorizerID;

    private String tradeDate;

    private String termType;

    private String termNo;

    private int requestType;

    private int encrypt;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getRequestBrancheCode() {
        return requestBrancheCode;
    }

    public void setRequestBrancheCode(String requestBrancheCode) {
        this.requestBrancheCode = requestBrancheCode;
    }

    public String getRequestOperatorId() {
        return requestOperatorId;
    }

    public void setRequestOperatorId(String requestOperatorId) {
        this.requestOperatorId = requestOperatorId;
    }

    public String getRequestOeratorType() {
        return requestOeratorType;
    }

    public void setRequestOeratorType(String requestOeratorType) {
        this.requestOeratorType = requestOeratorType;
    }

    public String getBankNoterBoxID() {
        return bankNoterBoxID;
    }

    public void setBankNoterBoxID(String bankNoterBoxID) {
        this.bankNoterBoxID = bankNoterBoxID;
    }

    public String getAuthorizerID() {
        return authorizerID;
    }

    public void setAuthorizerID(String authorizerID) {
        this.authorizerID = authorizerID;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getTermNo() {
        return termNo;
    }

    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public int getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(int encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public String toString() {
        return "Head{" +
                "serviceCode='" + serviceCode + '\'' +
                ", channelId='" + channelId + '\'' +
                ", version='" + version + '\'' +
                ", requestTime='" + requestTime + '\'' +
                ", externalReference='" + externalReference + '\'' +
                ", requestBrancheCode='" + requestBrancheCode + '\'' +
                ", requestOperatorId='" + requestOperatorId + '\'' +
                ", requestOeratorType='" + requestOeratorType + '\'' +
                ", bankNoterBoxID='" + bankNoterBoxID + '\'' +
                ", authorizerID='" + authorizerID + '\'' +
                ", tradeDate='" + tradeDate + '\'' +
                ", termType='" + termType + '\'' +
                ", termNo='" + termNo + '\'' +
                ", requestType=" + requestType +
                ", encrypt=" + encrypt +
                '}';
    }
}
