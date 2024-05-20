package com.example.assignment.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.assignment.Data.model.LoginDetail
import com.example.assignment.Data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities =[ User::class, LoginDetail::class], version = 1 , exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun loginDao() : LoginDao

    companion object{
        private var INSTANCE: UserDatabase?= null

        fun getDatabase(context: Context): UserDatabase = INSTANCE ?:synchronized(this){
                println("Creating the database")
                INSTANCE ?: buildDatabase(context).also{ INSTANCE = it}
            }

        private fun buildDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "User_Database"
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                populateDatabase(database.userDao(), database.loginDao())
                            }
                        }
                    }
                })
                .build()
        }
        suspend  fun populateDatabase(userDao: UserDao, loginDao: LoginDao){
            userDao.insertAll(DummyData.users)
            loginDao.insertAll(DummyData.loginDetails)
        }

      }




}
