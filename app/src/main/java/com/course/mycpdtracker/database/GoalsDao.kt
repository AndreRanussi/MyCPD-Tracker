package com.course.mycpdtracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalsDao {

    @Upsert
    suspend fun insertUpdateGoal(goalsEntity: GoalsEntity)

    @Delete
    suspend fun deleteGoal(goalsEntity: GoalsEntity)

   @Query("SELECT * FROM goalsentity ORDER BY startDate")
   fun fetchAllGoals(): Flow<List<GoalsEntity>>

    @Query("SELECT * FROM GoalsEntity WHERE id = :id")
    suspend fun fetchGoalById(id: Int): GoalsEntity

}
