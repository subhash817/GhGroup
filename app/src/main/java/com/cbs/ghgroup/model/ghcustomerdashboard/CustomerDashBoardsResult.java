
package com.cbs.ghgroup.model.ghcustomerdashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerDashBoardsResult {

    @SerializedName("LogMessage")
    @Expose
    private LogMessage logMessage;
    @SerializedName("ObjDirectBar")
    @Expose
    private List<ObjDirectBar> objDirectBar;
    @SerializedName("ObjDirectPie")
    @Expose
    private List<ObjDirectPie> objDirectPie;
    @SerializedName("ObjGHBar")
    @Expose
    private List<ObjGHBar> objGHBar;
    @SerializedName("ObjGHPi")
    @Expose
    private List<ObjGHPus> objGHPi;

    public LogMessage getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(LogMessage logMessage) {
        this.logMessage = logMessage;
    }

    public List<ObjDirectBar> getObjDirectBar() {
        return objDirectBar;
    }

    public void setObjDirectBar(List<ObjDirectBar> objDirectBar) {
        this.objDirectBar = objDirectBar;
    }

    public List<ObjDirectPie> getObjDirectPie() {
        return objDirectPie;
    }

    public void setObjDirectPie(List<ObjDirectPie> objDirectPie) {
        this.objDirectPie = objDirectPie;
    }

    public List<ObjGHBar> getObjGHBar() {
        return objGHBar;
    }

    public void setObjGHBar(List<ObjGHBar> objGHBar) {
        this.objGHBar = objGHBar;
    }

    public List<ObjGHPus> getObjGHPi() {
        return objGHPi;
    }

    public void setObjGHPi(List<ObjGHPus> objGHPi) {
        this.objGHPi = objGHPi;
    }

}
