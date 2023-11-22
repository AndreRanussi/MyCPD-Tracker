package com.course.mycpdtracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.course.mycpdtracker.R
import com.course.mycpdtracker.databinding.ActivityNewGoalBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

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



        binding?.tiStartDate?.setEndIconOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.DatePickerTheme)
                .setTitleText("Select a Start Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.show(this. supportFragmentManager, "TAG")

           datePicker.addOnPositiveButtonClickListener {
               binding?.etStartDate?.setText(milisescondsToDateFromater(it))
           }

        }



    }

    fun milisescondsToDateFromater(miliseconds: Long): String {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = miliseconds
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(utc.timeInMillis)
    }






    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}