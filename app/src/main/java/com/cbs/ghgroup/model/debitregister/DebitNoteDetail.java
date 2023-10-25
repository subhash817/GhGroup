
package com.cbs.ghgroup.model.debitregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DebitNoteDetail {

    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("LRNo")
    @Expose
    private String lRNo;
    @SerializedName("LrDate")
    @Expose
    private String lrDate;
    @SerializedName("TotalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("TransporterName")
    @Expose
    private String transporterName;
    @SerializedName("VchrNo")
    @Expose
    private String vchrNo;

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

    public String getLRNo() {
        return lRNo;
    }

    public void setLRNo(String lRNo) {
        this.lRNo = lRNo;
    }

    public String getLrDate() {
        return lrDate;
    }

    public void setLrDate(String lrDate) {
        this.lrDate = lrDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public String getVchrNo() {
        return vchrNo;
    }

    public void setVchrNo(String vchrNo) {
        this.vchrNo = vchrNo;
    }

}
