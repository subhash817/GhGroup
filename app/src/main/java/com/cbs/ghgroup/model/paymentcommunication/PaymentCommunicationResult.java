
package com.cbs.ghgroup.model.paymentcommunication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentCommunicationResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("PaymentCommunicationDetail")
    @Expose
    private List<PaymentCommunicationDetail> paymentCommunicationDetail = null;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<PaymentCommunicationDetail> getPaymentCommunicationDetail() {
        return paymentCommunicationDetail;
    }

    public void setPaymentCommunicationDetail(List<PaymentCommunicationDetail> paymentCommunicationDetail) {
        this.paymentCommunicationDetail = paymentCommunicationDetail;
    }

}
