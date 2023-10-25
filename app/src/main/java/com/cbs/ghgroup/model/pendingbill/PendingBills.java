
package com.cbs.ghgroup.model.pendingbill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingBills {

    @SerializedName("PendingBillsResult")
    @Expose
    private PendingBillsResult pendingBillsResult;

    public PendingBillsResult getPendingBillsResult() {
        return pendingBillsResult;
    }

    public void setPendingBillsResult(PendingBillsResult pendingBillsResult) {
        this.pendingBillsResult = pendingBillsResult;
    }

}
