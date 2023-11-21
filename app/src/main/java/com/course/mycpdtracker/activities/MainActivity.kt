package com.course.mycpdtracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.course.mycpdtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.btnAddGoal?.setOnClickListener{
            var i = Intent(this, NewGoalActivity::class.java)
            startActivity(i)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}