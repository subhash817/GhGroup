
package com.cbs.ghgroup.model.receiptregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiptDetail {

    @SerializedName("BankAccountName")
    @Expose
    private String bankAccountName;
    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("ChequeRefDate")
    @Expose
    private String chequeRefDate;
    @SerializedName("ChequeRefNo")
    @Expose
    private String chequeRefNo;
    @SerializedName("DocNumber")
    @Expose
    private String docNumber;
    @SerializedName("InstDate")
    @Expose
    private String instDate;
    @SerializedName("InstNo")
    @Expose
    private String instNo;
    @SerializedName("ModeOfPayment")
    @Expose
    private String modeOfPayment;
    @SerializedName("SeriesName")
    @Expose
    private String seriesName;
    @SerializedName("TotalAmount")
    @Expose
    private String totalAmount;

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getChequeRefDate() {
        return chequeRefDate;
    }

    public void setChequeRefDate(String chequeRefDate) {
        this.chequeRefDate = chequeRefDate;
    }

    public String getChequeRefNo() {
        return chequeRefNo;
    }

    public void setChequeRefNo(String chequeRefNo) {
        this.chequeRefNo = chequeRefNo;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getInstDate() {
        return instDate;
    }

    public void setInstDate(String instDate) {
        this.instDate = instDate;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

}
