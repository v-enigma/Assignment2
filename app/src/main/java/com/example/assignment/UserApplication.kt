package com.example.assignment

import android.app.Application
import com.example.assignment.Data.LoginRepository
import com.example.assignment.Data.UserDatabase
import com.example.assignment.Data.UserRepository

class UserApplication: Application() {
    val database by lazy{ UserDatabase.getDatabase(this) }
    val repository by lazy{ UserRepository(database.userDao(), database.loginDao()) }
    val loginRepository by lazy{ LoginRepository(database.loginDao()) }
}