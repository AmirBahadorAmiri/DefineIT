/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 6/3/22, 12:07 AM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.tools.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import ir.DEFINEit.model.SentenceModel;
import ir.DEFINEit.model.WordSearchModel;

@Database(entities = {WordSearchModel.class, SentenceModel.class}, version = 3, exportSchema = false)
public abstract class DBM extends RoomDatabase {

    private static DBM DBM;
//
//    public static void copyDatabase(Context context) {
//
//        String database_path = "data/data/" + context.getPackageName() + "/databases/";
//        String database_name = "words";
//
//        try {
//            File dir = new File(database_path);
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//
//            File file = new File(database_path, database_name);
//            if (!file.exists()) {
//
//                OutputStream outputStream = new FileOutputStream(database_path + database_name);
//                byte[] buffer = new byte[1024];
//                int lenght;
//                InputStream inputStream = context.getAssets().open("database/words");
//                while ((lenght = inputStream.read(buffer)) > 0) {
//                    outputStream.write(buffer, 0, lenght);
//                }
//                inputStream.close();
//                outputStream.flush();
//                outputStream.close();
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static DBM getDB(Context context) {
        if (DBM == null)
            DBM = Room.databaseBuilder(context, DBM.class, "words")
                    .createFromAsset("database/words")
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("ALTER TABLE word_tb Add Column view_time INTEGER");
                            database.execSQL("ALTER TABLE word_tb Add Column favorite_time INTEGER");
                        }
                    })
                    .addMigrations(new Migration(2, 3) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("ALTER TABLE sentence_tb Add Column view_count INTEGER default 0 not null");
                            database.execSQL("ALTER TABLE sentence_tb Add Column view_time INTEGER");
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build();

        /*
        *
        * .addMigrations(new Migration(1,2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("CREATE TABLE 'sentence_tb' ('id' INTEGER Not null, 'segment' Text, 'translation' Text , PRIMARY KEY('id'))");
                        }
                    })
        *
        * */

        return DBM;
    }

    public abstract WordDao getWordDao();

    public abstract SentenceDao getSentenceDao();

}