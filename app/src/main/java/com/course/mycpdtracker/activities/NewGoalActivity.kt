package com.course.mycpdtracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
    private var id = 0
    private var updateRecordFlag = false
    private var displayEditButtonFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGoalBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarNewGoalActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val goalsDao = (application as GoalApp).db.goalDao()

        id = intent.getIntExtra("id", 0)
        updateRecordFlag = (id > 0)

        if (updateRecordFlag) {
            supportActionBar?.title = "Goal"
            setViewToViewMode(goalsDao, id)
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

        binding?.btnSave?.setOnClickListener {
        if(updateRecordFlag) {
            updateRecord(goalsDao,id)
        }else{
                addToDatabase(goalsDao)}
        }

        binding?.btnCancel?.setOnClickListener {
            setViewToViewMode(goalsDao, id)
        }

     }






    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(updateRecordFlag) {
            menuInflater.inflate(R.menu.actionbar_menu, menu)
            val editIcon = menu?.findItem(R.id.action_edit)
            editIcon?.setVisible(displayEditButtonFlag)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            binding?.tiGoalTitle?.isEnabled = true
            binding?.tiCategory?.isEnabled = true
            binding?.tiTimeFrame?.isEnabled = true
            binding?.tiGoalDetails?.isEnabled = true
            binding?.tiActionRequired?.isEnabled = true
            binding?.tiAvailableResources?.isEnabled = true
            binding?.tiSuccessCriteria?.isEnabled = true
            binding?.tiStartDate?.isEnabled = true
            binding?.tiEndDate?.setEndIconDrawable(R.drawable.ic_date)
            binding?.tiStartDate?.setEndIconDrawable(R.drawable.ic_date)
            binding?.tiEndDate?.isEnabled = true
            binding?.btnSave?.visibility = View.VISIBLE
            binding?.btnCancel?.visibility = View.VISIBLE

            displayEditButtonFlag = false
            invalidateOptionsMenu()




     return true
    }


    private fun setViewToViewMode(goalsDao: GoalsDao, id: Int) {
        binding?.tiGoalTitle?.isEnabled = false
        binding?.tiCategory?.isEnabled = false
        binding?.tiTimeFrame?.isEnabled = false
        binding?.tiGoalDetails?.isEnabled = false
        binding?.tiActionRequired?.isEnabled = false
        binding?.tiAvailableResources?.isEnabled = false
        binding?.tiSuccessCriteria?.isEnabled = false
        binding?.tiStartDate?.isEnabled = false
        binding?.tiEndDate?.endIconDrawable = null
        binding?.tiStartDate?.endIconDrawable = null
        binding?.tiEndDate?.isEnabled = false
        binding?.btnSave?.visibility = View.GONE
        binding?.btnCancel?.visibility = View.GONE

        lifecycleScope.launch {
            val currentGoal = goalsDao.fetchGoalById(id)

            binding?.etGoalTitle?.setText(currentGoal.title)
            binding?.ddTimeFrame?.setText(currentGoal.timeFrame)
            binding?.ddCategory?.setText(currentGoal.category)
            binding?.etGoalDetails?.setText(currentGoal.goalDetails)
            binding?.etActionRequired?.setText(currentGoal.actionRequired)
            binding?.etAvailableResources?.setText(currentGoal.availableResources)
            binding?.etSuccessCriteria?.setText(currentGoal.successCriteria)
            binding?.etStartDate?.setText(currentGoal.startDate)
            binding?.etEndDate?.setText(currentGoal.endDate)
        }

        displayEditButtonFlag = true
        invalidateOptionsMenu()

    }



    private fun addToDatabase(goalsDao: GoalsDao) {
        val goalTitle = binding?.etGoalTitle?.text.toString()
        val timeFrame = binding?.ddTimeFrame?.text.toString()
        val category = binding?.ddCategory?.text.toString()
        val goalDetails = binding?.etGoalDetails?.text.toString()
        val actionRequired = binding?.etActionRequired?.text.toString()
        val availableResources = binding?.etAvailableResources?.text.toString()
        val successCriteria = binding?.etSuccessCriteria?.text.toString()
        val startDate = binding?.etStartDate?.text.toString()
        val endDate = binding?.etEndDate?.text.toString()

        if (isValidInput(goalTitle, timeFrame, category, goalDetails, actionRequired, availableResources, successCriteria, startDate, endDate)) {
            lifecycleScope.launch {
                try {
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
                } catch (e: Exception) {
                    Toast.makeText(this@NewGoalActivity, "Error adding goal", Toast.LENGTH_SHORT).show()
                    // Handle the exception as needed
                }
            }
        }
    }

    private fun isValidInput(
        goalTitle: String,
        timeFrame: String,
        category: String,
        goalDetails: String,
        actionRequired: String,
        availableResources: String,
        successCriteria: String,
        startDate: String,
        endDate: String
    ): Boolean {
        if (goalTitle.isBlank() || timeFrame.isBlank() || category.isBlank() ||
            goalDetails.isBlank() || actionRequired.isBlank() || availableResources.isBlank() ||
            successCriteria.isBlank() || startDate.isBlank() || endDate.isBlank()
        ) {
            Toast.makeText(this@NewGoalActivity, "All fields must be completed before saving a new goal", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun updateRecord(goalsDao: GoalsDao, id: Int) {
        val goalTitle = binding?.etGoalTitle?.text.toString()
        val timeFrame = binding?.ddTimeFrame?.text.toString()
        val category = binding?.ddCategory?.text.toString()
        val goalDetails = binding?.etGoalDetails?.text.toString()
        val actionRequired = binding?.etActionRequired?.text.toString()
        val availableResources = binding?.etAvailableResources?.text.toString()
        val successCriteria = binding?.etSuccessCriteria?.text.toString()
        val startDate = binding?.etStartDate?.text.toString()
        val endDate = binding?.etEndDate?.text.toString()

        if (isValidInput(goalTitle, timeFrame, category, goalDetails, actionRequired, availableResources, successCriteria, startDate, endDate)) {
            lifecycleScope.launch {
                try {
                    goalsDao.updateGoal(
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
                            false,
                            id
                        )
                    )
                    Toast.makeText(this@NewGoalActivity, "Record Updated Successfully", Toast.LENGTH_SHORT).show()
                    displayEditButtonFlag = true
                    invalidateOptionsMenu()
                    setViewToViewMode(goalsDao, id)
                } catch (e: Exception) {
                    Toast.makeText(this@NewGoalActivity, "Error editing record", Toast.LENGTH_SHORT).show()
                    // Handle the exception as needed
                }
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