package com.example.wrg.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.wrg.MyApplication
import com.example.wrg.R
import com.example.wrg.database.EmployeeDatabase
import com.example.wrg.databinding.ActivityEmpoyeeDetaisBinding
import com.example.wrg.databinding.ActivityMainBinding
import com.example.wrg.model.Employee

class EmployeeDetailsActivity : AppCompatActivity() {

    var empID = 0
    var employee: Employee? = null

    lateinit var binding: ActivityEmpoyeeDetaisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmpoyeeDetaisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            if (intent.hasExtra("empID")) {
                empID = intent.getIntExtra("empID", 0)
                if (empID != 0) {
                    employee = EmployeeDatabase.getDatabase(MyApplication.getContext()).Dao().getEmployeeByID(empID)
                }
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        binding.tvPersonName.text = employee?.name

        Glide.with(this)
            .load(employee?.profile_image)
            .placeholder(R.color.placeholder)
            .into(binding.imgPerson)

        binding.tvUserName.text = employee?.username
        binding.tvEmail.text = employee?.email
        binding.tvPhone.text = employee?.phone
        binding.tvWebSite.text = employee?.website

        binding.tvCompanyName.text = employee?.company?.companyName
        binding.tvCatch.text = employee?.company?.catchPhrase
        binding.tvBs.text = employee?.company?.bs

        binding.tvAddressDetails.text =
            "${employee?.address?.street}, ${employee?.address?.suite}, ${employee?.address?.city} - ${employee?.address?.zipcode}"

    }
}