/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 1/16/22, 4:31 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.tts;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

import ir.DEFINEit.StaticDatas;
import ir.DEFINEit.tools.language_manager.LanguageManager;
import ir.DEFINEit.tools.listeners.DefaultListener;
import ir.DEFINEit.tools.shared_helper.SharedSingle;

public class TTsSingle {

    private static TextToSpeech textToSpeech;

    public static final String MAN = "MAN";
    public static final String WOMAN = "WOMAN";

    public static final float TTS_MAN = 0.6f;
    public static final float TTS_WOMAN = 1.0f;

    public static void initialize(Context context, DefaultListener defaultListener) {
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(context, status -> {
                if (status == TextToSpeech.SUCCESS) defaultListener.onSuccess();
                else defaultListener.onFailure();
            });
        } else {
            defaultListener.onSuccess();
        }
    }

    public static boolean isSupportLanguage(Context context, String locale) {
        int result = textToSpeech.setLanguage(new Locale(locale));
        boolean supportedLanguage = !(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED);
        if (supportedLanguage) {
            if (!LanguageManager.getDefaultVoiceLanguage().equals(locale)) {
                LanguageManager.setDefaultVoiceLanguage(locale);
                Toast.makeText(context, "درحال بارگیری بسته صوتی زبان ...", Toast.LENGTH_SHORT).show();
            }
        }
        return supportedLanguage;
    }

    public static void speak(String text) {
        if (isMan()) {
            textToSpeech.setPitch(TTS_MAN);
        } else {
            textToSpeech.setPitch(TTS_WOMAN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public static boolean isMan() {
        if (SharedSingle.getSharedHelper().readString(StaticDatas.TTS).equals(""))
            SharedSingle.getSharedHelper().insert(StaticDatas.TTS, WOMAN);
        return SharedSingle.getSharedHelper().readString(StaticDatas.TTS).equals(MAN);
    }

    public static void setMan(boolean isMan) {
        if (isMan) SharedSingle.getSharedHelper().insert(StaticDatas.TTS, MAN);
        else SharedSingle.getSharedHelper().insert(StaticDatas.TTS, WOMAN);
    }

}