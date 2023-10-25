
package com.cbs.ghgroup.model.ghlogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDetail {

    @SerializedName("CODE")
    @Expose
    private String code;
    @SerializedName("ISCUSTOMER_OR_VENDOR")
    @Expose
    private String iscustomerOrVendor;
    @SerializedName("IS_VALID")
    @Expose
    private String isValid;
    @SerializedName("MOBILENO")
    @Expose
    private String mobileno;
    @SerializedName("NAME")
    @Expose
    private String name;

    @SerializedName("LastUpdatedOn")
    @Expose
    private String LastUpdatedOn;

    public String getLastUpdatedOn() {
        return LastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        LastUpdatedOn = lastUpdatedOn;
    }

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

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
