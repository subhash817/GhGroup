
package com.cbs.ghgroup.model.purchesregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogMessage {

    @SerializedName("ErrorMsg")
    @Expose
    private Object errorMsg;
    @SerializedName("Success")
    @Expose
    private Boolean success;

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
