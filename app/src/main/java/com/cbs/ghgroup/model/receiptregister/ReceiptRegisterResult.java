
package com.cbs.ghgroup.model.receiptregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ReceiptRegisterResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("ReceiptDetail")
    @Expose
    private List<ReceiptDetail> receiptDetail = null;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<ReceiptDetail> getReceiptDetail() {
        return receiptDetail;
    }

    public void setReceiptDetail(List<ReceiptDetail> receiptDetail) {
        this.receiptDetail = receiptDetail;
    }

}
