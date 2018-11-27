package com.ksoldatov.kr.model.suggestions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("unrestricted_value")
    @Expose
    private String unrestrictedValue;
    @SerializedName("data")
    @Expose
    private AdresData data;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnrestrictedValue() {
        return unrestrictedValue;
    }

    public void setUnrestrictedValue(String unrestrictedValue) {
        this.unrestrictedValue = unrestrictedValue;
    }

    public AdresData getData() {
        return data;
    }

    public void setData(AdresData data) {
        this.data = data;
    }

}