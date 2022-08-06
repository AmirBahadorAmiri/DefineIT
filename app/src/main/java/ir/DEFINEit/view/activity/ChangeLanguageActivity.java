/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:55 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.DEFINEit.R;
import ir.DEFINEit.adapter.ChangeLanguageAdapter;
import ir.DEFINEit.tools.country.Country;

public class ChangeLanguageActivity extends AppCompatActivity {

    RecyclerView translate_chnage_lang_recyclerView;
    ChangeLanguageAdapter changeLanguageAdapter;

    public boolean isFrom = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        findViews();
        start();

    }

    private void start() {

        isFrom = Objects.requireNonNull(getIntent().getExtras()).getBoolean("isFrom");
        translate_chnage_lang_recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        changeLanguageAdapter = new ChangeLanguageAdapter(this, Country.generateLanguage());
        translate_chnage_lang_recyclerView.setAdapter(changeLanguageAdapter);

    }

    private void findViews() {
        translate_chnage_lang_recyclerView = findViewById(R.id.translate_chnage_lang_recyclerView);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
