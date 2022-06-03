package com.breckneck.washapp.data.storage.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Zone {

    @PrimaryKey
    public long id;
    public String zoneName;
    public int picture;

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}
