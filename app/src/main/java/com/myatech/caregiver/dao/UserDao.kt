package com.myatech.caregiver.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myatech.caregiver.model.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM tbl_user WHERE username=:username AND password=:password")
    fun checkUser(username: String, password: String): LiveData<User>
}
