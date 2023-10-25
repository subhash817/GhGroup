
package com.cbs.ghgroup.model.getotp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpListResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("otpDetail")
    @Expose
    private List<OtpDetail> otpDetail = null;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<OtpDetail> getOtpDetail() {
        return otpDetail;
    }

    public void setOtpDetail(List<OtpDetail> otpDetail) {
        this.otpDetail = otpDetail;
    }

}
