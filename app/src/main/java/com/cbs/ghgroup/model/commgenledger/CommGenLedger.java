
package com.cbs.ghgroup.model.commgenledger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CommGenLedger {

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
