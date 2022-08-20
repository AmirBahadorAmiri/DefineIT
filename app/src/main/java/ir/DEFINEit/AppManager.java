/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 6/3/22, 12:07 AM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit;

import android.app.Application;
import android.util.Log;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.DEFINEit.tools.copy_helper.CopyHelper;
import ir.DEFINEit.tools.language_manager.LanguageManager;
import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.DEFINEit.tools.shared_helper.SharedSingle;
import ir.DEFINEit.tools.tts.TTsSingle;
import ir.tapsell.plus.TapsellPlus;
import ir.tapsell.plus.TapsellPlusInitListener;
import ir.tapsell.plus.model.AdNetworkError;
import ir.tapsell.plus.model.AdNetworks;

public class AppManager extends Application {

//    192.168.56.1      virtual local ip
//    192.168.43.10     physical local ip

    @Override
    public void onCreate() {
        super.onCreate();

        TTsSingle.initialize(this, new DefaultListener() {
            @Override
            public void onSuccess() {
                TTsSingle.isSupportLanguage(getBaseContext(), LanguageManager.getDefaultVoiceLanguage());
            }
        });
        CopyHelper.initialize(this);
        SharedSingle.initialize(this, "Toka");
        fontSetup();
        tapsellSetup();
    }

    private void fontSetup() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Dana-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    private void tapsellSetup() {

        TapsellPlus.initialize(this, "tkpjfdodbsfrkrehjsrnthmrnokfkjjagpdmhhokkdjfdskcrcikrelgbkkdllrsambppp",
                new TapsellPlusInitListener() {
                    @Override
                    public void onInitializeSuccess(AdNetworks adNetworks) {
                        Log.d("onInitializeSuccess", adNetworks.name());
                    }

                    @Override
                    public void onInitializeFailed(AdNetworks adNetworks,
                                                   AdNetworkError adNetworkError) {
                        Log.e("onInitializeFailed", "ad network: " + adNetworks.name() + ", error: " + adNetworkError.getErrorMessage());
                    }
                });

    }

}
