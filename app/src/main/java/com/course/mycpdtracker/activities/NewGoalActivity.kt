package com.course.mycpdtracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.course.mycpdtracker.databinding.ActivityNewGoalBinding

class NewGoalActivity : AppCompatActivity() {

    private var binding: ActivityNewGoalBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGoalBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarNewGoalActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarNewGoalActivity?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}