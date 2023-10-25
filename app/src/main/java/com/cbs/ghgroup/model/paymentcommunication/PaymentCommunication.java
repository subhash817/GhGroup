
package com.cbs.ghgroup.model.paymentcommunication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentCommunication {

    @SerializedName("PaymentCommunicationResult")
    @Expose
    private PaymentCommunicationResult paymentCommunicationResult;

    public PaymentCommunicationResult getPaymentCommunicationResult() {
        return paymentCommunicationResult;
    }

    public void setPaymentCommunicationResult(PaymentCommunicationResult paymentCommunicationResult) {
        this.paymentCommunicationResult = paymentCommunicationResult;
    }

}
