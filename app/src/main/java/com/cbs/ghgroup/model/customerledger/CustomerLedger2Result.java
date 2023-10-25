
package com.cbs.ghgroup.model.customerledger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerLedger2Result {

    @SerializedName("LedgerDetail")
    @Expose
    private List<LedgerDetail> ledgerDetail = null;
    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;

    public List<LedgerDetail> getLedgerDetail() {
        return ledgerDetail;
    }

    public void setLedgerDetail(List<LedgerDetail> ledgerDetail) {
        this.ledgerDetail = ledgerDetail;
    }

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

}
