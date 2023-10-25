package com.cbs.ghgroup.model.userlist;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExcelData {

@SerializedName("Branch")
@Expose
private String branch;
@SerializedName("Code")
@Expose
private String code;
@SerializedName("DataBaseName")
@Expose
private String dataBaseName;
@SerializedName("FromDate")
@Expose
private String fromDate;
@SerializedName("ProcName")
@Expose
private String procName;
@SerializedName("RptFileName")
@Expose
private String rptFileName;
@SerializedName("ScreenName")
@Expose
private String screenName;
@SerializedName("ToDate")
@Expose
private String toDate;
@SerializedName("Type")
@Expose
private String type;

public String getBranch() {
return branch;
}

public void setBranch(String branch) {
this.branch = branch;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getDataBaseName() {
return dataBaseName;
}

public void setDataBaseName(String dataBaseName) {
this.dataBaseName = dataBaseName;
}

public String getFromDate() {
return fromDate;
}

public void setFromDate(String fromDate) {
this.fromDate = fromDate;
}

public String getProcName() {
return procName;
}

public void setProcName(String procName) {
this.procName = procName;
}

public String getRptFileName() {
return rptFileName;
}

public void setRptFileName(String rptFileName) {
this.rptFileName = rptFileName;
}

public String getScreenName() {
return screenName;
}

public void setScreenName(String screenName) {
this.screenName = screenName;
}

public String getToDate() {
return toDate;
}

public void setToDate(String toDate) {
this.toDate = toDate;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

}