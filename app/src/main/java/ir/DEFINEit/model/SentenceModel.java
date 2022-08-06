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

@Entity(tableName = "sentence_tb")
@TypeConverters({DateConverter.class})
public class SentenceModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "segment")
    private String segment;

    @ColumnInfo(name = "translation")
    private String translation;

    @ColumnInfo(name = "view_count")
    private int viewCount = 1;

    @ColumnInfo(name = "view_time")
    private Date view_time;

    public SentenceModel(String segment, String translation, Date view_time) {
        this.segment = segment;
        this.translation = translation;
        this.view_time = view_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
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
}
