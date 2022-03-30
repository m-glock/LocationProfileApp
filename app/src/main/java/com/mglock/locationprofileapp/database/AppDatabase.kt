package com.mglock.locationprofileapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mglock.locationprofileapp.database.daos.*
import com.mglock.locationprofileapp.database.entities.*
import com.mglock.locationprofileapp.util.enums.DetailActionTitle

@Database(entities = [
    Place::class,
    Profile::class,
    ProfileDetailAction::class,
    DetailAction::class,
    Timeframe::class
], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun profileDao(): ProfileDao
    abstract fun profileDetailActionDao(): ProfileDetailActionDao
    abstract fun detailActionDao(): DetailActionDao
    abstract fun timeframeDao(): TimeframeDao

    companion object {
        // For Singleton instantiation
        @Volatile private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "database.db"

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME)
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            getInstance(context).detailActionDao().insertAll(*PREPOPULATE_DETAIL_ACTIONS)
                        }
                    }
                })
                .build()

        private val PREPOPULATE_DETAIL_ACTIONS = Array(DetailActionTitle.values().size){ position ->
            DetailAction(0, DetailActionTitle.values()[position].title, false)
        }
    }
}