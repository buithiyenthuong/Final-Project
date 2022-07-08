package com.example.life.management.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.life.management.R
import com.example.life.management.model.Plan
import com.example.life.management.model.PlanAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailPlanActivity : AppCompatActivity() {
    private var tvTitle: TextView?= null
    private var tvContent: TextView?= null
    private var tvTime: TextView?= null
    private var btnDelete: Button?= null
    private lateinit var plan: Plan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_plan)

        tvTitle = findViewById(R.id.tvTitle)
        tvTime = findViewById(R.id.tvTime)
        tvContent = findViewById(R.id.tvContent)
        btnDelete = findViewById(R.id.btnDelete)

        intent?.getSerializableExtra("PLAN")?.let {
            plan = it as Plan
            tvTime?.text = "${plan.timeStart} đến ${plan.timeEnd}"
            tvTitle?.text = "${plan.title}"
            tvContent?.text = "${plan.content}"
        }

        btnDelete?.setOnClickListener {
            val pref = getSharedPreferences("PREF_LIFE_MANAGEMENT", MODE_PRIVATE)
            val gson = Gson()
            if(!pref.getString("LIST_MANAGEMENT", "").isNullOrEmpty()) {
                val itemType = object: TypeToken<ArrayList<Plan>>() {}.type
                gson.fromJson<ArrayList<Plan>>(pref.getString("LIST_MANAGEMENT", ""), itemType)?.let {
                    if(it.size > 0 && it.contains(plan)) {
                        it.remove(plan)
                        pref.edit().putString("LIST_MANAGEMENT", gson.toJson(it)).apply()
                        Toast.makeText(this, "Xoá kế hoạch thành công!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }

        findViewById<ImageView>(R.id.icBack).setOnClickListener { onBackPressed() }
    }
}