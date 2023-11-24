package com.course.mycpdtracker.recyclerview

import android.animation.ValueAnimator.AnimatorUpdateListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.course.mycpdtracker.R
import com.course.mycpdtracker.database.GoalsEntity
import com.course.mycpdtracker.databinding.RecycleViewBoardTemplateBinding

class ItemAdapter(private val items: ArrayList<GoalsEntity>
): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

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
        val context = holder.itemView.context
        val item = items[position]

        holder.tvTitle.text = item.title
        holder.tvStart.text = item.startDate
        holder.tvEndDate.text = item.endDate
        holder.cbCompleted.isChecked = item.completed

        if(item.completed) {
            holder.ivStatusCircle.setImageResource(R.drawable.goal_status_circle_green)
        } else {
            holder.ivStatusCircle.setImageResource(R.drawable.goal_status_circle_red)
        }

    }


}