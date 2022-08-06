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
import ir.DEFINEit.model.WordSearchModel;

@Dao
public interface WordDao {

    @Insert
    Completable insert(WordSearchModel word);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable update(WordSearchModel word);

    @Delete
    Completable delete(WordSearchModel word);

    @Query("delete from word_tb")
    Completable deleteAllWords();

    //    @Query("SELECT * FROM word_tb WHERE english_word like '%' || :persian || '%' limit 15")
    @Query("SELECT * FROM word_tb WHERE english_word like '%' || :persian || '%' ORDER BY :persian ASC")
    Single<List<WordSearchModel>> readEnglishWord(String persian);

    //    @Query("SELECT * FROM word_tb WHERE persian_word like '%' || :english || '%' limit 15")
    @Query("SELECT * FROM word_tb WHERE persian_word like '%' || :english || '%' ORDER BY :english DESC")
    Single<List<WordSearchModel>> readPersianWord(String english);

    //    @Query("SELECT * FROM word_tb WHERE favorite = 1 ORDER BY fav_date DESC")
    //    @Query("SELECT * FROM word_tb WHERE favorite = 1 ORDER BY english_word")
    @Query("SELECT * FROM word_tb WHERE favorite = 1 ORDER BY favorite_time DESC")
    Single<List<WordSearchModel>> readFavorites();

    @Query("SELECT * FROM word_tb")
    Single<List<WordSearchModel>> getAll();

    @Query("SELECT * FROM word_tb WHERE view_count > 0 ORDER BY view_time DESC")
    Single<List<WordSearchModel>> readHistories();

    @Query("update word_tb set view_count = 0 , view_time = 0 where view_count > 0")
    Completable clearHistories();

    @Query("update word_tb set view_count = 0 , view_time = 0 where id = :number")
    Completable clearFromHistories(int number);

    @Query("update word_tb set favorite = 0 where id = :number")
    Completable clearFromFavorites(int number);

    @Query("update word_tb set favorite = 0 where favorite = 1")
    Completable clearFavorites();

    @Query("select * from word_tb limit :number")
    Single<List<WordSearchModel>> getDefaultWord(int number);

    @Query("select * from word_tb where id = :number")
    Single<WordSearchModel> getWordById(int number);

}
