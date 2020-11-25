package com.snowtam.io.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "search_airports", primaryKeys = {"airportCode", "searchId"})
public class SearchAirportCrossRef {
    @NonNull
    public String airportCode;
    public long searchId;

    public SearchAirportCrossRef(@NonNull String airportCode, long searchId) {
        this.airportCode = airportCode;
        this.searchId = searchId;
    }

    @Override
    public String toString() {
        return "SearchAirportCrossRef{" +
                "airportCode='" + airportCode + '\'' +
                ", searchId=" + searchId +
                '}';
    }
}
