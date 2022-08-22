/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:57 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.DEFINEit.R;
import ir.DEFINEit.adapter.WordTaggedAdapter;
import ir.DEFINEit.model.WordModel;
import ir.DEFINEit.tools.database.DBM;
import ir.DEFINEit.tools.dialog_manager.DialogManager;
import ir.DEFINEit.tools.listeners.DefaultListener;

public class WordTaggedActivity extends AppCompatActivity {

    RecyclerView tagged_recyclerView;
    WordTaggedAdapter wordTaggedAdapter;
    AppCompatImageButton delete_tagged;
    private List<WordModel> words = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_tagged);

        findViews();
        start();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void start() {
        tagged_recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        wordTaggedAdapter = new WordTaggedAdapter(this, words);
        tagged_recyclerView.setAdapter(wordTaggedAdapter);

        delete_tagged.setOnClickListener(n -> {

            if (words != null && !words.isEmpty()) {

                DialogManager.showDialog(this, false, "کلمات ستاره دار را پاک کنم ؟", new DefaultListener() {
                    @Override
                    public void onSuccess() {

                        DBM.getDB(WordTaggedActivity.this).getWordDao().clearFavorites()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                    }

                                    @Override
                                    public void onComplete() {
                                        Toast.makeText(WordTaggedActivity.this, "کلمات ستاره دار پاک شد", Toast.LENGTH_SHORT).show();
                                        words.clear();
                                        wordTaggedAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.d("TAG", "onError: " + e.getMessage());
                                    }
                                });

                    }
                });

            } else {
                Toast.makeText(this, "لیست کلمات ستاره دار خالی میباشد", Toast.LENGTH_SHORT).show();
            }

        });

        //noinspection ResultOfMethodCallIgnored
        DBM.getDB(this).getWordDao().readFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext -> {

                    if (onNext != null && !onNext.isEmpty()) {
                        words.clear();
                        words.addAll(onNext);
                        wordTaggedAdapter.setPersian(false);
                        wordTaggedAdapter.notifyDataSetChanged();
                    }

                }, e -> Log.d("TAG", "start: " + e.getMessage()));

    }

    private void findViews() {
        tagged_recyclerView = findViewById(R.id.tagged_recyclerView);
        delete_tagged = findViewById(R.id.delete_tagged);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == 1001) {

            //noinspection ResultOfMethodCallIgnored
            DBM.getDB(this).getWordDao().readFavorites()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext -> {

                        words.clear();
                        if (onNext != null && !onNext.isEmpty()) {
                            words.addAll(onNext);
                        } else {
                            words = new ArrayList<>();
                        }

                        wordTaggedAdapter.setPersian(false);
                        wordTaggedAdapter.notifyDataSetChanged();

                    }, e -> Log.d("TAG", "onActivityResult: " + e.getMessage()));

        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
