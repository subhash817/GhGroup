
package com.cbs.ghgroup.model.creditregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditRegisterDetail {

    @SerializedName("BalanceDue")
    @Expose
    private String balanceDue;
    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("DueDate")
    @Expose
    private String dueDate;
    @SerializedName("LRDate")
    @Expose
    private String lRDate;
    @SerializedName("LRNo")
    @Expose
    private String lRNo;
    @SerializedName("Narration")
    @Expose
    private String narration;
    @SerializedName("TotalAmt")
    @Expose
    private String totalAmt;
    @SerializedName("TransporterName")
    @Expose
    private String transporterName;
    @SerializedName("VoucherNumber")
    @Expose
    private String voucherNumber;

    public String getBalanceDue() {
        return balanceDue;
    }

    public void setBalanceDue(String balanceDue) {
        this.balanceDue = balanceDue;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getLRDate() {
        return lRDate;
    }

    public void setLRDate(String lRDate) {
        this.lRDate = lRDate;
    }

    public String getLRNo() {
        return lRNo;
    }

    public void setLRNo(String lRNo) {
        this.lRNo = lRNo;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

}
