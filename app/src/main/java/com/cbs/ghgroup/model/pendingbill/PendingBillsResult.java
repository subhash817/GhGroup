
package com.cbs.ghgroup.model.pendingbill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingBillsResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("PendingbillsDetail")
    @Expose
    private List<PendingbillsDetail> pendingbillsDetail = null;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<PendingbillsDetail> getPendingbillsDetail() {
        return pendingbillsDetail;
    }

    public void setPendingbillsDetail(List<PendingbillsDetail> pendingbillsDetail) {
        this.pendingbillsDetail = pendingbillsDetail;
    }

}
