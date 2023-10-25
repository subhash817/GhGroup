
package com.cbs.ghgroup.model.debitregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DebitNoteRegister {

    @SerializedName("DebitNoteRegisterResult")
    @Expose
    private DebitNoteRegisterResult debitNoteRegisterResult;

    public DebitNoteRegisterResult getDebitNoteRegisterResult() {
        return debitNoteRegisterResult;
    }

    public void setDebitNoteRegisterResult(DebitNoteRegisterResult debitNoteRegisterResult) {
        this.debitNoteRegisterResult = debitNoteRegisterResult;
    }

}
