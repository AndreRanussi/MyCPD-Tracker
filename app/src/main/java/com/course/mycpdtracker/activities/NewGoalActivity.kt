package com.course.mycpdtracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.course.mycpdtracker.R
import com.course.mycpdtracker.databinding.ActivityNewGoalBinding
import com.google.android.material.datepicker.CalendarConstraints
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
            val constraintsBuilder = CalendarConstraints.Builder()
                .setStart(parseDateStringToMilliseconds("01/01/2020"))
                .setEnd(parseDateStringToMilliseconds("01/01/2030"))
                .build()
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.DatePickerTheme)
                .setTitleText("Select a Start Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder)
                .build()

            datePicker.show(this.supportFragmentManager, "TAG")

            datePicker.addOnPositiveButtonClickListener {
               binding?.etStartDate?.setText(parseMillisecondsToDateFormatter(it))
           }

        }

        binding?.tiEndDate?.setEndIconOnClickListener{
            val constraintBuilder = CalendarConstraints.Builder()
                .setStart(parseDateStringToMilliseconds("01/01/2020"))
                .setEnd(parseDateStringToMilliseconds("01/01/2030"))
                .build()
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTheme(R.style.DatePickerTheme)
                .setTitleText("Select an End Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintBuilder)
                .build()

            datePicker.show(this.supportFragmentManager, "TAG")

            datePicker.addOnPositiveButtonClickListener {
                binding?.etEndDate?.setText(parseMillisecondsToDateFormatter(it))
            }
        }

        binding?.btnSave?.setOnClickListener {



        }



    }


    private fun parseMillisecondsToDateFormatter(milliseconds : Long): String {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = milliseconds
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(utc.timeInMillis)
    }
    private fun parseDateStringToMilliseconds(dateString: String): Long {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = sdf.parse(dateString)
        return date.time
    }










    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}