
package com.cbs.ghgroup.model.billregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BillRegisterResult {

    @SerializedName("BillRegisterDetail")
    @Expose
    private List<BillRegisterDetail> billRegisterDetail = null;
    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;

    public List<BillRegisterDetail> getBillRegisterDetail() {
        return billRegisterDetail;
    }

    public void setBillRegisterDetail(List<BillRegisterDetail> billRegisterDetail) {
        this.billRegisterDetail = billRegisterDetail;
    }

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

}
