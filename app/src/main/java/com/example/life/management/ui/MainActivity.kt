package com.example.life.management.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.life.management.R
import com.example.life.management.model.Plan
import com.example.life.management.model.PlanAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private var tvNoData: TextView?= null
    private var rvPlan: RecyclerView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvNoData = findViewById(R.id.tvNoData)
        rvPlan = findViewById(R.id.rvPlan)
        findViewById<CardView>(R.id.cardAdd).setOnClickListener {
            startActivity( Intent(this, AddNewPlanActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        val pref = getSharedPreferences("PREF_LIFE_MANAGEMENT", MODE_PRIVATE)
        val gson = Gson()
        if(pref.getString("LIST_MANAGEMENT", "").isNullOrEmpty()) {
            tvNoData?.visibility = View.VISIBLE
            rvPlan?.visibility = View.GONE
        } else {
            val itemType = object: TypeToken<ArrayList<Plan>>() {}.type
            gson.fromJson<ArrayList<Plan>>(pref.getString("LIST_MANAGEMENT", ""), itemType)?.let {
                if(it.size > 0) {
                    tvNoData?.visibility = View.GONE
                    rvPlan?.visibility = View.VISIBLE
                    rvPlan?.adapter = PlanAdapter(it) {
                        val intent = Intent(this, DetailPlanActivity::class.java)
                        intent.putExtra("PLAN", it)
                        startActivity(intent)
                    }
                } else {
                    tvNoData?.visibility = View.VISIBLE
                    rvPlan?.visibility = View.GONE
                }
            }
        }
    }
}