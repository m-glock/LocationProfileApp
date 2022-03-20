package com.mglock.locationprofileapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mglock.locationprofileapp.database.daos.*
import com.mglock.locationprofileapp.database.entities.*

@Database(entities = [
    ActionGroup::class,
    Place::class,
    Profile::class,
    ProfileAction::class,
    SubAction::class,
    Timeframe::class,
    TimeframeWeekday::class
], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun actionGroupDao(): ActionGroupDao
    abstract fun placeDao(): PlaceDao
    abstract fun profileDao(): ProfileDao
    abstract fun profileActionDao(): ProfileActionDao
    abstract fun subActionDao(): SubActionDao
    abstract fun timeframeDao(): TimeframeDao
    abstract fun timeframeWeekdayDao(): TimeframeWeekdayDao

    companion object {
        // For Singleton instantiation
        @Volatile private var INSTANCE: AppDatabase? = null
        private const val DATABASE_NAME = "database_name"

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "Example.db")
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            getInstance(context).actionGroupDao().insertAll(*PREPOPULATE_ACTION_GROUP)
                            getInstance(context).subActionDao().insertAll(*PREPOPULATE_SUB_ACTIONS)
                        }
                    }
                })
                .build()

        //TODO
        private val PREPOPULATE_ACTION_GROUP = arrayOf(
            ActionGroup(0, "Wi-Fi", false),
            ActionGroup(0, "Volume", false),
            ActionGroup(0, "Mobile Data", false),
            ActionGroup(0, "Screen Brightness", false),
            ActionGroup(0, "Notifications", false),
            ActionGroup(0, "Location", false),
            ActionGroup(0, "Bluetooth", false)
        )
        //TODO figure out the important ones
        private val PREPOPULATE_SUB_ACTIONS = arrayOf(
            SubAction(0, "Volume on/off", 2),
            SubAction(0, "Change Ringtone", 2),
            SubAction(0, "Change Notification Tone", 2)
        )
    }
}