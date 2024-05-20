package com.example.assignment.Data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName ="user_data")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val userId:String,
    val name:String,
    val age:Int,
    val place:String
)

@Entity(tableName = "LoginData")
data class LoginDetail(
    @PrimaryKey
    val userId:String,
    val password:String
)