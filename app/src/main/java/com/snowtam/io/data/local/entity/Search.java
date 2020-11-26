package com.snowtam.io.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "search")
public class Search {

    @PrimaryKey(autoGenerate = true)
    public long searchId;
    public Date date;

    public Search() {
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }
    public long getSearchId() {
        return searchId;
    }

    @Override
    public String toString() {
        return "Search{" +
                "searchId=" + searchId +
                ", date=" + date +
                '}';
    }
}
