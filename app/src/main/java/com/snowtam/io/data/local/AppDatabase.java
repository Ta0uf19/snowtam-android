package com.snowtam.io.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.snowtam.io.data.local.dao.SearchDao;
import com.snowtam.io.data.local.entity.Airport;
import com.snowtam.io.data.local.entity.Search;
import com.snowtam.io.data.local.entity.SearchAirportCrossRef;
import com.snowtam.io.utils.TimestampConverters;

// At runtime, you can acquire an instance of Database by calling Room.databaseBuilder() or Room.inMemoryDatabaseBuilder().

@Database(entities = {Search.class, Airport.class, SearchAirportCrossRef.class}, version = 1, exportSchema = false)
@TypeConverters({TimestampConverters.class}) // to convert Date object to unix timestamp
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract SearchDao searchDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "search_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
