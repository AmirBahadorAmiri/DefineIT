/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:56 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.DEFINEit.R;
import ir.DEFINEit.tools.tts.TTsSingle;

public class SettingsActivity extends AppCompatActivity {

    View selectGenderView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViews();
        start();

    }

    public void findViews() {
        selectGenderView = findViewById(R.id.selectGenderView);
    }

    public void start() {
        selectGenderView.setOnClickListener(v -> {

            AlertDialog.Builder chooseGender = new AlertDialog.Builder(this);
            String[] item = new String[]{"آقا", "خانم"};
            short isMan = 0;
            if (!TTsSingle.isMan()) isMan = 1;
            chooseGender.setSingleChoiceItems(item, isMan, (dialog, which) -> {
                switch (which) {
                    case 0:
                        TTsSingle.setMan(true);
                        break;
                    case 1:
                        TTsSingle.setMan(false);
                        break;
                }
                dialog.dismiss();
            });
            chooseGender.setNegativeButton("لغو", (dialog, which) -> {
            });
            chooseGender.show();

        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
