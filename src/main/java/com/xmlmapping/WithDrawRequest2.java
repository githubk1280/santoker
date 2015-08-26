package com.xmlmapping;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.math.BigDecimal;

/**
 * Created by
 * on 15/8/26.
 * Description:
 */
@XStreamAlias("request")
public class WithDrawRequest2 {
    /**
     * 理财平台号
     */
    private String finaBranchID;

    /**
     * 理财平台名称
     */
    private String finaBranchName;

    /**
     * 代付金额 2位小数
     */
    private BigDecimal amount;

    /**
     * 收款行
     */
    private String bankName;

    /**
     * 收款行号
     */
    private String bankID;

    /**
     * 收款账号
     */
    private String acctNo;

    /**
     * 收款户名
     */
    private String acctName;

    /**
     * 是否实时
     */
    private String ifOnTime;

    /**
     * 币种 01人民币
     */
    private String currCode;

    /**
     * 代付类型 默认填"0"：从理财平台对公结算户支付
     */
    private String payType;

    public String getFinaBranchID() {
        return finaBranchID;
    }

    public void setFinaBranchID(String finaBranchID) {
        this.finaBranchID = finaBranchID;
    }

    public String getFinaBranchName() {
        return finaBranchName;
    }

    public void setFinaBranchName(String finaBranchName) {
        this.finaBranchName = finaBranchName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getIfOnTime() {
        return ifOnTime;
    }

    public void setIfOnTime(String ifOnTime) {
        this.ifOnTime = ifOnTime;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "WithDrawRequest2{" +
                "finaBranchID='" + finaBranchID + '\'' +
                ", finaBranchName='" + finaBranchName + '\'' +
                ", amount=" + amount +
                ", bankName='" + bankName + '\'' +
                ", bankID='" + bankID + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", acctName='" + acctName + '\'' +
                ", ifOnTime='" + ifOnTime + '\'' +
                ", currCode='" + currCode + '\'' +
                ", payType='" + payType + '\'' +
                '}';
    }
}
