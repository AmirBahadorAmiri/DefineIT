package ir.DEFINEit.tools.translate_manager;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.DEFINEit.tools.web_api.WebApi;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class TranslateManager {

    public static short GOOGLE_TRANSLATE = 0;
    public static short YANDEX_TRANSLATE = 1;
    public static short MICROSOFT_TRANSLATE = 2;

    public static void translateBy(short translator, String text, DefaultListener defaultListener) {

        if (translator == GOOGLE_TRANSLATE) {

            WebApi.translateByGoogle(text, new DefaultListener() {
                @Override
                public void onSuccess(Object obj) {
                    try {
                        if (((Response<ResponseBody>) obj).body() != null) {

                            String result = ((Response<ResponseBody>) obj).body().string();
                            JSONArray array = new JSONArray(result);
                            JSONArray array_0 = array.getJSONArray(0);
                            StringBuilder translatedText = new StringBuilder();
                            for (int x = 0; x < array_0.length(); x++) {
                                JSONArray dynamic_object = array_0.getJSONArray(x);
                                if (!dynamic_object.isNull(0)) {
                                    translatedText.append(dynamic_object.getString(0));
                                }
                            }
                            Log.d("translated", translatedText.toString());
                            defaultListener.onSuccess(translatedText.toString());
                        }
                    } catch (IOException | JSONException e) {
                        defaultListener.onFailure(e);
                    }
                }

                @Override
                public void onFailure(Object obj) {
                    defaultListener.onFailure(obj);
                }
            });

        } else if (translator == YANDEX_TRANSLATE) {

            WebApi.translateByYandex(text, new DefaultListener() {
                @Override
                public void onSuccess(Object obj) {

                    try {
                        String result = ((Response<ResponseBody>) obj).body().string();
                        JSONObject jsonArray = new JSONObject(result);
                        if (jsonArray.getInt("code") == 200) {
                            String translatedText = jsonArray.getString("text");
                            defaultListener.onSuccess(translatedText);
                        } else {
                            defaultListener.onFailure(new Exception("json status code not 200"));
                        }
                    } catch (IOException | JSONException e) {
                        defaultListener.onFailure(e);
                    }

                }

                @Override
                public void onFailure(Object obj) {
                    defaultListener.onFailure(obj);
                }
            });

        } else if (translator == MICROSOFT_TRANSLATE) {

            WebApi.translateByMicrosoft(text, new DefaultListener() {
                @Override
                public void onSuccess(Object obj) {
                    try {
                        String result = ((Response<ResponseBody>) obj).body().string();
                        JSONArray array = new JSONArray(result);
                        JSONObject jsonObject = array.getJSONObject(0);
                        JSONArray jsonArray = jsonObject.getJSONArray("translations");
                        JSONObject object = jsonArray.getJSONObject(0);
                        String translatedText = object.getString("text");
                        defaultListener.onSuccess(translatedText);
                    } catch (IOException | JSONException e) {
                        defaultListener.onFailure(e);
                    }
                }

                @Override
                public void onFailure(Object obj) {
                    defaultListener.onFailure(obj);
                }
            });
        }
    }
}
