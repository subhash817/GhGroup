
package com.cbs.ghgroup.model.userlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersDetail {

    @SerializedName("UserCode")
    @Expose
    private String userCode;
    @SerializedName("UserName")
    @Expose
    private String userName;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
