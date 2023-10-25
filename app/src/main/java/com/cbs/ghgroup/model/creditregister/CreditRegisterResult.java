
package com.cbs.ghgroup.model.creditregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditRegisterResult {

    @SerializedName("CreditRegisterDetail")
    @Expose
    private List<CreditRegisterDetail> creditRegisterDetail = null;
    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;

    public List<CreditRegisterDetail> getCreditRegisterDetail() {
        return creditRegisterDetail;
    }

    public void setCreditRegisterDetail(List<CreditRegisterDetail> creditRegisterDetail) {
        this.creditRegisterDetail = creditRegisterDetail;
    }

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

}
