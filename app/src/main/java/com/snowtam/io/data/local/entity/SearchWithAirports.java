package com.snowtam.io.data.local.entity;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

/**
 * A Pojo that is used by {@link Relation} to query for entity relationships
 * Exlusive class to manage one to many relationship
 */

public class SearchWithAirports {
    @Embedded public Search search;

    @Relation(
            parentColumn = "searchId",
            entityColumn = "airportCode",
            associateBy = @Junction(SearchAirportCrossRef.class)
    )
    public List<Airport> airports;

    public SearchWithAirports(Search search, List<Airport> airports) {
        this.search = search;
        this.airports = airports;
    }

    @Override
    public String toString() {
        return "SearchWithAirports{" +
                "search=" + search +
                ", airports=" + airports +
                '}';
    }
}
