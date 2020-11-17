package com.snowtam.io.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "search")
public class Search {

    @PrimaryKey(autoGenerate = true)
    private long searchId;
    private Date date;

    public Search() {
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public long getSearchId() {
        return searchId;
    }

    public void setSearchId(long searchId) {
        this.searchId = searchId;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
