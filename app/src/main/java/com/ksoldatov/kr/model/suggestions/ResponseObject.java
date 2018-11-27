package com.ksoldatov.kr.model.suggestions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

public class ResponseObject {

    @SerializedName("suggestions")
    @Expose
    private ArrayList<Suggestion> suggestions = null;

    public ArrayList<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(ArrayList<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public Suggestion getSuggestionByHid(String hid) {
        for (Suggestion sg : suggestions) {
            if (Objects.equals(sg.getData().getHid(), hid)) {
                return sg;
            }
        }
        return null;
    }
}


