package com.jnkhan.transfomersfightclub.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit client for AllSpark API.
 */

public class ClientAllSpark {

    private static String URL_BASE = "https://transformers-api.firebaseapp.com/";
    private static Retrofit clientAllSpark;

    public static Retrofit getClientAllSpark() {
        if (clientAllSpark == null) {
            clientAllSpark = new Retrofit
                    .Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return clientAllSpark;
    }
}
