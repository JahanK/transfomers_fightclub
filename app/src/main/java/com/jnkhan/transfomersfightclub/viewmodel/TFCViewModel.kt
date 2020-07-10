package com.jnkhan.transfomersfightclub.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jnkhan.transfomersfightclub.store.Transformer
import com.jnkhan.transfomersfightclub.utilities.ApiAllSpark
import com.jnkhan.transfomersfightclub.utilities.ClientAllSpark
import java.util.*

class TFCViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        val AUTOBOT_CHARACTER = "A"
        val DECEPTICON_CHARACTER = "D"
    }

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

    var autobotMutableLiveData = MutableLiveData<TreeSet<Transformer>>(autobots)
    var decepticonMutableLiveData = MutableLiveData<TreeSet<Transformer>>(decepticons)

    fun addATransformer(transformer : Transformer) {
        if(transformer.team.compareTo(AUTOBOT_CHARACTER) == 0)
            autobots.add(transformer)
        else
            decepticons.add(transformer)
    }
}