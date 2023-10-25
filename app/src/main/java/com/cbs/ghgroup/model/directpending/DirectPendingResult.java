
package com.cbs.ghgroup.model.directpending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectPendingResult {

    @SerializedName("DirectPendingDetail")
    @Expose
    private List<DirectPendingDetail> directPendingDetail = null;
    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;

    public List<DirectPendingDetail> getDirectPendingDetail() {
        return directPendingDetail;
    }

    public void setDirectPendingDetail(List<DirectPendingDetail> directPendingDetail) {
        this.directPendingDetail = directPendingDetail;
    }

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

}
