
package com.cbs.ghgroup.model.ghcustomerdashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjGHBar {

    @SerializedName("CreditNote")
    @Expose
    private String creditNote;
    @SerializedName("Payment")
    @Expose
    private String payment;
    @SerializedName("Purchase")
    @Expose
    private String purchase;

    public String getCreditNote() {
        return creditNote;
    }

    public void setCreditNote(String creditNote) {
        this.creditNote = creditNote;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

}
