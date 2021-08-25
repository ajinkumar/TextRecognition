package com.example.scantext.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TwitterData implements Serializable {

    @SerializedName("data")
    @Expose
    private List<TweetsData> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public TwitterData(List<TweetsData> data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }

    public List<TweetsData> getData() {
        return data;
    }

    public void setData(List<TweetsData> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
