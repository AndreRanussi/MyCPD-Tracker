package com.course.mycpdtracker.database

import android.app.Application

class GoalApp: Application() {

    val db by lazy{
        GoalsDatabase.getInstance(this)
    }
}