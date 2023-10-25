
package com.cbs.ghgroup.model.paymentregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRegister {

    @SerializedName("PaymentRegisterResult")
    @Expose
    private PaymentRegisterResult paymentRegisterResult;

    public PaymentRegisterResult getPaymentRegisterResult() {
        return paymentRegisterResult;
    }

    public void setPaymentRegisterResult(PaymentRegisterResult paymentRegisterResult) {
        this.paymentRegisterResult = paymentRegisterResult;
    }

}
