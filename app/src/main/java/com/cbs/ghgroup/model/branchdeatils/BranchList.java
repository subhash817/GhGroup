
package com.cbs.ghgroup.model.branchdeatils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchList {

    @SerializedName("BranchListResult")
    @Expose
    private BranchListResult branchListResult;

    public BranchListResult getBranchListResult() {
        return branchListResult;
    }

    public void setBranchListResult(BranchListResult branchListResult) {
        this.branchListResult = branchListResult;
    }

}
