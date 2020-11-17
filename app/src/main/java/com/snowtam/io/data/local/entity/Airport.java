package com.snowtam.io.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "airports"
//        foreignKeys = @ForeignKey(entity = Search.class,
//        parentColumns = "searchId",
//        childColumns = "searchId",
//        onDelete = ForeignKey.CASCADE)
)
public class Airport {

    @NonNull
    @PrimaryKey
    private String airportCode;
    private String name;
    private double lat;
    private double log;
    private long searchId;

    // another specific info to add here (item C,..) (as embedded objects)
    @Embedded
    private Snowtam snowtam;

    public Airport(String airportCode, String name, double lat, double log) {
        this.airportCode = airportCode;
        this.name = name;
        this.lat = lat;
        this.log = log;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public Snowtam getSnowtam() {
        return snowtam;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLog() {
        return log;
    }

    public void setSnowtam(Snowtam snowtam) {
        this.snowtam = snowtam;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "airportCode='" + airportCode + '\'' +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", log=" + log +
                ", snowtam=" + snowtam +
                '}';
    }

    public long getSearchId() {
        return searchId;
    }

    public void setSearchId(long searchId) {
        this.searchId = searchId;
    }
}
