
package com.cbs.ghgroup.model.grcommunication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GRCommunication {

    @SerializedName("CommunicationRegisterResult")
    @Expose
    private CommunicationRegisterResult communicationRegisterResult;

    public CommunicationRegisterResult getCommunicationRegisterResult() {
        return communicationRegisterResult;
    }

    public void setCommunicationRegisterResult(CommunicationRegisterResult communicationRegisterResult) {
        this.communicationRegisterResult = communicationRegisterResult;
    }

}
