/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/9/22, 2:53 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.tapsell_ads;

import android.app.Activity;

import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.tapsell.plus.AdRequestCallback;
import ir.tapsell.plus.AdShowListener;
import ir.tapsell.plus.TapsellPlus;
import ir.tapsell.plus.model.TapsellPlusAdModel;

public class AdManager {

    private static String rewardedResponseId;

    public static void requestAd(Activity activity, DefaultListener defaultListener) {
        TapsellPlus.requestRewardedVideoAd(
                activity,
                "629d9ce973843918417ab458",
                new AdRequestCallback() {
                    @Override
                    public void response(TapsellPlusAdModel tapsellPlusAdModel) {
                        super.response(tapsellPlusAdModel);
                        rewardedResponseId = tapsellPlusAdModel.getResponseId();
                        defaultListener.onSuccess(null);
                    }

                    @Override
                    public void error(String message) {
                        defaultListener.onFailure(message);
                    }
                });
    }

    public static void showAd(Activity activity, AdShowListener adShowListener) {
        TapsellPlus.showRewardedVideoAd(activity, rewardedResponseId,
                adShowListener);
    }

}