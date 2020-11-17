package com.snowtam.io.data.local.dao;

// Room creates this DAO implementation at compile time

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

import java.util.List;

// making query async using LiveData
// for unit test we want synchronous behaviour, we have to block test thread..

@Dao
public interface SearchDao {

    /**
     * Get all recent search
     * @return
     */
    @Transaction
    @Query("SELECT * FROM search ORDER BY date ASC")
    LiveData<List<SearchWithAirports>> getRecentSearch();

    /**
     * Insert a search
     */
    @Insert
    @Transaction
    void insertSearch(Search ...search);

    /**
     * Update a search
     */
    @Update
    void updateSearch(Search... search);

    /**
     * Delete a search
     */
    @Delete
    void deleteSearch(Search... search);

    /**
     * Insert an Airport
     */
    @Insert
    void insertAirport(Airport... airport);

}
