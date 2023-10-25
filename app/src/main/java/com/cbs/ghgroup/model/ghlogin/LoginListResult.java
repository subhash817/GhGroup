
package com.cbs.ghgroup.model.ghlogin;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginListResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("loginDetail")
    @Expose
    private List<LoginDetail> loginDetail = null;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<LoginDetail> getLoginDetail() {
        return loginDetail;
    }

    public void setLoginDetail(List<LoginDetail> loginDetail) {
        this.loginDetail = loginDetail;
    }

}
