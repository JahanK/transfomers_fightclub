package com.jnkhan.transfomersfightclub.store;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TransformersResponse {

    @SerializedName("transformers")
    private ArrayList<Transformer> transformers;

    public ArrayList<Transformer> getTransformers() {
        return transformers;
    }
}
