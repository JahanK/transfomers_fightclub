package com.jnkhan.transfomersfightclub.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TFCViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TFCViewModel::class.java))
            return TFCViewModel(application) as T

        throw IllegalArgumentException ("Wrong dependencies")
    }
}