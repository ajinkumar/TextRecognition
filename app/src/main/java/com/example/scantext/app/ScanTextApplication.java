package com.example.scantext.app;

import android.app.Application;
import android.content.Context;


import com.example.scantext.api.APIClient;
import com.example.scantext.api.APIInterface;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class ScanTextApplication extends Application {

    private APIInterface APIInterface;
    private Scheduler scheduler;

    public static ScanTextApplication create(Context context) {
        return ScanTextApplication.get(context);
    }

    private static ScanTextApplication get(Context context) {
        return (ScanTextApplication) context.getApplicationContext();
    }

    public APIInterface getRetrofitService() {
        if (APIInterface == null) APIInterface = APIClient.create();
        return APIInterface;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) scheduler = Schedulers.io();
        return scheduler;
    }
}
