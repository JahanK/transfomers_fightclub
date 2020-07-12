package com.jnkhan.transfomersfightclub.utilities

import com.jnkhan.transfomersfightclub.store.Transformer
import com.jnkhan.transfomersfightclub.store.TransformersResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiAllSpark {

    @GET("/allspark")
    fun getAuthenticationToken(): Call<ResponseBody>

    @GET("/transformers")
    fun getTransformers(@Header("authorization") token: String): Call<TransformersResponse>

    @GET("/transformers/{transformerId}")
    fun getTransformer(
        @Header("authorization") token: String,
        @Path("transformerId") id: String,
        @Body transformer: Transformer
    ): Call<Transformer>

    @POST("/transformers")
    fun createTransformer(
        @Header("authorization") token: String,
        @Body transformer: Transformer
    ): Call<Transformer>

    @PUT("/transformers")
    fun updateTransformer(
        @Header("authorization") token: String,
        @Body transformer: Transformer
    ): Call<Transformer>

    @DELETE("/transformers/{transformerId}")
    fun deleteTransformer(
        @Header("authorization") token: String,
        @Path("transformerId") id: String
    ): Call<ResponseBody>
}