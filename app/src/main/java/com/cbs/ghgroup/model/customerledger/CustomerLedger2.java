
package com.cbs.ghgroup.model.customerledger;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerLedger2 {

    @SerializedName("CustomerLedger2Result")
    @Expose
    private CustomerLedger2Result customerLedger2Result;

    public CustomerLedger2Result getCustomerLedger2Result() {
        return customerLedger2Result;
    }

    public void setCustomerLedger2Result(CustomerLedger2Result customerLedger2Result) {
        this.customerLedger2Result = customerLedger2Result;
    }

}
