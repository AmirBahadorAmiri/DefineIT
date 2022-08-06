/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 2/16/22, 3:45 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.web_api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class WebConfig {

    private static Retrofit retrofit;
    private static RetrofitInterfaces retrofitInterfaces;

    public static RetrofitInterfaces getRetrofitInterfaces() {

        if (retrofit == null)
            retrofit = new Retrofit.Builder().baseUrl("https://google.com").build();
        if (retrofitInterfaces == null)
            retrofitInterfaces = retrofit.create(RetrofitInterfaces.class);
        return retrofitInterfaces;
    }

    public interface RetrofitInterfaces {

        @GET
        Call<ResponseBody> translateText(@Url String url,
                                         @Query("client") String client, @Query("sl") String from,
                                         @Query("tl") String to, @Query("dt") String dt,
                                         @Query("q") String text, @Query("ie") String ie,
                                         @Query("oe") String oe);

//
//        @GET
//        Call<ResponseBody> loginAppVersion(@Url String url,
//                                           @Query("versioncode") int versionCode,
//                                           @Query("versionname") String versionName);
//
//        @GET
//        Call<ResponseBody> loginUserNumber(@Url String url,
//                                           @Query("user_number") String user_number,
//                                           @Query("user_password") String user_password);
//
//        @GET
//        Call<ResponseBody> loginUserEmail(@Url String url,
//                                          @Query("user_email") String user_email,
//                                          @Query("user_password") String user_password);
//
//        @GET
//        Call<ResponseBody> getUserInfo(@Url String url,
//                                       @Query("user_token") String user_token,
//                                       @Query("user_password") String user_password);
//
//        @GET
//        Call<ResponseBody> createUserAccount(@Url String url,
//                                             @Query("user_email") String user_email,
//                                             @Query("user_number") String user_number,
//                                             @Query("user_namefamily") String user_namefamily,
//                                             @Query("user_password") String user_password);
//
//
//        @GET
//        Call<ResponseBody> changeUserStatus(@Url String url,
//                                            @Query("user_token") String user_token,
//                                            @Query("user_status") String user_status,
//                                            @Query("user_password") String user_password);
//
//
//        @GET
//        Call<ResponseBody> changeUserInfo(@Url String url,
//                                          @Query("user_token") String user_token,
//                                          @Query("user_email") String user_email,
//                                          @Query("user_namefamily") String user_namefamily,
//                                          @Query("user_number") String user_number,
//                                          @Query("user_password") String user_password);
//
//
//        @GET
//        Call<ResponseBody> changeUserPassword(@Url String url,
//                                          @Query("user_token") String user_token,
//                                          @Query("user_password") String user_password,
//                                          @Query("user_newpassword") String user_newpassword);

    }
}
