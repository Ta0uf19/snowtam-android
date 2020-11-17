package com.snowtam.io.data.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * A Pojo that is used by {@link Relation} to query for entity relationships
 * Exlusive class to manage one to many relationship
 */

public class SearchWithAirports {
    @Embedded
    private Search search;

    @Relation(parentColumn = "searchId", entityColumn = "airportCode")
    private List<Airport> airports;

    public void setSearch(Search search) {
        this.search = search;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public Search getSearch() {
        return search;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    @Override
    public String toString() {
        return "SearchWithAirports{" +
                "search=" + search.getDate() +
                ", airports=" + airports +
                '}';
    }
}
