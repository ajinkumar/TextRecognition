package com.example.scantext.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.scantext.R;
import com.example.scantext.databinding.ItemTwitterBinding;
import com.example.scantext.model.TweetsData;

import java.util.Collections;
import java.util.List;

public class TwitterListAdapter extends RecyclerView.Adapter<TwitterViewHolder> {

    private List<TweetsData> twitterData;

    public TwitterListAdapter() {
        this.twitterData = Collections.emptyList();
    }

    @Override
    @NonNull
    public TwitterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTwitterBinding itemTwitterBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_twitter,
                parent, false
        );
        return new TwitterViewHolder(itemTwitterBinding);
    }

    @Override
    public void onBindViewHolder(TwitterViewHolder holder, int position) {
        holder.bindDetails(twitterData.get(position));
    }

    @Override
    public int getItemCount() {
        return twitterData.size();
    }

    public void setDetails(List<TweetsData> twitterData) {
        this.twitterData = twitterData;
        notifyDataSetChanged();
    }

}
