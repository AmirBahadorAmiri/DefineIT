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
import androidx.appcompat.app.AppCompatActivity;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.DEFINEit.R;
import ir.DEFINEit.tools.dialog_manager.DialogManager;
import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.DEFINEit.tools.tts.TTsSingle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        start();

    }

    public void start() {

        findViewById(R.id.selectGenderView).setOnClickListener(v -> {
            String[] item = new String[]{"آقا", "خانم"};
            short isMan = (short) (TTsSingle.isMan() ? 0 : 1);
            DialogManager.showItemChooserDialog(this, true, "", item, isMan, new DefaultListener() {
                @Override
                public void onSuccess(Object obj) {
                    switch ((int) obj) {
                        case 0:
                            TTsSingle.setMan(true);
                            break;
                        case 1:
                            TTsSingle.setMan(false);
                            break;
                    }
                }
            });

        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
