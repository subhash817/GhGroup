
package com.cbs.ghgroup.model.ghlogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GhLogin {

    @SerializedName("loginListResult")
    @Expose
    private LoginListResult loginListResult;

    public LoginListResult getLoginListResult() {
        return loginListResult;
    }

    public void setLoginListResult(LoginListResult loginListResult) {
        this.loginListResult = loginListResult;
    }

}
