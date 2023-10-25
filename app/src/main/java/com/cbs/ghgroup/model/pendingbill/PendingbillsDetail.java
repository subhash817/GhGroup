
package com.cbs.ghgroup.model.pendingbill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingbillsDetail {

    @SerializedName("Balance")
    @Expose
    private String balance;
    @SerializedName("BalanceDue")
    @Expose
    private String balanceDue;
    @SerializedName("BillAmt")
    @Expose
    private String billAmt;
    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("DocEntry")
    @Expose
    private String docEntry;
    @SerializedName("DueDate")
    @Expose
    private String dueDate;
    @SerializedName("LRDate")
    @Expose
    private String lRDate;
    @SerializedName("LRNo")
    @Expose
    private String lRNo;
    @SerializedName("NoOfParcels")
    @Expose
    private String noOfParcels;
    @SerializedName("TransporterName")
    @Expose
    private String transporterName;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("VoucherNumber")
    @Expose
    private String voucherNumber;
    @SerializedName("VoucherType")
    @Expose
    private String voucherType;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalanceDue() {
        return balanceDue;
    }

    public void setBalanceDue(String balanceDue) {
        this.balanceDue = balanceDue;
    }

    public String getBillAmt() {
        return billAmt;
    }

    public void setBillAmt(String billAmt) {
        this.billAmt = billAmt;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(String docEntry) {
        this.docEntry = docEntry;
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

    public String getNoOfParcels() {
        return noOfParcels;
    }

    public void setNoOfParcels(String noOfParcels) {
        this.noOfParcels = noOfParcels;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

}
