
package com.cbs.ghgroup.model.ghcustomerdashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GhCustomerDashboard {

    @SerializedName("CustomerDashBoardsResult")
    @Expose
    private CustomerDashBoardsResult customerDashBoardsResult;

    public CustomerDashBoardsResult getCustomerDashBoardsResult() {
        return customerDashBoardsResult;
    }

    public void setCustomerDashBoardsResult(CustomerDashBoardsResult customerDashBoardsResult) {
        this.customerDashBoardsResult = customerDashBoardsResult;
    }

}
