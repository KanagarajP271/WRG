package com.example.wrg.service

import com.example.wrg.model.Employee
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("v2/5d565297300000680030a986")
    suspend fun getAllEmployees() : Response<List<Employee>>

    companion object {
        var BASE_URL = "https://www.mocky.io/"


        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if(BASE_URL.startsWith("http://")){
                BASE_URL = BASE_URL.replaceFirst("http","https")
            }
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}