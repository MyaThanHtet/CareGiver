package com.myatech.caregiver.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myatech.caregiver.model.User
import com.myatech.caregiver.repository.MyDatabase
import com.myatech.caregiver.repository.UserRepository


class LoginViewModel constructor(
    application: Application
) : ViewModel() {
    private val userRepository: UserRepository

    private val _getSignedInUser = MutableLiveData<User>()
    private var getSignedInUser: LiveData<User> = _getSignedInUser

    init {
        val userDao = MyDatabase.getInstance(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun checkUser(username: String, password: String): LiveData<User> {
        getSignedInUser = userRepository.checkUser(username, password)
        return getSignedInUser
    }
}