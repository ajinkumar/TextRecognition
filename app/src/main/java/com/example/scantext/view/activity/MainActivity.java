package com.example.scantext.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantext.R;
import com.example.scantext.databinding.ActivityMainBinding;
import com.example.scantext.view.adapter.TwitterListAdapter;
import com.example.scantext.viewmodel.MainViewModel;
import com.google.mlkit.vision.common.InputImage;

import java.util.Observable;
import java.util.Observer;


public class MainActivity extends BaseActivity implements Observer {

    ActivityResultLauncher<Intent> someActivityResultLauncher;
    ActivityResultLauncher<String> mGetContent;
    private ActivityMainBinding mainBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startDataBinding();
        createTwitterList(mainBinding.twitterList);
        setUpObserver(mainViewModel);
    }

    private void startDataBinding() {
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bundle bundle = result.getData().getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
                        mainViewModel.recognizeText(inputImage, bitmap);
                    }
                });

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    Bitmap bitmap = getRotatedBitmap(getApplicationContext(), uri);
                    InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
                    mainViewModel.recognizeText(inputImage, bitmap);
                });

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this, someActivityResultLauncher, mGetContent);
        mainBinding.setMainViewModel(mainViewModel);
    }

    private void createTwitterList(RecyclerView recyclerView) {
        TwitterListAdapter adapter = new TwitterListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MainViewModel) {
            TwitterListAdapter twitterListAdapter = (TwitterListAdapter) mainBinding.twitterList.getAdapter();
            MainViewModel mainViewModel = (MainViewModel) observable;
            twitterListAdapter.setDetails(mainViewModel.getDetails());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.reset();
    }
}





