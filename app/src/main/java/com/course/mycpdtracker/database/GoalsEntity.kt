package com.course.mycpdtracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class GoalsEntity(

    val title: String,
    val timeFrame: String,
    val category: String,
    val goalDetails: String,
    val actionRequired: String,
    val availableResources: String,
    val successCriteria: String,
    val startDate: String,
    val endDate: String,
    val completed: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id:Int? =null,
)
