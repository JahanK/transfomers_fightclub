package com.jnkhan.transfomersfightclub.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.jnkhan.transfomersfightclub.store.Transformer
import com.jnkhan.transfomersfightclub.utilities.ApiAllSpark
import com.jnkhan.transfomersfightclub.utilities.ClientAllSpark
import java.util.*

class TFCViewModel(application: Application) : AndroidViewModel(application) {

    var preferences = "prefs"

    var authPrefix = "Bearer "
    var authKey = "authentication"

    var apiAllSpark = ClientAllSpark.getClientAllSpark().create(ApiAllSpark::class.java)

    var token : String? = null
    init {
        token = application.getSharedPreferences(preferences, Context.MODE_PRIVATE).getString(authKey, "")
    }

    var autobots = TreeSet<Transformer>( Comparator<Transformer> { a, b -> (a.rating - b.rating) } )
    var decepticons = TreeSet<Transformer>( Comparator<Transformer> { a, b -> (a.rating - b.rating) } )
}