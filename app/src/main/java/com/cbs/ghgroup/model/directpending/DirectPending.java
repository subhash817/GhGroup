
package com.cbs.ghgroup.model.directpending;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DirectPending {

    @SerializedName("DirectPendingResult")
    @Expose
    private DirectPendingResult directPendingResult;

    public DirectPendingResult getDirectPendingResult() {
        return directPendingResult;
    }

    public void setDirectPendingResult(DirectPendingResult directPendingResult) {
        this.directPendingResult = directPendingResult;
    }

}
