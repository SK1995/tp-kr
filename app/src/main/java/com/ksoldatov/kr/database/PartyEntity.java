package com.ksoldatov.kr.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ksoldatov.kr.model.suggestions.AdresData;
import com.ksoldatov.kr.model.suggestions.Suggestion;

import java.util.Date;

@Entity(tableName = "party")
public class PartyEntity {
    @NonNull
    @PrimaryKey
    String hid;
    String value;
    String managerName;
    String managerPost;
    String kpp;
    String inn;
    String fullName;
    String ogrn;
    String address;
    String latitude;
    String longitude;
    Date lastRequestDate;
    boolean isFavourite;

    public PartyEntity() {
    }

    public PartyEntity(Suggestion sg, boolean isFavourite) {
        hid = sg.getData().getHid();
        value = sg.getValue();
        if (sg.getData().getManagement() != null) {
            if (sg.getData().getManagement().getName() != null) {
                managerName = sg.getData().getManagement().getName();
            }
            if (sg.getData().getManagement().getPost() != null) {
                managerPost = sg.getData().getManagement().getPost();
            }
        }
        kpp = sg.getData().getKpp();
        fullName = sg.getData().getName().getFullWithOpf();
        inn = sg.getData().getInn();
        ogrn = sg.getData().getOgrn();
        address = sg.getData().getAddress().getValue();
        lastRequestDate = new Date();
        AdresData adresData = sg.getData().getAddress().getData();
        if (adresData != null) {
            longitude = sg.getData().getAddress().getData().getGeoLon();
            latitude = sg.getData().getAddress().getData().getGeoLat();
        }

        this.isFavourite = isFavourite;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPost() {
        return managerPost;
    }

    public void setManagerPost(String managerPost) {
        this.managerPost = managerPost;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getLastRequestDate() {
        return lastRequestDate;
    }

    public void setLastRequestDate(Date lastRequestDate) {
        this.lastRequestDate = lastRequestDate;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public String toString() {
        return "Party value: " + value + "\n" +
                "Manager name: " + (managerName == null ? "No info" : managerName) + "\n" +
                "Manager post: " + (managerPost == null ? "No info" : managerPost) + "\n" +
                "KPP: " + (kpp == null ? "No info" : kpp) + "\n" +
                "Full party name: " + fullName + "\n" +
                "INN: " + inn + "\n" +
                "OGRN: " + ogrn + "\n" +
                "Address: " + address + "\n" +
                "Last requested date: " + lastRequestDate.toString();
    }

}
