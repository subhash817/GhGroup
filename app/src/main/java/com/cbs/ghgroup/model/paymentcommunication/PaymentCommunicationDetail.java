
package com.cbs.ghgroup.model.paymentcommunication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentCommunicationDetail {

    @SerializedName("BillDate")
    @Expose
    private String billDate;
    @SerializedName("BillNO")
    @Expose
    private String billNO;
    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("DocNumber")
    @Expose
    private String docNumber;
    @SerializedName("ModeOfPayment")
    @Expose
    private String modeOfPayment;
    @SerializedName("Narration")
    @Expose
    private String narration;
    @SerializedName("PostingDate")
    @Expose
    private String postingDate;
    @SerializedName("TotalAmt")
    @Expose
    private String totalAmt;
    @SerializedName("VendorBillNo")
    @Expose
    private String vendorBillNo;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    private String CustomerName;


    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillNO() {
        return billNO;
    }

    public void setBillNO(String billNO) {
        this.billNO = billNO;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
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
