package com.breckneck.washapp.data.storage.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey
    public long id;
    public long zoneId;
    public String taskName;
    public int frequency;
//    public int picture;
//
//    public int getPicture() {
//        return picture;
//    }
//
//    public void setPicture(int picture) {
//        this.picture = picture;
//    }
}
