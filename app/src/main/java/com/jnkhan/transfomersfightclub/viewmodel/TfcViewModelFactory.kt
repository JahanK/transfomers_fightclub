package com.jnkhan.transfomersfightclub.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TfcViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TfcViewModel::class.java))
            return TfcViewModel(application) as T

        throw IllegalArgumentException ("Wrong dependencies")
    }
}