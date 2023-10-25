
package com.cbs.ghgroup.model.paymentregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentRegisterResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("PaymentRegisterDetail")
    @Expose
    private List<PaymentRegisterDetail> paymentRegisterDetail = null;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<PaymentRegisterDetail> getPaymentRegisterDetail() {
        return paymentRegisterDetail;
    }

    public void setPaymentRegisterDetail(List<PaymentRegisterDetail> paymentRegisterDetail) {
        this.paymentRegisterDetail = paymentRegisterDetail;
    }

}
