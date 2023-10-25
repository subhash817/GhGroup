
package com.cbs.ghgroup.model.purchesregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseRegister {

    @SerializedName("PurchaseRegisterResult")
    @Expose
    private PurchaseRegisterResult purchaseRegisterResult;

    public PurchaseRegisterResult getPurchaseRegisterResult() {
        return purchaseRegisterResult;
    }

    public void setPurchaseRegisterResult(PurchaseRegisterResult purchaseRegisterResult) {
        this.purchaseRegisterResult = purchaseRegisterResult;
    }

}
