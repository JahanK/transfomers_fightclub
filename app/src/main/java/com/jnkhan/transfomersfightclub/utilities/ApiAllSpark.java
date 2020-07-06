package com.jnkhan.transfomersfightclub.utilities;

import com.jnkhan.transfomersfightclub.store.Transformer;
import com.jnkhan.transfomersfightclub.store.TransformersResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiAllSpark {

    @GET("/allspark")
    Call<String> getAuthToken();

    @GET("/transformers")
    Call<TransformersResponse> getTransformers(@Header("Authentication") String token);

    @GET("/transformers/{transformerId}")
    Call<Transformer> getTransformer(@Header("Authentication") String token,
                                     @Path("transformerId") String id,
                                     @Body String transformer);

    @POST("/transformers")
    Call<Transformer> createTransformer(@Header("Authentication") String token,
                                        @Body String transformer);

    @PUT("/transformers")
    Call<Transformer> updateTransformer(@Header("Authentication") String token,
                                        @Body String transformer);

    @DELETE("/transformers/{transformerId}")
    Call<Transformer> deleteTransformer(@Header("Authentication") String token,
                                        @Path("transformerId") String id);
}
