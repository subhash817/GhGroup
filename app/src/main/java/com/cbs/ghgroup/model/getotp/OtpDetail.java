
package com.cbs.ghgroup.model.getotp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpDetail {

    @SerializedName("CODE")
    @Expose
    private String code;
    @SerializedName("ISCUSTOMER_OR_VENDOR")
    @Expose
    private String iscustomerOrVendor;
    @SerializedName("NAME")
    @Expose
    private String name;
    @SerializedName("OTP")
    @Expose
    private String otp;
    @SerializedName("Valid")
    @Expose
    private String valid;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIscustomerOrVendor() {
        return iscustomerOrVendor;
    }

    public void setIscustomerOrVendor(String iscustomerOrVendor) {
        this.iscustomerOrVendor = iscustomerOrVendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

}
