/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/31/22, 8:39 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.country;

import java.util.ArrayList;
import java.util.List;

import ir.DEFINEit.model.LangModel;

public class Country {

    private static List<LangModel> langModels;

    public static List<LangModel> generateLanguage() {
        if (langModels == null) {
            langModels = new ArrayList<>();
            langModels.add(new LangModel("فارسی", "fa", "ic_iran.png"));
            langModels.add(new LangModel("انگلیسی", "en-GB", "ic_united_kingdom.png"));
            langModels.add(new LangModel("آمریکایی", "en-US", "ic_usa.png"));
            langModels.add(new LangModel("فرانسوی", "fr", "ic_france.png"));
            langModels.add(new LangModel("آلمانی", "de", "ic_germany.png"));
            langModels.add(new LangModel("ایتالیایی", "it", "ic_italy.png"));
            langModels.add(new LangModel("اسپانیایی", "es", "ic_spain.png"));
            langModels.add(new LangModel("هلندی", "nl", "ic_netherlands.png"));
            langModels.add(new LangModel("عربی", "ar", "ic_saudi.png"));
            langModels.add(new LangModel("ترکیه ای", "tr", "ic_turkey.png"));
            langModels.add(new LangModel("چینی", "zh", "ic_china.png"));
            langModels.add(new LangModel("روسی", "ru", "ic_russia.png"));
            langModels.add(new LangModel("کره ای", "ko", "ic_south_korea.png"));
            langModels.add(new LangModel("ارمنی", "hy", "ic_armenia.png"));
            langModels.add(new LangModel("استرالیایی", "en-AU", "ic_australia.png"));
            langModels.add(new LangModel("آذربایجانی", "az", "ic_azerbaijan.png"));
            langModels.add(new LangModel("بنگلادشی", "bn", "ic_bangladesh.png"));
            langModels.add(new LangModel("بوسنیه ای", "bs", "ic_bosnia.png"));
            langModels.add(new LangModel("بلغاری", "bg", "ic_bulgaria.png"));
            langModels.add(new LangModel("کمبوجیه ای", "km", "ic_cambodia.png"));
            langModels.add(new LangModel("کانادایی", "en-CA", "ic_canada.png"));
            langModels.add(new LangModel("کرواسی", "hr", "ic_croatia.png"));
            langModels.add(new LangModel("چکی", "cs", "ic_czech.png"));
            langModels.add(new LangModel("دانمارکی", "da", "ic_denmark.png"));
            langModels.add(new LangModel("استونی", "et", "ic_estonia.png"));
            langModels.add(new LangModel("فنلاندی", "fi", "ic_finland.png"));
            langModels.add(new LangModel("گرجی", "ka", "ic_georgia.png"));
            langModels.add(new LangModel("یونانی", "el", "ic_greece.png"));
            langModels.add(new LangModel("مجارستانی", "hu", "ic_hungary.png"));
            langModels.add(new LangModel("هندی", "hi", "ic_india.png"));
            langModels.add(new LangModel("اندونزیایی", "id", "ic_indonesia.png"));
            langModels.add(new LangModel("ایرلندی", "en-ie", "ic_ireland.png"));
            langModels.add(new LangModel("اسرائیلی", "he-IL", "ic_israel.png"));
            langModels.add(new LangModel("ژاپنی", "ja", "ic_japan.png"));
            langModels.add(new LangModel("مالزیایی", "ms", "ic_malaysia.png"));
            langModels.add(new LangModel("مکزیکی", "es-MX", "ic_mexico.png"));
            langModels.add(new LangModel("نپالی", "ne", "ic_nepal.png"));
            langModels.add(new LangModel("نروژی", "no", "ic_norway.png"));
            langModels.add(new LangModel("پاکستانی", "en-PK", "ic_pakistan.png"));
            langModels.add(new LangModel("لهستانی", "pl", "ic_poland.png"));
            langModels.add(new LangModel("پرتغالی", "pt", "ic_portugal.png"));
            langModels.add(new LangModel("رومانیایی", "ro", "ic_romania.png"));
            langModels.add(new LangModel("صربستانی", "sr", "ic_serbia.png"));
            langModels.add(new LangModel("اسلواکیایی", "sk", "ic_slovakia.png"));
            langModels.add(new LangModel("سوئدی", "sv", "ic_sweden.png"));
            langModels.add(new LangModel("تایوانی", "zh-tw", "ic_taiwan.png"));
            langModels.add(new LangModel("تایلندی", "th", "ic_thailand.png"));
            langModels.add(new LangModel("اکراینی", "uk", "ic_ukraine.png"));
            langModels.add(new LangModel("ازبکی", "uz", "ic_uzbekistn.png"));
            langModels.add(new LangModel("ویتنامی", "vi", "ic_vietnam.png"));
            langModels.add(new LangModel("ولزی", "cy", "ic_wales.png"));
        }
        return langModels;
    }

}
