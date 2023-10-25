
package com.cbs.ghgroup.model.generaldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralReportDetail {

    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("Credit")
    @Expose
    private String credit;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Debit")
    @Expose
    private String debit;
    @SerializedName("LRDate")
    @Expose
    private String lRDate;
    @SerializedName("LRNo")
    @Expose
    private String lRNo;
    @SerializedName("NoOfParcels")
    @Expose
    private String noOfParcels;
    @SerializedName("OutStanding")
    @Expose
    private String outStanding;
    @SerializedName("TransporterName")
    @Expose
    private String transporterName;
    @SerializedName("VendorBillNo")
    @Expose
    private String vendorBillNo;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("VoucherNumber")
    @Expose
    private String voucherNumber;
    @SerializedName("VoucherType")
    @Expose
    private String voucherType;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
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

    public String getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(String outStanding) {
        this.outStanding = outStanding;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public String getVendorBillNo() {
        return vendorBillNo;
    }

    public void setVendorBillNo(String vendorBillNo) {
        this.vendorBillNo = vendorBillNo;
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
