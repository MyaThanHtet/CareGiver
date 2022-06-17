package com.myatech.caregiver.repository

import androidx.lifecycle.LiveData
import com.myatech.caregiver.dao.UserDao
import com.myatech.caregiver.model.User

class UserRepository(private val userDao: UserDao) {

    fun insert(user: User) {
        userDao.insertUser(user)
    }

    fun checkUser(username: String, password: String): LiveData<User> {

        return userDao.checkUser(username, password)
    }


}