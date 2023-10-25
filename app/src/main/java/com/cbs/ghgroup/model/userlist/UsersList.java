
package com.cbs.ghgroup.model.userlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersList {

    @SerializedName("GetUsersListResult")
    @Expose
    private GetUsersListResult getUsersListResult;

    public GetUsersListResult getGetUsersListResult() {
        return getUsersListResult;
    }

    public void setGetUsersListResult(GetUsersListResult getUsersListResult) {
        this.getUsersListResult = getUsersListResult;
    }

}
