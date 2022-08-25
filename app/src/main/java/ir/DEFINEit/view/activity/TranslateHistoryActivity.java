/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:56 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
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
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.DEFINEit.R;
import ir.DEFINEit.adapter.TranslateHistoryAdapter;
import ir.DEFINEit.model.TextModel;
import ir.DEFINEit.tools.database.DBM;
import ir.DEFINEit.tools.dialog_manager.DialogManager;
import ir.DEFINEit.tools.listeners.DefaultListener;

public class TranslateHistoryActivity extends AppCompatActivity {

    AppCompatImageButton delete_translated;
    RecyclerView translated_recyclerView;
    TranslateHistoryAdapter translateHistoryAdapter;
    List<TextModel> sentenceList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_history);
        findViews();
        start();
    }

    private void findViews() {
        delete_translated = findViewById(R.id.delete_translated);
        translated_recyclerView = findViewById(R.id.translated_recyclerView);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void start() {

        translated_recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        translateHistoryAdapter = new TranslateHistoryAdapter(sentenceList);
        translated_recyclerView.setAdapter(translateHistoryAdapter);

        DBM.getDB(this).getSentenceDao().readAllText()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<TextModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull List<TextModel> onNext) {
                        if (onNext != null && !onNext.isEmpty()) {
                            sentenceList.clear();
                            sentenceList.addAll(onNext);
                            translateHistoryAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onError: " + e.getMessage());
                    }
                });


        delete_translated.setOnClickListener(v -> {

            if (sentenceList != null && !sentenceList.isEmpty()) {

                DialogManager.showAcceptableQuizDialog(this, false, "تاریخچه را پاک کنم ؟", new DefaultListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        DBM.getDB(TranslateHistoryActivity.this).getSentenceDao().deleteAllText()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                    }

                                    @Override
                                    public void onComplete() {
                                        Toast.makeText(TranslateHistoryActivity.this, "تاریخچه پاک شد", Toast.LENGTH_SHORT).show();
                                        sentenceList.clear();
                                        translateHistoryAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.d("TAG", "onError: " + e.getMessage());
                                    }
                                });
                    }
                });

            } else {
                Toast.makeText(this, "تاریخچه ترجمه خالی میباشد", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}