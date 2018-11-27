package com.ksoldatov.kr.api;

import com.ksoldatov.kr.model.ApiRequest;
import com.ksoldatov.kr.model.suggestions.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {
    @POST("4_1/rs/suggest/party")
    Call<ResponseObject> getSuggestions(
            @Body ApiRequest userInput);
}
