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
import com.snowtam.io.data.local.entity.SearchAirportCrossRef;
import com.snowtam.io.data.local.entity.SearchWithAirports;

import java.util.Arrays;
import java.util.List;

// making query async using LiveData
// for unit test we want synchronous behaviour, we have to block test thread..

@Dao
public abstract class SearchDao {
    public static final String TAG = "SearchDao";

    /**
     * Get all recent search with relation
     * @return
     */
    @Transaction
    @Query("SELECT * FROM search")
    public abstract LiveData<List<SearchWithAirports>> getRecentSearch();

    /**
     * Get all airports
     * @return
     */
    @Transaction
    @Query("SELECT * FROM airports")
    public abstract List<Airport> getAirports();

    /**
     * Get all searches
     * @return
     */
    @Transaction
    @Query("SELECT * FROM search")
    public abstract List<Search> getSearches();

    /**
     * Get all search_airports
     * @return
     */
    @Transaction
    @Query("SELECT * FROM search_airports")
    public abstract List<SearchAirportCrossRef> getSearchAirports();


    /**
     * Insert a search
     * @return Id search
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract long insertSearch(Search search);

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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertAirport(Airport... airport);

    /**
     * Insert relation between Airport <--> Search
     * @param searchAirportCrossRefs
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract void insertSearchAirportCrossRef(SearchAirportCrossRef ...searchAirportCrossRefs);

    /**
     * Insert airports list of a specific search
     */
    @Transaction
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertAirportsAndSearch(Search search, List<Airport> airports) {

        // insert airports
        Airport[] array = airports.toArray(new Airport[airports.size()]);
        insertAirport(array);

        // insert search
        long searchId = insertSearch(search);

        // insert relation between airport and search
        airports.forEach(airport -> {
                    SearchAirportCrossRef searchAirportCrossRef = new SearchAirportCrossRef(airport.getAirportCode(), searchId);
                    insertSearchAirportCrossRef(searchAirportCrossRef);
        });

        Log.d(TAG, Arrays.toString(array));

    }
}
