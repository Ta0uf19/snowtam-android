package com.snowtam.io.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "airports")
public class AirportNotam {

    @PrimaryKey
    @NonNull
    private String airportCode;
    private String name;
    private Double lat;
    private Double log;
    private String countryCode;
    private String snowtam; // formattedText snowtam

    public AirportNotam(@NonNull String airportCode, String countryCode, String name, Double lat, Double log) {
        this.airportCode = airportCode;
        this.name = name;
        this.lat = lat;
        this.log = log;
        this.countryCode = countryCode;
    }
    public AirportNotam() { }

    public void setAirportCode(@NonNull String airportCode) {
        this.airportCode = airportCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLog(Double log) {
        this.log = log;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public Double getLat() {
        return lat;
    }

    public Double getLog() {
        return log;
    }

    public void setSnowtam(String snowtam) {
        this.snowtam = snowtam;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String toString() {
        return "AirportNotam{" +
                "airportCode='" + airportCode + '\'' +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", log=" + log +
                ", countryCode='" + countryCode + '\'' +
                ", snowtam='" + snowtam + '\'' +
                '}';
    }
}
