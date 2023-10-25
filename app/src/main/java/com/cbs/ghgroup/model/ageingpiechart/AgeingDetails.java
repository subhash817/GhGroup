
package com.cbs.ghgroup.model.ageingpiechart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AgeingDetails {

    @SerializedName("AgeingDetailsResult")
    @Expose
    private AgeingDetailsResult ageingDetailsResult;

    public AgeingDetailsResult getAgeingDetailsResult() {
        return ageingDetailsResult;
    }

    public void setAgeingDetailsResult(AgeingDetailsResult ageingDetailsResult) {
        this.ageingDetailsResult = ageingDetailsResult;
    }

}
