
package com.cbs.ghgroup.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjUserProfileDetail {

    @SerializedName("BillingAddress")
    @Expose
    private String billingAddress;
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("GstIn")
    @Expose
    private String gstIn;
    @SerializedName("OwnerName")
    @Expose
    private String ownerName;
    @SerializedName("OwnerNo")
    @Expose
    private String ownerNo;
    @SerializedName("ShippingAddresses")
    @Expose
    private List<ShippingAddress> shippingAddresses;

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGstIn() {
        return gstIn;
    }

    public void setGstIn(String gstIn) {
        this.gstIn = gstIn;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerNo() {
        return ownerNo;
    }

    public void setOwnerNo(String ownerNo) {
        this.ownerNo = ownerNo;
    }

    public List<ShippingAddress> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(List<ShippingAddress> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

}
