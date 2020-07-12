package com.jnkhan.transfomersfightclub.store;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TransformerDao {

    @Insert (onConflict = REPLACE)
    void insert(Transformer transformer);

    @Delete
    void delete(Transformer transformer);

    @Update
    void update(Transformer transformer);

    @Query("SELECT name FROM transformers_table where name = :transformerName")
    String contains(String transformerName);

    @Query("SELECT * FROM transformers_table ORDER BY name ASC")
    LiveData<List<Transformer>> getTransformers();
}
