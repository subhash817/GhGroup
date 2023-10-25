
package com.cbs.ghgroup.model.generaldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeneralReportResult {

    @SerializedName("GeneralReportDetail")
    @Expose
    private List<GeneralReportDetail> generalReportDetail = null;
    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;

    public List<GeneralReportDetail> getGeneralReportDetail() {
        return generalReportDetail;
    }

    public void setGeneralReportDetail(List<GeneralReportDetail> generalReportDetail) {
        this.generalReportDetail = generalReportDetail;
    }

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

}
