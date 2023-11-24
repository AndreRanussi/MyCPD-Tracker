package com.course.mycpdtracker.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.course.mycpdtracker.R
import com.course.mycpdtracker.database.GoalsEntity
import com.course.mycpdtracker.databinding.RecycleViewBoardTemplateBinding
import org.apache.commons.lang3.math.NumberUtils

class ItemAdapter(private val items: ArrayList<GoalsEntity>,
                  private val checkBoxCompletedListener: (id:Int, completed:Boolean) -> Unit): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    inner class ViewHolder (binding: RecycleViewBoardTemplateBinding) : RecyclerView.ViewHolder(binding.root){
        val tvTitle = binding.tvGoalTitle
        val tvStart = binding.tvStartDate
        val tvEndDate = binding.tvEndDate
        val cbCompleted = binding.cbCompleted
        val ivStatusCircle = binding.ivStatusCircle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecycleViewBoardTemplateBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        holder.tvTitle.text = item.title
        holder.tvStart.text = item.startDate
        holder.tvEndDate.text = item.endDate
        holder.cbCompleted.isChecked = item.completed
        holder.cbCompleted.setOnClickListener{
            checkBoxCompletedListener.invoke(item.id, (it as CheckBox).isChecked)
            notifyDataSetChanged()

        }



        if(item.completed) {
            holder.ivStatusCircle.setImageResource(R.drawable.goal_status_circle_green)
        } else {
            holder.ivStatusCircle.setImageResource(R.drawable.goal_status_circle_red)
        }
    }



}