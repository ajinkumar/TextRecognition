package com.example.scantext.api;

import com.example.scantext.model.TwitterData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface APIInterface {

    @GET("recent?")
    Observable<TwitterData> fetchTwitterDetails(@Query("query") String query);

}
