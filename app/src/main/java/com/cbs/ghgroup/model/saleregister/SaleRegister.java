
package com.cbs.ghgroup.model.saleregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleRegister {

    @SerializedName("SaleRegisterResult")
    @Expose
    private SaleRegisterResult saleRegisterResult;

    public SaleRegisterResult getSaleRegisterResult() {
        return saleRegisterResult;
    }

    public void setSaleRegisterResult(SaleRegisterResult saleRegisterResult) {
        this.saleRegisterResult = saleRegisterResult;
    }

}
