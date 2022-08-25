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
import ir.DEFINEit.adapter.WordHistoryAdapter;
import ir.DEFINEit.model.WordModel;
import ir.DEFINEit.tools.database.DBM;
import ir.DEFINEit.tools.dialog_manager.DialogManager;
import ir.DEFINEit.tools.listeners.DefaultListener;

public class WordHistoryActivity extends AppCompatActivity {

    RecyclerView history_recyclerView;
    AppCompatImageButton delete_history;
    WordHistoryAdapter wordHistoryAdapter;
    private List<WordModel> words = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_history);
        findViews();
        start();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void start() {

        history_recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        wordHistoryAdapter = new WordHistoryAdapter(words);
        history_recyclerView.setAdapter(wordHistoryAdapter);

        //noinspection ResultOfMethodCallIgnored
        DBM.getDB(this).getWordDao().readHistories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext -> {

                    if (onNext != null && !onNext.isEmpty()) {
                        words.clear();
                        words.addAll(onNext);
                        wordHistoryAdapter.setPersian(false);
                        wordHistoryAdapter.notifyDataSetChanged();
                    }

                }, e -> Log.d("TAG", "start: " + e.getMessage()));

        delete_history.setOnClickListener(n -> {

            if (words != null && !words.isEmpty()) {

                DialogManager.showAcceptableQuizDialog(this, false, "تاریخچه را پاک کنم ؟", new DefaultListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        DBM.getDB(WordHistoryActivity.this).getWordDao().clearHistories()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {}
                                    @Override
                                    public void onComplete() {
                                        Toast.makeText(WordHistoryActivity.this, "تاریخچه پاک شد", Toast.LENGTH_SHORT).show();
                                        words.clear();
                                        wordHistoryAdapter.notifyDataSetChanged();
                                    }
                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.d("TAG", "onError: " + e.getMessage());
                                    }
                                });
                    }
                });

            } else {
                Toast.makeText(this, "تاریخچه کلمات خالی میباشد", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void findViews() {
        history_recyclerView = findViewById(R.id.history_recyclerView);
        delete_history = findViewById(R.id.delete_history);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1001) {
            //noinspection ResultOfMethodCallIgnored
            DBM.getDB(this).getWordDao().readHistories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext -> {
                        words.clear();
                        if (onNext != null && !onNext.isEmpty()) {
                            words.addAll(onNext);
                        } else {
                            words = new ArrayList<>();
                        }
                        wordHistoryAdapter.setPersian(false);
                        wordHistoryAdapter.notifyDataSetChanged();
                    }, e -> Log.d("TAG", "onActivityResult: " + e.getMessage()));
        }
    }
}
