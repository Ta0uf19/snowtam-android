package com.snowtam.io;
import static org.junit.Assert.*;
import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.jraska.livedata.TestObserver;
import com.snowtam.io.data.local.AppDatabase;
import com.snowtam.io.data.local.dao.SearchDao;
import com.snowtam.io.data.local.entity.AirportNotam;
import com.snowtam.io.data.local.entity.Search;
import com.snowtam.io.data.local.entity.SearchWithAirports;

import org.junit.*;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SearchDatabaseTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    private SearchDao dao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();

        dao = db.searchDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeAndReadRecentSearch() throws Exception {

        // create list of airports
        List<AirportNotam> airports = new ArrayList<AirportNotam>();
        airports.add(new AirportNotam("GMMN", "MA", "Aéroport de Casablanca Mohammed V", 38.299992, -89.022758,""));
        airports.add(new AirportNotam("VDM","MA", "Aéroport VDM", 38.299992, -89.022758,""));

        // insert search with airports
        Search search = new Search();
        dao.insertAirportsAndSearch(new Search(), airports);

        // mock search with airports and compare it insert db values.
        SearchWithAirports mockSearchWithAirports = new SearchWithAirports(search, airports);

        // retrive recent search
        LiveData<List<SearchWithAirports>> recentSearch = dao.getRecentSearch();

        // test mock with actual db value. (https://github.com/jraska/livedata-testing) (waiting for value)
        TestObserver.test(recentSearch).awaitValue();
        List<SearchWithAirports> dbRecentSearch = recentSearch.getValue();

        // check if we have only one search
        assertEquals(dbRecentSearch.size(), 1);

        // check if we have two airports in this search
        assertEquals(dbRecentSearch.get(0).airports.size(), 2);

        // check if airport code of the first airport is the same
        assertEquals(
                dbRecentSearch.get(0).airports.get(0).getAirportCode(),
                mockSearchWithAirports.airports.get(0).getAirportCode()
        );


        Log.d("recentSearch in db ", dbRecentSearch.toString());

//        Log.d("airpots in db", dao.getAirports().toString());
//        Log.d("searches in db", dao.getSearches().toString());
//        Log.d("search_airports in db", dao.getSearchAirports().toString());
    }

}
