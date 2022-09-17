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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
        Call<ResponseBody> translateByGoogle(@Url String url,
                                             @Query("client") String client, @Query("sl") String from,
                                             @Query("tl") String to, @Query("dt") String dt,
                                             @Query("q") String text, @Query("ie") String ie,
                                             @Query("oe") String oe);

        @POST
        Call<ResponseBody> translateByYandex(@Url String url,
                                             @Query("id") String id, @Query("srv") String srv,
                                             @Header("Content=Type") String content_type,
                                             @Body String body);

        @POST
        Call<ResponseBody> translateByMicrosoft(@Url String url,
                                                String api_version, String from,
                                                String to, @Body String Body);

    }
}
