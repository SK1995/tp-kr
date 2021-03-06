package com.ksoldatov.kr.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;

import com.ksoldatov.kr.App;
import com.ksoldatov.kr.adapters.CounterPartyAdapter;
import com.ksoldatov.kr.model.ApiRequest;
import com.ksoldatov.kr.model.suggestions.ResponseObject;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionsTextWatcher implements TextWatcher {

    private AutoCompleteTextView autoCompleteTextView;
    private CounterPartyAdapter adapter;
    private Timer timer = new Timer();
    public static ResponseObject responseObject;

    public SuggestionsTextWatcher(CounterPartyAdapter adapter, AutoCompleteTextView autoCompleteTextView) {
        this.adapter = adapter;
        this.autoCompleteTextView = autoCompleteTextView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        timer.cancel();
        timer = new Timer();
        long DELAY = 1000;
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        App.getApi().getSuggestions(new ApiRequest(editable.toString())).enqueue(new Callback<ResponseObject>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseObject> call, @NonNull Response<ResponseObject> response) {

                                adapter.clear();
                                assert response.body() != null;
                                adapter.addAll(response.body().getSuggestions());
                                adapter.notifyDataSetChanged();
                                adapter.getFilter().filter(autoCompleteTextView.getText(), null);
                                autoCompleteTextView.showDropDown();
                                responseObject = response.body();
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseObject> call, @NonNull Throwable t) {
                                autoCompleteTextView.setText(t.getMessage());
                            }
                        });
                    }
                },
                DELAY
        );
    }
}
