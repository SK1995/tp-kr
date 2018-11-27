package com.ksoldatov.kr.model.suggestions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Opf {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("full")
    @Expose
    private String full;
    @SerializedName("short")
    @Expose
    private String _short;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getShort() {
        return _short;
    }

    public void setShort(String _short) {
        this._short = _short;
    }

}