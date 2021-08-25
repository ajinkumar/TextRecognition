package com.example.scantext.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

//    private final static String BASE_URL = "https://www.wordsapiv1.p.mashape.com/words/";
    private final static String BASE_URL = "https://api.twitter.com/2/tweets/search/";
    private static Retrofit retrofit = null;

    public static APIInterface create() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", " Bearer AAAAAAAAAAAAAAAAAAAAAEgeTAEAAAAAb9jlZgQBMFH8IOW8WM12von40ks%3DvB3ifLOuB9vrDKzHWxmhoKaujfsqqv7r6HcTgj1IT4Jf0Y6XCM")
                    .build();
            return chain.proceed(newRequest);
        }).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(APIInterface.class);
    }
}
