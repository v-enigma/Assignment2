package com.example.assignment.Data

import androidx.lifecycle.LiveData
import com.example.assignment.Data.model.LoginDetail
import com.example.assignment.Data.model.User


class UserRepository(private val userDao: UserDao, private val loginDao: LoginDao) {


    fun getUserSortedByAge():LiveData<List<User>> = userDao.getUsersSortedByAge()

    fun getUserSortedByPlace():LiveData<List<User>> = userDao.getUsersSortedByplace()

    fun getUserSortedByName(): LiveData<List<User>> =userDao.getUsersSortedByName()

    suspend fun add(user: User, loginDetail: LoginDetail){
        userDao.Upsert(user)
        loginDao.add(loginDetail)
    }
    suspend fun update(user: User) = userDao.Upsert(user)
    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun isUserIdAvailable(userId:String) =  loginDao.getUser(userId)==null
}
