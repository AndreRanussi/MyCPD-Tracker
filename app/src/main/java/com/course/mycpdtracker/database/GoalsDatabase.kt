package com.course.mycpdtracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [GoalsEntity::class],
    version = 1
)
abstract class GoalsDatabase: RoomDatabase() {

    abstract fun goalDao():GoalsDao


    companion object{

        @Volatile
        private var INSTANCE: GoalsDatabase? = null

        fun getInstance(context: Context):GoalsDatabase {
            var instance = INSTANCE


            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    GoalsDatabase::class.java,
                    name = "goal_database")
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
            }

            return instance
        }

    }
}