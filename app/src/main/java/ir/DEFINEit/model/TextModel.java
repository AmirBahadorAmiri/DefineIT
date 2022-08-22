/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 6:00 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import ir.DEFINEit.tools.database.DateConverter;

@Entity(tableName = "text_tb")
@TypeConverters({DateConverter.class})
public class TextModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "main_text")
    private String text;

    @ColumnInfo(name = "translation_text")
    private String translation;

    @ColumnInfo(name = "translation_time")
    private long translationTime;

    @ColumnInfo(name = "from_language_code")
    private String fromLanguageCode;

    @ColumnInfo(name = "to_language_code")
    private String toLanguageCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
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

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public long getTranslationTime() {
        return translationTime;
    }

    public void setTranslationTime(long translationTime) {
        this.translationTime = translationTime;
    }
}
