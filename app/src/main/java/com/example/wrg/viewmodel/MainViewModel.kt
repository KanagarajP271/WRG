package com.example.wrg.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wrg.MyApplication
import com.example.wrg.database.EmployeeDatabase
import com.example.wrg.model.Employee
import com.example.wrg.service.ProjectRepository
import kotlinx.coroutines.*

class MainViewModel constructor(private val projectRepository: ProjectRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    //val movieList = MutableLiveData<List<Employee>>()
    val employeeListLiveData = MutableLiveData<List<Employee>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllEmployees() {

        val employeeList = EmployeeDatabase.getDatabase(MyApplication.getContext()).Dao().allEmployees

        if (employeeList.isNotEmpty()) {
            employeeListLiveData.value = employeeList
            loading.value = false
        }
        else {
            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                loading.postValue(true)
                val response = projectRepository.getAllEmployees()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        EmployeeDatabase.getDatabase(MyApplication.getContext()).Dao().insertAllEmployees(response.body())
                        employeeListLiveData.postValue(response.body())
                        loading.value = false
                    } else {
                        onError("Error : ${response.message()} ")
                    }
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}