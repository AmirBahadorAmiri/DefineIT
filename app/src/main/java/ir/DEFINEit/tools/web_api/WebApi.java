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

    public static void translateText(String text, DefaultListener defaultListener) {
        WebConfig.getRetrofitInterfaces().translateText(
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


//    public static void loginAppVersion(Context context, DefaultListener defaultListener) {
//        WebConfig.getRetrofitInterfaces().loginAppVersion("http://192.168.43.10/web/toka/versionControl.php",
//                Packager.getVersionCode(context),Packager.getVersionName(context)).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                defaultListener.onSuccess(response);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                defaultListener.onFailure(t);
//            }
//        });
//    }
//
//    public static void loginUserNumber(String number, String password, DefaultListener defaultListener) {
//        WebConfig.getRetrofitInterfaces().loginUserNumber("http://192.168.43.10/web/toka/userNumberLogin.php", number, password).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                defaultListener.onSuccess(response);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                defaultListener.onFailure(t);
//            }
//        });
//    }
//
//    public static void loginUserEmail(String email, String password, DefaultListener defaultListener) {
//        WebConfig.getRetrofitInterfaces().loginUserEmail("http://192.168.43.10/web/toka/userEmailLogin.php", email, password).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                defaultListener.onSuccess(response);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                defaultListener.onFailure(t);
//            }
//        });
//    }
//
//    public static void createUserAccount(String email,String number,String namefamily,String password, DefaultListener defaultListener) {
//        WebConfig.getRetrofitInterfaces().createUserAccount("http://192.168.43.10/web/toka/userCreateAccount.php",email,number,namefamily,password).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                defaultListener.onSuccess(response);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                defaultListener.onFailure(t);
//            }
//        });
//    }
//
//    public static void getUserInfo(String token, String password, DefaultListener defaultListener) {
//        WebConfig.getRetrofitInterfaces().getUserInfo("http://192.168.43.10/web/toka/userGetInformation.php",token,password).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                defaultListener.onSuccess(response);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                defaultListener.onFailure(t);
//            }
//        });
//    }

}
