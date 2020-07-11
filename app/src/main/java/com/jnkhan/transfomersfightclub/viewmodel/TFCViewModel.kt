package com.jnkhan.transfomersfightclub.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jnkhan.transfomersfightclub.store.Transformer
import com.jnkhan.transfomersfightclub.store.TransformersResponse
import com.jnkhan.transfomersfightclub.utilities.ApiAllSpark
import com.jnkhan.transfomersfightclub.utilities.ClientAllSpark
import okhttp3.ResponseBody
import retrofit2.Call
import java.util.*
import retrofit2.Callback
import retrofit2.Response

class TFCViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        val AUTOBOT_CHARACTER = "A"
        val DECEPTICON_CHARACTER = "D"
    }

    private var appbarCollapsed = false
        get
        set

    private val TAG_TFCVIEWMODE_ERROR = "ERROR TFCVIEWMODEL"
    private val VALUE_PREFERENCES_KEY = "prefs"
    private val VALUE_AUTH_PREFIX = "Bearer "
    private val VALUE_AUTH_TOKEN_KEY = "authentication"
    private val SYNC_ADD = "add"
    private val SYNC_DELETE = "del"
    private val SYNC_UPDATE = "upd"

    /**
     * Autobot and decepticon team lists along with their MutableLiveData objects
     * The sets are sorted by transformer rating
     */
    private val autobots =
        TreeSet<Transformer>(Comparator<Transformer> { a, b -> (a.rating - b.rating) })
    private val autobotMutableLiveData = MutableLiveData<TreeSet<Transformer>>(autobots)

    private val decepticons =
        TreeSet<Transformer>(Comparator<Transformer> { a, b -> (a.rating - b.rating) })
    private val decepticonMutableLiveData = MutableLiveData<TreeSet<Transformer>>(decepticons)

    /**
     * AllSpark retrofit client, used for all API calls
     */
    private val clientAllSpark = ClientAllSpark.getClientAllSpark().create(ApiAllSpark::class.java)

    /**
     * Authentication token with synchronized accessors
     */
    private var token: String? = null
    private fun getAuthToken(): String? {
        synchronized(this) { return token }
    }

    private fun setAuthToken(value: String?) {
        synchronized(this) { token = value }
    }

    /**
     * Acquires saved authentication token from sharedpreferences or online if no previous token exists
     */
    init {
        val prefs = application.getSharedPreferences(VALUE_PREFERENCES_KEY, Context.MODE_PRIVATE)

        setAuthToken(prefs.getString(VALUE_AUTH_TOKEN_KEY, ""))

        if (getAuthToken() == null || getAuthToken()?.length == 0) {
            clientAllSpark.authToken.enqueue(object : Callback<ResponseBody> {

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    errorToast("init -> authToken", t)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    setAuthToken(response.body()?.string())

                    with(prefs.edit()) {
                        putString(VALUE_AUTH_TOKEN_KEY, getAuthToken())
                        commit()
                    }
                }
            })
        }

        populateTransformers()
    }

    /**
     * Populates transformers from AllSpark Server
     */
    private fun populateTransformers() {

        if (getAuthToken() != null) {
            clientAllSpark.getTransformers(VALUE_AUTH_PREFIX + getAuthToken())
                .enqueue(object : Callback<TransformersResponse> {

                    override fun onFailure(call: Call<TransformersResponse>, t: Throwable) {
                        errorToast("populateTransformers -> getTransformers", t)
                    }

                    override fun onResponse(
                        call: Call<TransformersResponse>,
                        response: Response<TransformersResponse>
                    ) {
                        var list = response.body()!!.transformers

                        for (transformer in list) {
                            Log.e(TAG_TFCVIEWMODE_ERROR, transformer.name.toString())
                            if (transformer.team.compareTo(AUTOBOT_CHARACTER) == 0)
                                autobots.add(transformer)
                            else
                                decepticons.add(transformer)

                        }
                    }

                })
        }
    }

    /**
     * Adds transformer to their respective list after communicating with AllSpark to register transformer
     */
    fun addATransformer(transformer: Transformer) {

        clientAllSpark.createTransformer(VALUE_AUTH_PREFIX + token, transformer)
            .enqueue(object : Callback<Transformer> {

                override fun onFailure(call: Call<Transformer>, t: Throwable) {
                    errorToast("addTransformer", t)

                }

                override fun onResponse(call: Call<Transformer>, response: Response<Transformer>) {
                    var newTransformer = response.body()

                    transformer.apply {
                        if (newTransformer != null) {
                            id = newTransformer.id
                            team = newTransformer.team
                            teamIcon = newTransformer.teamIcon
                        }
                    }

                    if (transformer.team.compareTo(AUTOBOT_CHARACTER) == 0)
                        autobots.add(transformer)
                    else
                        decepticons.add(transformer)
                }
            })
    }

    private fun errorToast(error: String, t: Throwable) {
        Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show()
        Log.e(TAG_TFCVIEWMODE_ERROR, "Error: $t")
        Log.e(TAG_TFCVIEWMODE_ERROR, "Error: ${t.printStackTrace()}")
    }

    private fun synchronizedOperations(operation: String, transformer: Transformer) {
        synchronized(this) {
        }
    }
}