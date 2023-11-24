package com.course.mycpdtracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalsDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertGoal(goalsEntity: GoalsEntity)

    @Update(onConflict = REPLACE)
    suspend fun updateGoal(goalsEntity: GoalsEntity)

    @Delete
    suspend fun deleteGoal(goalsEntity: GoalsEntity)

   @Query("SELECT * FROM goalsentity ORDER BY startDate")
   fun fetchAllGoals(): Flow<List<GoalsEntity>>

    @Query("SELECT * FROM GoalsEntity WHERE id = :id")
     fun fetchGoalById(id: Int): Flow<GoalsEntity>


}
