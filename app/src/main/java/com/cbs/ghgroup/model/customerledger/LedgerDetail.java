
package com.cbs.ghgroup.model.customerledger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LedgerDetail {
        @SerializedName("ErrorMsg")
        @Expose
        private Object errorMsg;
        @SerializedName("Success")
        @Expose
        private Boolean success;

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("Credit")
    @Expose
    private Object credit;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Debit")
    @Expose
    private Object debit;
    @SerializedName("DocEntry")
    @Expose
    private String docEntry;
    @SerializedName("Flag")
    @Expose
    private String flag;
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
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("VoucherNumber")
    @Expose
    private String voucherNumber;
    @SerializedName("VoucherType")
    @Expose
    private String voucherType;



    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Object getCredit() {
        return credit;
    }

    public void setCredit(Object credit) {
        this.credit = credit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getDebit() {
        return debit;
    }

    public void setDebit(Object debit) {
        this.debit = debit;
    }

    public String getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(String docEntry) {
        this.docEntry = docEntry;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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
