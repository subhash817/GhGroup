
package com.cbs.ghgroup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownlodLedgerRequest {

    @SerializedName("ScreenName")
    @Expose
    private String screenName;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("ProcName")
    @Expose
    private String procName;
    @SerializedName("DataBaseName")
    @Expose
    private String dataBaseName;
    @SerializedName("RptFileName")
    @Expose
    private String rptFileName;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("Branch")
    @Expose
    private String branch;

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getRptFileName() {
        return rptFileName;
    }

    public void setRptFileName(String rptFileName) {
        this.rptFileName = rptFileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
