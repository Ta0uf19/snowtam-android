package com.snowtam.io.data.local.dao;

// Room creates this DAO implementation at compile time

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.snowtam.io.data.local.entity.Airport;
import com.snowtam.io.data.local.entity.Search;
import com.snowtam.io.data.local.entity.SearchWithAirports;

import java.util.Arrays;
import java.util.List;

// making query async using LiveData
// for unit test we want synchronous behaviour, we have to block test thread..

@Dao
public abstract class SearchDao {
    public static final String TAG = "SearchDao";

    /**
     * Get all recent search
     * @return
     */
    @Transaction
    @Query("SELECT * FROM search")
    public abstract LiveData<List<SearchWithAirports>> getRecentSearch();

    @Transaction
    @Query("SELECT * FROM airports")
    public abstract List<Airport> getAirports();

    /**
     * Insert a search
     */
    @Insert
    @Transaction
    abstract void insertSearch(Search ...search);

    /**
     * Update a search
     */
    @Update
    abstract void updateSearch(Search... search);

    /**
     * Delete a search
     */
    @Delete
    abstract void deleteSearch(Search... search);

    /**
     * Insert an Airport
     */
    @Transaction
    @Insert
    abstract void insertAirport(Airport... airport);

    /**
     * Insert airports list of a specific search
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertAirportsAndSearch(Search search, List<Airport> airports) {
        airports.forEach(airport ->
                airport.setFksearchId(search.getSearchId())
        );

        Airport[] array = airports.toArray(new Airport[airports.size()]);

        Log.d(TAG, Arrays.toString(array));

        insertSearch(search);
        insertAirport(array);
    }
}
