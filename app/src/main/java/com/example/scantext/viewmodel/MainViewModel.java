package com.example.scantext.viewmodel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.example.scantext.api.APIInterface;
import com.example.scantext.app.ScanTextApplication;
import com.example.scantext.model.Result;
import com.example.scantext.model.TweetsData;
import com.example.scantext.model.WordData;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainViewModel extends Observable {


    private final List<TweetsData> twitterData;
    private final List<WordData> wordData;
    public ObservableInt layoutVisibility;
    public ObservableInt textVisibility;
    public ObservableField<String> messageLabel;
    public ObservableField<Bitmap> bitmapImage;
    public ObservableField<String> textSynonyms;
    public ObservableField<String> textAntonyms;
    public ObservableField<String> textThesaurus;
    private List<String> antonyms = new ArrayList<>();
    private List<String> synonyms = new ArrayList<>();
    private List<String> hasTypes = new ArrayList<>();

    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private ActivityResultLauncher<String> mGetContent;
    private Context context;
    private Subscription subscription;

    public MainViewModel(Context context, ActivityResultLauncher<Intent> someActivityResultLauncher,
                         ActivityResultLauncher<String> mGetContent) {
        this.context = context;
        this.someActivityResultLauncher = someActivityResultLauncher;
        this.mGetContent = mGetContent;
        twitterData = new ArrayList<>();
        wordData = new ArrayList<>();

        layoutVisibility = new ObservableInt(View.GONE);
        textVisibility = new ObservableInt(View.VISIBLE);
        messageLabel = new ObservableField<>("");
        bitmapImage = new ObservableField<>();
        textSynonyms = new ObservableField<>("");
        textAntonyms = new ObservableField<>("");
        textThesaurus = new ObservableField<>("");
    }

    public void capture(View view) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals("Take Photo")) {
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(in);

            } else if (options[item].equals("Choose from Gallery")) {
                mGetContent.launch("image/*");

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void recognizeText(InputImage image, Bitmap bitmap) {

        bitmapImage.set(bitmap);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        Task<Text> result = recognizer.process(image)
                .addOnSuccessListener(visionText -> {
                    Log.e("visionText 1 ", "" + visionText.getText());
                    String text = visionText.getText();
                    messageLabel.set(text);
                    getWordsAPI(text);
                    fetchTweetList(text);

//                    for (Text.TextBlock block : visionText.getTextBlocks()) {
//                        Rect boundingBox = block.getBoundingBox();
//                        Point[] cornerPoints = block.getCornerPoints();
//                        String text = block.getText();
//
//
//                        for (Text.Line line : block.getLines()) {
//                            Log.e("line 1 ", "" + line.getText());
//                            for (Text.Element element : line.getElements()) {
//                                Log.e("line 2 ", "" + element.getText());
//                            }
//                        }
//                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void getWordsAPI(String word) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://wordsapiv1.p.rapidapi.com/words/" + word)
                .addHeader("X-Mashape-Key", "0ee03de144mshb6bdd7f9bc55783p17d677jsn1e96ca70e528")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                layoutVisibility.set(View.GONE);
                textVisibility.set(View.VISIBLE);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Gson gson = new Gson();
                WordData result = gson.fromJson(myResponse, WordData.class);

                if (result.getResults() == null) {
                    textVisibility.set(View.VISIBLE);
                    layoutVisibility.set(View.GONE);
                    return;
                }

                textVisibility.set(View.GONE);
                layoutVisibility.set(View.VISIBLE);

                for (Result result1 : result.getResults()) {
                    if (result1.getAntonyms() != null) {
                        antonyms.addAll(result1.getAntonyms());
                    }

                    if (result1.getSynonyms() != null) {
                        synonyms.addAll(result1.getSynonyms());
                    }

                    if (result1.getHasTypes() != null) {
                        hasTypes.addAll(result1.getHasTypes());
                    }
                }

                if (synonyms.size() != 0) {
                    String st = replaceBrackets(synonyms.toString());
                    textSynonyms.set(replaceBrackets(synonyms.toString()));
                }

                if (antonyms.size() != 0) {
                    textAntonyms.set(replaceBrackets(antonyms.toString()));
                }

                if (hasTypes.size() != 0) {
                    textThesaurus.set(replaceBrackets(hasTypes.toString()));
                }

            }
        });
    }

    private String replaceBrackets(String str) {
        return str.replaceAll("\\[", "").replaceAll("\\]", "");
    }

    private void fetchTweetList(String word) {
        unSubscribeFromObservable();

        ScanTextApplication scanTextApplication = ScanTextApplication.create(context);
        APIInterface retrofitService = scanTextApplication.getRetrofitService();
        subscription = retrofitService.fetchTwitterDetails(word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(scanTextApplication.subscribeScheduler())
                .subscribe(data -> {
                    changeDetails(data.getData());
                }, throwable -> {
                    throwable.printStackTrace();
                    Log.e("error", "" + throwable.getMessage());
                });
    }

    private void changeDetails(List<TweetsData> twitterData) {
        this.twitterData.clear();
        this.twitterData.addAll(twitterData);
        setChanged();
        notifyObservers();
    }

    public List<TweetsData> getDetails() {
        return twitterData;
    }

    private void unSubscribeFromObservable() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        subscription = null;
        context = null;
    }
}
