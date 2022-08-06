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
import ir.DEFINEit.model.SentenceModel;

@Dao
public interface SentenceDao {

    @Insert
    Completable insert(SentenceModel sentence);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable update(SentenceModel sentence);

    @Delete
    Completable delete(SentenceModel sentence);

    @Query("SELECT * FROM sentence_tb")
    Single<List<SentenceModel>> readAllSentence();

    @Query("DELETE FROM sentence_tb")
    Completable deleteAllSentence();

}
