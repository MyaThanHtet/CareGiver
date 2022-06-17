package com.myatech.caregiver.ui.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class RegisterViewModelFactory constructor(
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            RegisterViewModel(this.application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}