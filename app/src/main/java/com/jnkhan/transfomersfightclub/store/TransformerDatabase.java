package com.jnkhan.transfomersfightclub.store;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Transformer.class}, version = 1, exportSchema = false)
public abstract class TransformerDatabase extends RoomDatabase {

    public abstract TransformerDao transformerDao();

    public static TransformerDatabase INSTANCE;

    public static TransformerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TransformerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TransformerDatabase.class, "transformer_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
