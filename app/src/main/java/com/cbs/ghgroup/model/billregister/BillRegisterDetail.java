
package com.cbs.ghgroup.model.billregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillRegisterDetail {

    @SerializedName("BillAmt")
    @Expose
    private String billAmt;
    @SerializedName("BpCode")
    @Expose
    private String bpCode;
    @SerializedName("BpName")
    @Expose
    private String bpName;
    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("DocNumber")
    @Expose
    private String docNumber;
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
    @SerializedName("VendorBillNo")
    @Expose
    private String vendorBillNo;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;

    public String getBillAmt() {
        return billAmt;
    }

    public void setBillAmt(String billAmt) {
        this.billAmt = billAmt;
    }

    public String getBpCode() {
        return bpCode;
    }

    public void setBpCode(String bpCode) {
        this.bpCode = bpCode;
    }

    public String getBpName() {
        return bpName;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName;
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

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
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

}
