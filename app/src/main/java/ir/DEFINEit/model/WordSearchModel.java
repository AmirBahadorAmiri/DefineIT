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

@Entity(tableName = "word_tb")
@TypeConverters({DateConverter.class})
public class WordSearchModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "persian_word")
    private String persianWord;

    @ColumnInfo(name = "english_word")
    private String englishWord;

    @ColumnInfo(name = "favorite")
    private Boolean favorite = false;

    @ColumnInfo(name = "view_count")
    private int viewCount = 0;

    @ColumnInfo(name = "view_time")
    private Date view_time;

    @ColumnInfo(name = "favorite_time")
    private Date favorite_time;

    public WordSearchModel(String persianWord, String englishWord) {
        this.persianWord = persianWord;
        this.englishWord = englishWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersianWord() {
        return persianWord;
    }

    public void setPersianWord(String persianWord) {
        this.persianWord = persianWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Date getView_time() {
        return view_time;
    }

    public void setView_time(Date view_time) {
        this.view_time = view_time;
    }

    public Date getFavorite_time() {
        return favorite_time;
    }

    public void setFavorite_time(Date favorite_time) {
        this.favorite_time = favorite_time;
    }
}
