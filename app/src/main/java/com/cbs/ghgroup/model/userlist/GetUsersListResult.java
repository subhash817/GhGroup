
package com.cbs.ghgroup.model.userlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUsersListResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("UsersDetails")
    @Expose
    private List<UsersDetail> usersDetails = null;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<UsersDetail> getUsersDetails() {
        return usersDetails;
    }

    public void setUsersDetails(List<UsersDetail> usersDetails) {
        this.usersDetails = usersDetails;
    }

}
