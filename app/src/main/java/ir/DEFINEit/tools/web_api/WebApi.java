/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 2/16/22, 3:47 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.web_api;

import androidx.annotation.NonNull;

import ir.DEFINEit.tools.language_manager.LanguageManager;
import ir.DEFINEit.tools.listeners.DefaultListener;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebApi {

    public static void translateByGoogle(String text, DefaultListener defaultListener) {
        WebConfig.getRetrofitInterfaces().translateByGoogle(
                "https://translate.googleapis.com/translate_a/single", "gtx", LanguageManager.getFromLangaugeCode(),
                LanguageManager.getToLangaugeCode(), "t", text,
                "UTF-8", "UTF-8").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                defaultListener.onSuccess(response);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {
                defaultListener.onFailure(throwable);
            }
        });
    }

    public static void translateByYandex(String text, DefaultListener defaultListener) {

        String sb = "lang=" + LanguageManager.getFromLangaugeCode() + "-" +
                LanguageManager.getToLangaugeCode() + "&text=" + text;

        WebConfig.getRetrofitInterfaces().translateByYandex("", "4b90eaa326954760990ab007b02b0318-44-0",
                "android", "application/x-www-form-urlencoded", sb).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                defaultListener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                defaultListener.onFailure(throwable);
            }
        });
    }

    public static void translateByMicrosoft(String text, DefaultListener defaultListener) {

        String sb = "[{\"Text\": \"" + text + "\"}]";

        WebConfig.getRetrofitInterfaces().translateByMicrosoft("api.cognitive.microsofttranslator.com",
                        "3.0", LanguageManager.getFromLangaugeCode(),
                        LanguageManager.getToLangaugeCode(), sb)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        defaultListener.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        defaultListener.onFailure(t);
                    }
                });
    }

}
