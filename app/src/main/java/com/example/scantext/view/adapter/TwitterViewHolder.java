package com.example.scantext.view.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.scantext.databinding.ItemTwitterBinding;
import com.example.scantext.model.TweetsData;
import com.example.scantext.viewmodel.TwitterItemViewModel;


class TwitterViewHolder extends RecyclerView.ViewHolder {
    private final ItemTwitterBinding itemTwitterBinding;

    TwitterViewHolder(ItemTwitterBinding itemTwitterBinding) {
        super(itemTwitterBinding.itemPhoto);
        this.itemTwitterBinding = itemTwitterBinding;
    }

    void bindDetails(TweetsData tweetsData) {
        if (itemTwitterBinding.getTwitterItemViewModel() == null) {
            itemTwitterBinding.setTwitterItemViewModel(new TwitterItemViewModel(tweetsData, itemView.getContext()));
        } else {
            itemTwitterBinding.getTwitterItemViewModel().setDetails(tweetsData);
        }
    }
}

