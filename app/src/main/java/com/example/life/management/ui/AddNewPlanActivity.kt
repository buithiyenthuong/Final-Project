package com.example.life.management.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.life.management.R
import com.example.life.management.model.Plan
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

class AddNewPlanActivity : AppCompatActivity() {
    private var dateStart: TextView?= null
    private var dateEnd: TextView?= null
    private var startDate: String?= null
    private var endDate: String?= null
    private var yearStart: Int?= null
    private var monthStart: Int?= null
    private var dayStart: Int?= null
    private var etTitle: EditText?= null
    private var etContent: EditText?= null

    private var btnCreate: Button?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_plan)

        dateStart = findViewById(R.id.tvStartDate)
        dateEnd = findViewById(R.id.tvEndDate)
        btnCreate = findViewById(R.id.btnCreate)
        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)

        dateStart?.setOnClickListener {
            endDate = null
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH) + 1
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(this,
                { view, year, month, dayOfMonth ->
                    startDate = "$dayOfMonth/${month + 1}/$year"
                    yearStart = year
                    monthStart = month
                    dayStart = dayOfMonth
                    dateStart?.text = startDate
                }, year, month, day)

            datePicker.show()
        }

        dateEnd?.setOnClickListener {
            if(startDate.isNullOrEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ngày bắt đầu", Toast.LENGTH_LONG).show()
            } else {
                val datePicker = DatePickerDialog(this,
                    { view, year, month, dayOfMonth ->
                        endDate = "$dayOfMonth/${month+1}/$year"
                        dateEnd?.text = endDate
                    }, yearStart!!, monthStart!!, dayStart!!)

                val startCal = Calendar.getInstance()
                startCal.set(Calendar.YEAR, yearStart!!)
                startCal.set(Calendar.MONTH, monthStart!!)
                startCal.set(Calendar.DAY_OF_MONTH, dayStart!!)
                datePicker.datePicker.minDate = startCal.timeInMillis
                datePicker.show()
            }

        }

        btnCreate?.setOnClickListener {
            if(startDate.isNullOrEmpty() || endDate.isNullOrEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ngày bắt đầu và ngày kết thúc", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(etTitle?.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tiêu đề", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(etContent?.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Vui lòng nhập nội dung", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val plan = Plan(startDate!!, endDate!!, etTitle?.text.toString(), etContent?.text.toString())
            val pref = getSharedPreferences("PREF_LIFE_MANAGEMENT", MODE_PRIVATE)
            val gson = Gson()
            if(pref.getString("LIST_MANAGEMENT", "").isNullOrEmpty()) {
                val list: ArrayList<Plan> = arrayListOf()
                list.add(plan)
                pref.edit().putString("LIST_MANAGEMENT", gson.toJson(list)).apply()
            } else {
                val itemType = object: TypeToken<ArrayList<Plan>>() {}.type
                gson.fromJson<ArrayList<Plan>>(pref.getString("LIST_MANAGEMENT", ""), itemType)?.let {
                    it.add(plan)
                    pref.edit().putString("LIST_MANAGEMENT", gson.toJson(it)).apply()
                }
            }

            Toast.makeText(this, "Tạo kế hoạch thành công!", Toast.LENGTH_LONG).show()
            finish()
        }

        findViewById<ImageView>(R.id.icBack).setOnClickListener { onBackPressed() }
        findViewById<ConstraintLayout>(R.id.csMain).setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(etContent?.windowToken, 0)
        }


    }
}