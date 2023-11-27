package com.course.mycpdtracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goalsEntity: GoalsEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGoal(goalsEntity: GoalsEntity)

    @Query("UPDATE GoalsEntity SET completed = :completed WHERE id = :id")
    suspend fun updateCompletedStatus (id: Int, completed: Boolean)

    @Delete
    suspend fun deleteGoal(goalsEntity: GoalsEntity)

    @Query("SELECT * FROM GoalsEntity ORDER BY endDate DESC")
    fun fetchAllGoals(): Flow<List<GoalsEntity>>

    @Query("SELECT * FROM GoalsEntity WHERE id = :id")
    suspend fun fetchGoalById(id: Int): GoalsEntity


}
