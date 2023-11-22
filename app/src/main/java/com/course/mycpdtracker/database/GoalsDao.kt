package com.course.mycpdtracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalsDao {

    @Upsert
    suspend fun insertGoal(goals: Goals)

    @Delete
    suspend fun deleteGoal(goals: Goals)

   @Query("SELECT * FROM goals ORDER BY startDate")
   fun getGoals(): Flow<List<Goals>>

    @Query("SELECT * FROM Goals WHERE id = id" )
    fun getContactById(id: Int): Goals
}
