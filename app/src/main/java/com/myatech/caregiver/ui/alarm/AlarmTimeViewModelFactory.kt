package com.myatech.caregiver.ui.alarm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AlarmTimeViewModelFactory constructor(
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlarmTimeViewModel::class.java)) {
            AlarmTimeViewModel(this.application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}