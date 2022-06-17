package com.myatech.caregiver.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class LoginViewModelFactory constructor(
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(this.application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}