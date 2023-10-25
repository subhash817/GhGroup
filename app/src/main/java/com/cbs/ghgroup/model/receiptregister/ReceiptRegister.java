
package com.cbs.ghgroup.model.receiptregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiptRegister {

    @SerializedName("ReceiptRegisterResult")
    @Expose
    private ReceiptRegisterResult receiptRegisterResult;

    public ReceiptRegisterResult getReceiptRegisterResult() {
        return receiptRegisterResult;
    }

    public void setReceiptRegisterResult(ReceiptRegisterResult receiptRegisterResult) {
        this.receiptRegisterResult = receiptRegisterResult;
    }

}
