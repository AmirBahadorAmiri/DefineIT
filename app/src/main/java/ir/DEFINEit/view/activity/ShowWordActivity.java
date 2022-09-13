/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:56 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ir.DEFINEit.R;
import ir.DEFINEit.tools.copy_helper.CopyHelper;
import ir.DEFINEit.tools.database.DBM;
import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.DEFINEit.tools.sound_volume.VolumeManager;
import ir.DEFINEit.tools.tts.TTsSingle;

public class ShowWordActivity extends AppCompatActivity {

    AppCompatImageButton wordTagged, speakLogo, searchSettings, copyLogo;
    AppCompatTextView englishWord, persianWord;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_word);

        findViews();
        start();

    }

    private void start() {

        int id = Objects.requireNonNull(getIntent().getExtras()).getInt("id");
        //noinspection ResultOfMethodCallIgnored
        DBM.getDB(this).getWordDao().getWordById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(word -> {
                    if (word != null) {

                        setResult(1001);
                        word.setView_time(System.currentTimeMillis());

                        DBM.getDB(this).getWordDao().update(word)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                    }

                                    @Override
                                    public void onComplete() {
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.d("TAG", "onError: " + e.getMessage());
                                    }
                                });

                        englishWord.setText(word.getEnglishWord());
                        persianWord.setText(word.getPersianWord());

                        if (word.isFavorite())
                            wordTagged.setImageResource(R.drawable.ic_round_favorite);
                        else wordTagged.setImageResource(R.drawable.ic_round_unfavorite);

                        wordTagged.setOnClickListener(n -> {
                            setResult(1001);
                            word.setFavorite(!word.isFavorite());
                            word.setFavorite_time(System.currentTimeMillis());
                            if (word.isFavorite())
                                wordTagged.setImageResource(R.drawable.ic_round_favorite);
                            else wordTagged.setImageResource(R.drawable.ic_round_unfavorite);
                            DBM.getDB(this).getWordDao().update(word)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new CompletableObserver() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {
                                        }

                                        @Override
                                        public void onComplete() {
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            Log.d("TAG", "onError: " + e.getMessage());
                                        }
                                    });

                        });

                    }
                });

        searchSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        copyLogo.setOnClickListener(v -> {
            CopyHelper.insert(englishWord.getText().toString() + "\n" + persianWord.getText().toString());
            Toast.makeText(this, "متن کپی شد", Toast.LENGTH_SHORT).show();
        });

        speakLogo.setOnClickListener(v -> {

            if (VolumeManager.getVolume(this) == 0) {

                Snackbar.make(v, "صدای سیستم قطع است", Snackbar.LENGTH_LONG)
                        .setAction("افزایش صدا", n -> VolumeManager.setVolume(this, VolumeManager.getManager(this).getStreamMaxVolume(AudioManager.STREAM_MUSIC))).setBackgroundTint(getResources().getColor(R.color.themeActionBarColor)).setTextColor(getResources().getColor(R.color.material_dark_2)).show();

            } else {

                TTsSingle.initialize(this, new DefaultListener() {
                    @Override
                    public void onSuccess(Object obj) {
                        if (TTsSingle.isSupportLanguage(ShowWordActivity.this, "en")) {
                            TTsSingle.speak(englishWord.getText().toString());
                        } else {
                            Toast.makeText(ShowWordActivity.this, "بسته نرم صوتی این زبان نصب نشده است", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void findViews() {
        wordTagged = findViewById(R.id.wordTagged);
        speakLogo = findViewById(R.id.send_speakLogo);
        persianWord = findViewById(R.id.persianWord);
        englishWord = findViewById(R.id.englishWord);
        searchSettings = findViewById(R.id.settings);
        copyLogo = findViewById(R.id.copyLogo);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
