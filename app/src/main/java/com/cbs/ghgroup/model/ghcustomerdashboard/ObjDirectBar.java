
package com.cbs.ghgroup.model.ghcustomerdashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjDirectBar {

    @SerializedName("Billing")
    @Expose
    private String billing;
    @SerializedName("GR")
    @Expose
    private String gr;
    @SerializedName("Payment")
    @Expose
    private String payment;

    public String getBilling() {
        return billing;
    }

    public void setBilling(String billing) {
        this.billing = billing;
    }

    public String getGr() {
        return gr;
    }

    public void setGr(String gr) {
        this.gr = gr;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

}
