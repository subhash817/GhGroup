
package com.cbs.ghgroup.model.branchdeatils;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchListResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("branchDetail")
    @Expose
    private List<BranchDetail> branchDetail = null;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<BranchDetail> getBranchDetail() {
        return branchDetail;
    }

    public void setBranchDetail(List<BranchDetail> branchDetail) {
        this.branchDetail = branchDetail;
    }

}
