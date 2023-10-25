
package com.cbs.ghgroup.model.branchdeatils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchDetail {

    @SerializedName("BRANCHCODE")
    @Expose
    private String branchcode;
    @SerializedName("BRANCHNAME")
    @Expose
    private String branchname;

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

}
