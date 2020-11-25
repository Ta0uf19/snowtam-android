package com.snowtam.io.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "airports")
public class Airport {

    @PrimaryKey
    @NonNull
    private String airportCode;
    private String name;
    private double lat;
    private double log;
    // coded snowtam
    private String snowtam;

    public Airport(@NonNull String airportCode, String name, double lat, double log) {
        this.airportCode = airportCode;
        this.name = name;
        this.lat = lat;
        this.log = log;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public String getSnowtam() {
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

    public void setSnowtam(String snowtam) {
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
}
