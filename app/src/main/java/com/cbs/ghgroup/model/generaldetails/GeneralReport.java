
package com.cbs.ghgroup.model.generaldetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralReport {

    @SerializedName("GeneralReportResult")
    @Expose
    private GeneralReportResult generalReportResult;

    public GeneralReportResult getGeneralReportResult() {
        return generalReportResult;
    }

    public void setGeneralReportResult(GeneralReportResult generalReportResult) {
        this.generalReportResult = generalReportResult;
    }

}
