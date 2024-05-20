package com.example.assignment.Data


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment.Data.model.User


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun Upsert(user: User)
    @Delete
    suspend fun deleteUser(user: User)
    @Query("SELECT * FROM user_data ORDER BY age ASC")
    fun getUsersSortedByAge():LiveData<List<User>>
    @Query("SELECT * FROM user_data ORDER BY place ASC")
    fun getUsersSortedByplace():LiveData<List<User>>
    @Query("SELECT * FROM user_data ORDER BY name ASC")
    fun getUsersSortedByName():LiveData<List<User>>
    @Insert
    suspend fun insertAll(users: List<User>)




}