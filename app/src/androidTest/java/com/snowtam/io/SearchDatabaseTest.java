package com.snowtam.io;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.jraska.livedata.TestObserver;
import com.snowtam.io.data.local.AppDatabase;
import com.snowtam.io.data.local.DatabaseHelper;
import com.snowtam.io.data.local.dao.SearchDao;
import com.snowtam.io.data.local.entity.Airport;
import com.snowtam.io.data.local.entity.Search;
import com.snowtam.io.data.local.entity.SearchWithAirports;

import org.junit.*;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SearchDatabaseTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    private DatabaseHelper dao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();

        dao = new DatabaseHelper(db);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {

        Log.d("TEST", "Test running");
        List<Airport> airports = new ArrayList<Airport>();
        airports.add(new Airport("GMMN", "Aéroport de Casablanca Mohammed V", 38.299992, -89.022758));


        // Make a search
        Search search = new Search();

        SearchWithAirports searchWithAirports = new SearchWithAirports();
        searchWithAirports.setSearch(search);
        searchWithAirports.setAirports(airports);

        dao.insertAirportsForSearch(search, airports);

        Log.d("TEST - Recent search", searchWithAirports.toString());

        LiveData<List<SearchWithAirports>> recentSearch =
                dao
                .getSearchDao()
                .getRecentSearch();

        TestObserver.test(recentSearch)
                .awaitValue()
                .map(s -> s.get(0).getSearch().getDate())
                .assertValue(search.getDate());

        Log.d("TEST - Recent search in db", recentSearch.getValue().toString());
    }

}
