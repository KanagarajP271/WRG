package com.example.wrg.service

class ProjectRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllEmployees() = retrofitService.getAllEmployees()
}