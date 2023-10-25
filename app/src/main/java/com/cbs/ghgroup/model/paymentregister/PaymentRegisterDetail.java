
package com.cbs.ghgroup.model.paymentregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRegisterDetail {

    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("DocNumber")
    @Expose
    private String docNumber;
    @SerializedName("InstrumentDate")
    @Expose
    private String instrumentDate;
    @SerializedName("InstrumentNo")
    @Expose
    private String instrumentNo;
    @SerializedName("ModeOfPayment")
    @Expose
    private String modeOfPayment;
    @SerializedName("Narration")
    @Expose
    private String narration;
    @SerializedName("RefBills")
    @Expose
    private String refBills;
    @SerializedName("TotalAmt")
    @Expose
    private String totalAmt;

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

    public String getInstrumentDate() {
        return instrumentDate;
    }

    public void setInstrumentDate(String instrumentDate) {
        this.instrumentDate = instrumentDate;
    }

    public String getInstrumentNo() {
        return instrumentNo;
    }

    public void setInstrumentNo(String instrumentNo) {
        this.instrumentNo = instrumentNo;
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

    public String getRefBills() {
        return refBills;
    }

    public void setRefBills(String refBills) {
        this.refBills = refBills;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

}
