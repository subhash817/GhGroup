
package com.cbs.ghgroup.model.ageingpiechart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AgeingDetailsResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("ObjAgeingDetail")
    @Expose
    private List<ObjAgeingDetail> objAgeingDetail;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<ObjAgeingDetail> getObjAgeingDetail() {
        return objAgeingDetail;
    }

    public void setObjAgeingDetail(List<ObjAgeingDetail> objAgeingDetail) {
        this.objAgeingDetail = objAgeingDetail;
    }

}
