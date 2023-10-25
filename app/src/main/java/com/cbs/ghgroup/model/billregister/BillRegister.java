
package com.cbs.ghgroup.model.billregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillRegister {

    @SerializedName("BillRegisterResult")
    @Expose
    private BillRegisterResult billRegisterResult;

    public BillRegisterResult getBillRegisterResult() {
        return billRegisterResult;
    }

    public void setBillRegisterResult(BillRegisterResult billRegisterResult) {
        this.billRegisterResult = billRegisterResult;
    }

}
