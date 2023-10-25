
package com.cbs.ghgroup.model.getotp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOtp {

    @SerializedName("OtpListResult")
    @Expose
    private OtpListResult otpListResult;

    public OtpListResult getOtpListResult() {
        return otpListResult;
    }

    public void setOtpListResult(OtpListResult otpListResult) {
        this.otpListResult = otpListResult;
    }

}
