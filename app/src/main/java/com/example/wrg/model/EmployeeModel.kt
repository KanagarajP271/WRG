package com.example.wrg.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.wrg.database.IntListTypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "employee_detail")
@TypeConverters(IntListTypeConverters::class)
data class Employee(
    @PrimaryKey
    var id:Int?,
    var name: String?,
    var username: String?,
    var email: String?,
    var profile_image: String?,
    @Embedded var address: Address?,
    var phone: String?,
    var website: String?,
    @Embedded var company: Company?
)

data class Geo (
    var lat: String?,
    var lng: String?
)

data class Address (
    var street: String?,
    var suite: String?,
    var city: String?,
    var zipcode: String?,
    @Embedded var geo: Geo? = null
)

data class Company (
    @SerializedName("name")
    var companyName: String?,
    var catchPhrase: String?,
    var bs: String?
)