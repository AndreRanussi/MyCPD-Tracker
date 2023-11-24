package com.course.mycpdtracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.course.mycpdtracker.R
import com.course.mycpdtracker.database.GoalApp
import com.course.mycpdtracker.database.GoalsDao
import com.course.mycpdtracker.database.GoalsEntity
import com.course.mycpdtracker.databinding.ActivityNewGoalBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
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

        val timeFrame: Array<String> = resources.getStringArray(R.array.timeFrame)
        val timeFramaAdapter= ArrayAdapter(this, R.layout.dropdown_item, timeFrame)
        binding?.ddTimeFrame?.setAdapter(timeFramaAdapter)

        val category: Array<String> = resources.getStringArray(R.array.categories)
        val categoryAdapter = ArrayAdapter(this, R.layout.dropdown_item, category)
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

        val goalsDao = (application as GoalApp).db.goalDao()
        binding?.btnSave?.setOnClickListener {
            addToDatabase(goalsDao)

        }

        binding?.btnCancel?.setOnClickListener {
            finish()
        }



    }

    private fun addToDatabase(goalsDao: GoalsDao) {
        val goalTitle = binding?.etGoalTitle?.text.toString()

        var timeFrame = binding?.ddTimeFrame?.text.toString()
        if (timeFrame == "Short-Term" ||
            timeFrame == "Medium-Term" ||
            timeFrame == "Long-Term"
        ) {
        } else {
            binding?.ddTimeFrame?.text = null
            timeFrame = null.toString()
        }

        var category = binding?.ddCategory?.text.toString()
        if (category == "Personal" ||
            category == "Professional" ||
            category == "Academic"
        ) {
        } else {
            binding?.ddCategory?.text = null
            category = null.toString()
        }

        val goalDetails = binding?.etGoalDetails?.text.toString()
        val actionRequired = binding?.etActionRequired?.text.toString()
        val availableResources = binding?.etAvailableResources?.text.toString()
        val successCriteria = binding?.etSuccessCriteria?.text.toString()
        val startDate = binding?.etStartDate?.text.toString()
        val endDate = binding?.etEndDate?.text.toString()


        if(goalTitle.isBlank() ||
            timeFrame.isBlank() ||
            category.isBlank() ||
            goalDetails.isBlank() ||
            actionRequired.isBlank() ||
            availableResources.isBlank() ||
            successCriteria.isBlank() ||
            startDate.isBlank() ||
            endDate.isBlank()) {
            Toast.makeText(this@NewGoalActivity, "All fields must be completed before saving a new goal", Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch {
                goalsDao.insertGoal(
                    GoalsEntity(
                        goalTitle,
                        timeFrame,
                        category,
                        goalDetails,
                        actionRequired,
                        availableResources,
                        successCriteria,
                        startDate,
                        endDate,
                        false
                    )
                )

                Toast.makeText(this@NewGoalActivity, "New goal added", Toast.LENGTH_SHORT).show()
                finish()

            }

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