package com.ksoldatov.kr;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.ksoldatov.kr.api.Api;
import com.ksoldatov.kr.database.PartyDB;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static Api api;
    private PartyDB partyDB;
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Token " + BuildConfig.API_KEY)
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept-Type", "application/json")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        String BASE_URL = "https://suggestions.dadata.ru/suggestions/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        partyDB = Room.databaseBuilder(this, PartyDB.class, "database").build();
        app = this;
    }

    public static Api getApi() {
        return api;
    }

    public PartyDB getPartyDB() {
        return partyDB;
    }

    public static App getApp() {
        return app;
    }
}
