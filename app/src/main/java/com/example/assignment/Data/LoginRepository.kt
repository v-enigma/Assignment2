package com.example.assignment.Data

import com.example.assignment.Data.model.Result

class LoginRepository(private val loginDao: LoginDao) {

    suspend fun authenticate(userId:String, password:String) : Result {
        val result = loginDao.authenticate(userId, password)
       return result?.let{ Result.SUCCESS } ?: Result.FAILURE

    }

}