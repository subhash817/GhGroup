
package com.cbs.ghgroup.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("UserProfileResult")
    @Expose
    private UserProfileResult userProfileResult;

    public UserProfileResult getUserProfileResult() {
        return userProfileResult;
    }

    public void setUserProfileResult(UserProfileResult userProfileResult) {
        this.userProfileResult = userProfileResult;
    }

}
