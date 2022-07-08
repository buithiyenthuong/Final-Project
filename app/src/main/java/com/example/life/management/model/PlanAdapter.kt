package com.example.life.management.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.life.management.R

class PlanAdapter(private val list: ArrayList<Plan>, private val onItemClick: (Plan) -> Unit): RecyclerView.Adapter<PlanAdapter.PlanViewHolder>() {
    class PlanViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private var tvTitle: TextView?= null
        private var tvContent: TextView?= null
        private var tvTime: TextView?= null

        init {
            tvTitle = view.findViewById(R.id.tvTitle)
            tvTime = view.findViewById(R.id.tvTime)
            tvContent = view.findViewById(R.id.tvContent)
        }

        fun bindData(plan: Plan) {
            tvTime?.text = "${plan.timeStart} đến ${plan.timeEnd}"
            tvTitle?.text = "${plan.title}"
            tvContent?.text = "${plan.content}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plan, parent, false)
        return PlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        holder.bindData(list[position])
        holder.itemView.setOnClickListener {
            onItemClick.invoke(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}