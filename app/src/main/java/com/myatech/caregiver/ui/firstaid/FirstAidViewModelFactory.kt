package com.myatech.caregiver.ui.firstaid

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class FirstAidViewModelFactory constructor(
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FirstAidViewModel::class.java)) {
            FirstAidViewModel(this.application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}