package com.jnkhan.transfomersfightclub.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jnkhan.transfomersfightclub.store.Transformer
import com.jnkhan.transfomersfightclub.store.TransformerDatabase
import com.jnkhan.transfomersfightclub.store.TransformerRepository
import com.jnkhan.transfomersfightclub.store.TransformersResponse
import com.jnkhan.transfomersfightclub.utilities.ApiAllSpark
import com.jnkhan.transfomersfightclub.utilities.ClientAllSpark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TFCViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        val AUTOBOT_CHARACTER = "A"
        val DECEPTICON_CHARACTER = "D"
        val SYNC_ADD = "add"
        val SYNC_DELETE = "del"
        val SYNC_UPDATE = "upd"
    }

    private val TAG_TFCVIEWMODE_ERROR = "ERROR TFCVIEWMODEL"
    private val VALUE_PREFERENCES_KEY = "prefs"
    private val VALUE_AUTH_PREFIX = "Bearer "
    private val VALUE_AUTH_TOKEN_KEY = "authentication"

    /**
     * Gets all transformers livedata object from the transformers_table from Transformers repository.
     */
    private val repository: TransformerRepository
    val fighters: LiveData<List<Transformer>>

    init {
        repository =
            TransformerRepository(TransformerDatabase.getDatabase(application).transformerDao())
        fighters = repository.allTransformers
    }

    /**
     * AllSpark retrofit client, used for all API calls
     */
    private val clientAllSpark = ClientAllSpark.getClientAllSpark().create(ApiAllSpark::class.java)

    /**
     * Authentication token with synchronized accessors
     */
    private lateinit var token: String
    private fun getAuthToken(): String {
        synchronized(this) { return token }
    }

    private fun setAuthToken(value: String) {
        synchronized(this) { token = value }
    }

    /**
     * Acquires saved authentication token from sharedpreferences or online if no previous token exists.
     * Also calls populateTransformers after initializing token.
     */
    init {

        val prefs = application.getSharedPreferences(VALUE_PREFERENCES_KEY, Context.MODE_PRIVATE)
        prefs.getString(VALUE_AUTH_TOKEN_KEY, "")?.let { setAuthToken(it) }

        if (getAuthToken().isEmpty()) {
            clientAllSpark.getAuthenticationToken().enqueue(object : Callback<ResponseBody> {

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    errorToast("init -> authToken", t)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    setAuthToken(response.body()!!.string())

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
     * Gets all transformers from AllSpark Server and updates local transformers accordingly.
     * This makes the AllSpark Server the source of truth.
     */
    private fun populateTransformers() {

        clientAllSpark.getTransformers(VALUE_AUTH_PREFIX + getAuthToken())
            .enqueue(object : Callback<TransformersResponse> {

                override fun onFailure(call: Call<TransformersResponse>, t: Throwable) {
                    errorToast("populateTransformers -> getTransformers", t)
                }

                override fun onResponse(
                    call: Call<TransformersResponse>,
                    response: Response<TransformersResponse>
                ) {
                    val list = response.body()!!.transformers

                    viewModelScope.launch(Dispatchers.IO) {
                        for (transformer in list) {
                            repository.insert(transformer)
                        }
                    }
                }

            })
    }

    /**
     * Server and repository manipulation functions
     */
    fun addATransformer(transformer: Transformer) {

        //Adds to server
        clientAllSpark.createTransformer(VALUE_AUTH_PREFIX + token, transformer)
            .enqueue(object : Callback<Transformer> {

                override fun onFailure(call: Call<Transformer>, t: Throwable) {
                    errorToast("addTransformer", t)

                }

                override fun onResponse(
                    call: Call<Transformer>,
                    response: Response<Transformer>
                ) {
                    val newTransformer = response.body()

                    transformer.apply {
                        if (newTransformer != null) {
                            id = newTransformer.id
                            team = newTransformer.team
                            teamIcon = newTransformer.teamIcon
                        }
                    }

                    //Adds to local
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.insert(transformer)
                    }
                }
            })

    }

    fun deleteTransformer(transformer: Transformer) {
        //Deletes from server
        clientAllSpark.deleteTransformer(VALUE_AUTH_PREFIX + getAuthToken(), transformer.id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    errorToast("deleteTransformer", t)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    //Deletes from local
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.delete(transformer)
                    }

                }

            })
    }

    fun updateTransformer(transformer: Transformer) {
        //Updates server
        clientAllSpark.updateTransformer(VALUE_AUTH_PREFIX + getAuthToken(), transformer)
            .enqueue(object : Callback<Transformer> {
                override fun onFailure(call: Call<Transformer>, t: Throwable) {
                    errorToast("updateTransformer", t)
                }

                override fun onResponse(call: Call<Transformer>, response: Response<Transformer>) {
                    //Updates local
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.update(transformer)
                    }
                }

            })
    }

    private fun errorToast(error: String, t: Throwable) {
        Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show()
        Log.e(TAG_TFCVIEWMODE_ERROR, "Error: $error")
        Log.e(TAG_TFCVIEWMODE_ERROR, "Error: $t")
    }
}