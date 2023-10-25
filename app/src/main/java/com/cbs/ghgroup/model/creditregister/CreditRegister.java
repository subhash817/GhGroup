
package com.cbs.ghgroup.model.creditregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditRegister {

    @SerializedName("CreditRegisterResult")
    @Expose
    private CreditRegisterResult creditRegisterResult;

    public CreditRegisterResult getCreditRegisterResult() {
        return creditRegisterResult;
    }

    public void setCreditRegisterResult(CreditRegisterResult creditRegisterResult) {
        this.creditRegisterResult = creditRegisterResult;
    }

}
