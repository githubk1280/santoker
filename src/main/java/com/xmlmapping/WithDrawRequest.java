package com.xmlmapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * Created by
 * on 15/8/26.
 * Description:
 */
@XmlRootElement(name = "request")
public class WithDrawRequest {
    /**
     * 理财平台号 0603
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

    @XmlElement(name = "FinaBranchID")
    public String getFinaBranchID() {
        return finaBranchID;
    }

    public void setFinaBranchID(String finaBranchID) {
        this.finaBranchID = finaBranchID;
    }

    @XmlElement(name = "FinaBranchName")
    public String getFinaBranchName() {
        return finaBranchName;
    }

    public void setFinaBranchName(String finaBranchName) {
        this.finaBranchName = finaBranchName;
    }

    @XmlElement(name = "Amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @XmlElement(name = "BankName")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @XmlElement(name = "BankID")
    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    @XmlElement(name = "AcctNo")
    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    @XmlElement(name = "AcctName")
    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    @XmlElement(name = "IfOnTime")
    public String getIfOnTime() {
        return ifOnTime;
    }

    public void setIfOnTime(String ifOnTime) {
        this.ifOnTime = ifOnTime;
    }

    @XmlElement(name = "CurrCode")
    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    @XmlElement(name = "PayType")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "WithDrawRequest{" +
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
