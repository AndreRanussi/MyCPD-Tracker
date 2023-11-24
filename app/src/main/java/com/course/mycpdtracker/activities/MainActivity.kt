package com.course.mycpdtracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.course.mycpdtracker.database.GoalApp
import com.course.mycpdtracker.database.GoalsDao
import com.course.mycpdtracker.database.GoalsEntity
import com.course.mycpdtracker.databinding.ActivityMainBinding
import com.course.mycpdtracker.recyclerview.ItemAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.btnAddGoal?.setOnClickListener {
            val i = Intent(this, NewGoalActivity::class.java)
            startActivity(i)
        }

        val goalsDao = (application as GoalApp).db.goalDao()
        fetchAllGoals(goalsDao)


    }

    private fun fetchAllGoals(goalsDao: GoalsDao) {
        lifecycleScope.launch {
            goalsDao.fetchAllGoals().collect() { goals ->
                val list = ArrayList(goals)
                setUpListOfGoalsOnRecycleView(list, goalsDao)

            }
        }
    }

    private fun setUpListOfGoalsOnRecycleView(goalsList:ArrayList<GoalsEntity>, goalsDao: GoalsDao, ) {
        if (goalsList.isNotEmpty()){
         val itemAdapter = ItemAdapter(goalsList) { id, completed ->
             goalsDao.updateCompletedStatus(id,completed)


             Toast.makeText(this@MainActivity, "ID:$id, STATUS: $completed", Toast.LENGTH_SHORT).show()

             }
          binding?.rvGoalView?.layoutManager = LinearLayoutManager(this@MainActivity)
            binding?.rvGoalView?.adapter = itemAdapter
             }
    }






    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}

