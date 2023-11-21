package com.course.mycpdtracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.course.mycpdtracker.R
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

        var timeFrame: Array<String> = resources.getStringArray(R.array.timeFrame)
        var timeFramaAdapter= ArrayAdapter(this, R.layout.dropdown_item, timeFrame)
        binding?.ddTimeFrame?.setAdapter(timeFramaAdapter)

        var category: Array<String> = resources.getStringArray(R.array.categories)
        var categoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, category)
        binding?.ddCategory?.setAdapter(categoryAdapter)



    }






    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}