package com.course.mycpdtracker.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Goals::class],
    version = 1
)
abstract class GoalsDatabase: RoomDatabase() {

    abstract val dao: GoalsDao
}