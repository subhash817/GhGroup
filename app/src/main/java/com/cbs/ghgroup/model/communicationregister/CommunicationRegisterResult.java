
package com.cbs.ghgroup.model.communicationregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommunicationRegisterResult {

    @SerializedName("CommunicationRegisterDetail")
    @Expose
    private List<CommunicationRegisterDetail> communicationRegisterDetail = null;
    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;

    public List<CommunicationRegisterDetail> getCommunicationRegisterDetail() {
        return communicationRegisterDetail;
    }

    public void setCommunicationRegisterDetail(List<CommunicationRegisterDetail> communicationRegisterDetail) {
        this.communicationRegisterDetail = communicationRegisterDetail;
    }

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

}
