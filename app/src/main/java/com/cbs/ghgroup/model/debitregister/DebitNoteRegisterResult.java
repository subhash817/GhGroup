
package com.cbs.ghgroup.model.debitregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DebitNoteRegisterResult {

    @SerializedName("DebitNoteDetail")
    @Expose
    private List<DebitNoteDetail> debitNoteDetail = null;
    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;

    public List<DebitNoteDetail> getDebitNoteDetail() {
        return debitNoteDetail;
    }

    public void setDebitNoteDetail(List<DebitNoteDetail> debitNoteDetail) {
        this.debitNoteDetail = debitNoteDetail;
    }

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

}
