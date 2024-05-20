package com.example.assignment.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment.Data.model.LoginDetail

@Dao
interface LoginDao {
    @Query(" SELECT * FROM Logindata where userId = :userId AND password =:password ")
   suspend fun authenticate(userId:String, password:String): LoginDetail?
   @Insert
   suspend fun add(loginDetail: LoginDetail)
   @Insert
   suspend fun insertAll(loginDetails: List<LoginDetail>)

   @Query("SELECT * FROM LoginData where userId=:userId")
   suspend fun getUser(userId: String): LoginDetail?




}