
package com.cbs.ghgroup.model.ghcustomerdashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjDirectPie {

    @SerializedName("NineTyOneTwety")
    @Expose
    private String nineTyOneTwety;
    @SerializedName("OneTwetyabove")
    @Expose
    private String oneTwetyabove;
    @SerializedName("Sixty")
    @Expose
    private String sixty;
    @SerializedName("SixtyNinety")
    @Expose
    private String sixtyNinety;

    public String getNineTyOneTwety() {
        return nineTyOneTwety;
    }

    public void setNineTyOneTwety(String nineTyOneTwety) {
        this.nineTyOneTwety = nineTyOneTwety;
    }

    public String getOneTwetyabove() {
        return oneTwetyabove;
    }

    public void setOneTwetyabove(String oneTwetyabove) {
        this.oneTwetyabove = oneTwetyabove;
    }

    public String getSixty() {
        return sixty;
    }

    public void setSixty(String sixty) {
        this.sixty = sixty;
    }

    public String getSixtyNinety() {
        return sixtyNinety;
    }

    public void setSixtyNinety(String sixtyNinety) {
        this.sixtyNinety = sixtyNinety;
    }

}
