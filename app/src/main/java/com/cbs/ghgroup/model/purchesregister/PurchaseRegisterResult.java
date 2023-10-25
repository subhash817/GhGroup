
package com.cbs.ghgroup.model.purchesregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PurchaseRegisterResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("RegisterDetail")
    @Expose
    private List<RegisterDetail> registerDetail = null;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<RegisterDetail> getRegisterDetail() {
        return registerDetail;
    }

    public void setRegisterDetail(List<RegisterDetail> registerDetail) {
        this.registerDetail = registerDetail;
    }

}
