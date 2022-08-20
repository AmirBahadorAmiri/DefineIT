/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:56 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.DEFINEit.R;
import ir.DEFINEit.tools.tts.TTsSingle;
import ir.DEFINEit.tools.user_info.User;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        start();

    }

    public void start() {

        findViewById(R.id.selectThemeView).setOnClickListener(v -> {
            AlertDialog.Builder chooseGender = new AlertDialog.Builder(this);
            String[] item = new String[]{"روز", "شب"};
            short isMan = (short) (User.isDarkTheme() ?1:0);
            chooseGender.setSingleChoiceItems(item, isMan, (dialog, which) -> {
                switch (which) {
                    case 0:
                        User.setDarkTheme(false);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case 1:
                        User.setDarkTheme(true);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                }
                dialog.dismiss();
            });
            chooseGender.setNegativeButton("لغو", (dialog, which) -> {
            });
            chooseGender.show();
        });

        findViewById(R.id.selectGenderView).setOnClickListener(v -> {
            AlertDialog.Builder chooseGender = new AlertDialog.Builder(this);
            String[] item = new String[]{"آقا", "خانم"};
            short isMan = (short) (TTsSingle.isMan()?0:1);
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
            chooseGender.setNegativeButton("لغو", (dialog, which) -> {});
            chooseGender.show();
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
