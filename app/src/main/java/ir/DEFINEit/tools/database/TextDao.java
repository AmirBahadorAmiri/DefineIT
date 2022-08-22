/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 6/3/22, 12:07 AM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ir.DEFINEit.model.TextModel;

@Dao
public interface TextDao {

    @Insert
    Completable insert(TextModel text);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable update(TextModel text);

    @Delete
    Completable delete(TextModel text);

    @Query("SELECT * FROM text_tb order by translation_time DESC")
    Single<List<TextModel>> readAllText();

    @Query("DELETE FROM text_tb")
    Completable deleteAllText();

}
