
package com.cbs.ghgroup.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfileResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("ObjUserProfileDetail")
    @Expose
    private List<ObjUserProfileDetail> objUserProfileDetail;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<ObjUserProfileDetail> getObjUserProfileDetail() {
        return objUserProfileDetail;
    }

    public void setObjUserProfileDetail(List<ObjUserProfileDetail> objUserProfileDetail) {
        this.objUserProfileDetail = objUserProfileDetail;
    }

}
