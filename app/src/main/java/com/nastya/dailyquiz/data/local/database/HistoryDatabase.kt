package com.nastya.dailyquiz.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nastya.dailyquiz.data.local.dao.HistoryDao
import com.nastya.dailyquiz.data.local.entity.History

@Database(
    entities = [History::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class HistoryDatabase : RoomDatabase()  {
    abstract val historyDao: HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "quiz_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}