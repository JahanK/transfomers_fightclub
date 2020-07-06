package com.jnkhan.transfomersfightclub.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.jnkhan.transfomersfightclub.utilities.ApiAllSpark
import com.jnkhan.transfomersfightclub.utilities.ClientAllSpark

class TFCViewModel(application: Application) : AndroidViewModel(application) {

    var preferences = "prefs"
    var auth = "authentication"

    var apiAllSpark = ClientAllSpark.getClientAllSpark().create(ApiAllSpark::class.java)

    var token : String?
    init {
        token = application.getSharedPreferences(preferences, Context.MODE_PRIVATE).getString(auth, "")

        if(token?.length == 0) {

        }
    }


}