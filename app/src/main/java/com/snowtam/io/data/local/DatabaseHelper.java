package com.snowtam.io.data.local;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.snowtam.io.data.local.dao.SearchDao;
import com.snowtam.io.data.local.entity.Airport;
import com.snowtam.io.data.local.entity.Search;

import java.util.List;

public class DatabaseHelper {

    private SearchDao searchDao;

    public DatabaseHelper(AppDatabase database) {
        searchDao = database.searchDao();
    }

    /**
     * Insert airports list from a specific search
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertAirportsForSearch(Search search, List<Airport> airports) {
        airports.forEach(airport -> airport.setSearchId(search.getSearchId()));

        Airport[] array = airports.toArray(new Airport[airports.size()]);

        searchDao.insertSearch(search);
        searchDao.insertAirport(array);
    }

    public SearchDao getSearchDao() {
        return searchDao;
    }
}
