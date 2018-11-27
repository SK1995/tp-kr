package com.ksoldatov.kr.model.suggestions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("actuality_date")
    @Expose
    private Double actualityDate;
    @SerializedName("registration_date")
    @Expose
    private Double registrationDate;
    @SerializedName("liquidation_date")
    @Expose
    private Object liquidationDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getActualityDate() {
        return actualityDate;
    }

    public void setActualityDate(Double actualityDate) {
        this.actualityDate = actualityDate;
    }

    public Double getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Double registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Object getLiquidationDate() {
        return liquidationDate;
    }

    public void setLiquidationDate(Object liquidationDate) {
        this.liquidationDate = liquidationDate;
    }

}
