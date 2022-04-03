package com.example.wrg.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.wrg.adapter.Adapter
import com.example.wrg.databinding.ActivityMainBinding
import com.example.wrg.service.MyViewModelFactory
import com.example.wrg.service.ProjectRepository
import com.example.wrg.service.RetrofitService
import com.example.wrg.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), Adapter.ItemClickListener {

    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = ProjectRepository(retrofitService)
        binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)

        viewModel.employeeListLiveData.observe(this) {
            adapter.addData(it, this, this)
            //binding.recyclerview.adapter = Adapter(it, this, this)
            /*for (i in it.indices) {
                Log.d("zzzz", it[i].id.toString())
            }*/
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this) {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        }

        viewModel.getAllEmployees()

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString() == "") {
                    binding.ivClear.visibility = View.GONE
                }
                else {
                    binding.ivClear.visibility = View.VISIBLE
                }

                adapter.filter.filter(editable.toString())
            }
        })

        binding.ivClear.setOnClickListener {
            binding.etSearch.text.clear()
            binding.ivClear.visibility = View.GONE
        }
    }

    fun setupSearch(query: String?) {
        if (query != null && query != "") {
            var count = 0


        }

    }

    override fun onItemClick(id: Int?) {
        if (id != 0) {
            val intent = Intent(this, EmployeeDetailsActivity::class.java)
            intent.putExtra("empID", id)
            startActivity(intent)
        }
    }
}