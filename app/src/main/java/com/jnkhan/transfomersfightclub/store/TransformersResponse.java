package com.jnkhan.transfomersfightclub.store;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TransformersResponse {

    @SerializedName("transformers")
    private List<Transformer> transformers;

    public List<Transformer> getTransformers() {
        return transformers;
    }
}
