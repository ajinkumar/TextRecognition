package com.example.scantext.viewmodel;

import android.content.Context;

import androidx.databinding.BaseObservable;

import com.example.scantext.model.TweetsData;


public class TwitterItemViewModel extends BaseObservable {

    private TweetsData tweetsData;
    private final Context context;

    public TwitterItemViewModel(TweetsData tweetsData, Context context) {
        this.tweetsData = tweetsData;
        this.context = context;
    }

    public String getText() {
        return tweetsData.getText();
    }

    public void setDetails(TweetsData tweetsData) {
        this.tweetsData = tweetsData;
        notifyChange();
    }
}
