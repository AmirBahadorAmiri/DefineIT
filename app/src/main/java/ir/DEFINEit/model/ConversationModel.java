/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 6:00 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.model;

public class ConversationModel {

    String text, translate, fromLanguageCode, toLanguageCode;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getFromLanguageCode() {
        return fromLanguageCode;
    }

    public void setFromLanguageCode(String fromLanguageCode) {
        this.fromLanguageCode = fromLanguageCode;
    }

    public String getToLanguageCode() {
        return toLanguageCode;
    }

    public void setToLanguageCode(String toLanguageCode) {
        this.toLanguageCode = toLanguageCode;
    }
}
