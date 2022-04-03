package com.example.wrg.utils

import com.example.wrg.model.Employee

object ValidationUtil {

    fun validateEmployee(employee: Employee) : Boolean {
        if (employee.name?.isNotEmpty()!! && employee.username?.isNotEmpty()!!) {
            return true
        }
        return false
    }
}