package com.myatech.caregiver.ui.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myatech.caregiver.model.User
import com.myatech.caregiver.repository.MyDatabase
import com.myatech.caregiver.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel constructor(
    application: Application
) : ViewModel() {
    private val userRepository: UserRepository

    init {
        val userDao = MyDatabase.getInstance(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun registerUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.insert(user)
    }
}
