/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 1/28/22, 4:57 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.language_manager;

import ir.DEFINEit.StaticDatas;
import ir.DEFINEit.tools.country.Country;
import ir.DEFINEit.tools.shared_helper.SharedSingle;

public class LanguageManager {

    public static void setDefaultVoiceLanguage(String locale) {
        SharedSingle.getSharedHelper().insert(StaticDatas.DEFAULT_VOICE_PACK, locale);
    }

    public static String getDefaultVoiceLanguage() {
        if (SharedSingle.getSharedHelper().readString(StaticDatas.DEFAULT_VOICE_PACK).isEmpty())
            setDefaultVoiceLanguage("en");
        return SharedSingle.getSharedHelper().readString(StaticDatas.DEFAULT_VOICE_PACK);
    }

    public static String getFromLangauge() {
        if (SharedSingle.getSharedHelper().readInt(StaticDatas.TRANSLATE_FROM) == 0)
            setFromLangauge(0);
        return Country.generateLanguage().get(SharedSingle.getSharedHelper().readInt(StaticDatas.TRANSLATE_FROM) - 1).getName();
    }

    public static String getToLangauge() {
        if (SharedSingle.getSharedHelper().readInt(StaticDatas.TRANSLATE_TO) == 0)
            setToLangauge(1);
        return Country.generateLanguage().get(SharedSingle.getSharedHelper().readInt(StaticDatas.TRANSLATE_TO) - 1).getName();
    }

    public static String getFromLangaugeCode() {
        return Country.generateLanguage().get(SharedSingle.getSharedHelper().readInt(StaticDatas.TRANSLATE_FROM) - 1).getCode();
    }

    public static String getToLangaugeCode() {
        return Country.generateLanguage().get(SharedSingle.getSharedHelper().readInt(StaticDatas.TRANSLATE_TO) - 1).getCode();
    }

    public static void reverceLanguage() {
        int to = SharedSingle.getSharedHelper().readInt(StaticDatas.TRANSLATE_TO) - 1;
        int from = SharedSingle.getSharedHelper().readInt(StaticDatas.TRANSLATE_FROM) - 1;

        setFromLangauge(to);
        setToLangauge(from);
    }

    public static void setFromLangauge(int pos) {
        SharedSingle.getSharedHelper().insert(StaticDatas.TRANSLATE_FROM, pos + 1);
    }

    public static void setToLangauge(int pos) {
        SharedSingle.getSharedHelper().insert(StaticDatas.TRANSLATE_TO, pos + 1);
    }

}
