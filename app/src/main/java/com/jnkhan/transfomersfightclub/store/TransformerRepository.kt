package com.jnkhan.transfomersfightclub.store

import androidx.lifecycle.LiveData

class TransformerRepository(private val transformerDao: TransformerDao) {

    val allTransformers: LiveData<List<Transformer>> = transformerDao.transformers

    fun insert(transformer: Transformer) {
        transformerDao.insert(transformer)
    }

    fun delete(transformer: Transformer) {
        transformerDao.delete(transformer)
    }

    fun update(transformer: Transformer) {
        transformerDao.update(transformer)
    }

    fun contains(transformer: Transformer) {
        transformerDao.contains(transformer.name)
    }
}