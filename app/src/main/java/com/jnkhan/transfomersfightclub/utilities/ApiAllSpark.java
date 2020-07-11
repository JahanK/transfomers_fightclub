package com.jnkhan.transfomersfightclub.utilities;

import com.jnkhan.transfomersfightclub.store.Transformer;
import com.jnkhan.transfomersfightclub.store.TransformersResponse;

import okhttp3.ResponseBody;
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
    Call<ResponseBody> getAuthToken();

    @GET("/transformers")
    Call<TransformersResponse> getTransformers(@Header("authorization") String token);

    @GET("/transformers/{transformerId}")
    Call<Transformer> getTransformer(@Header("authorization") String token,
                                     @Path("transformerId") String id,
                                     @Body Transformer transformer);

    @POST("/transformers")
    Call<Transformer> createTransformer(@Header("authorization") String token,
                                        @Body Transformer transformer);

    @PUT("/transformers")
    Call<Transformer> updateTransformer(@Header("authorization") String token,
                                        @Body Transformer transformer);

    @DELETE("/transformers/{transformerId}")
    Call<Transformer> deleteTransformer(@Header("authorization") String token,
                                        @Path("transformerId") String id);
}
