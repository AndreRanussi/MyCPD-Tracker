package com.course.mycpdtracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Goals(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
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
)
