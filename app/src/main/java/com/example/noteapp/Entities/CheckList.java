package com.example.noteapp.Entities;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "checklist")
public class CheckList implements Serializable{

        @PrimaryKey(autoGenerate = true)
        private int id;

        @ColumnInfo(name = "title")
        private String title;

        @ColumnInfo(name = "date_time")
        private String dateTime;

        @ColumnInfo(name = "list_text")
        private String listText;

        @ColumnInfo(name = "color")
        private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getListText() {
        return listText;
    }

    public void setListText(String listText) {
        this.listText = listText;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Nullable
        @Override
        public String toString() {
            return title + ":" + dateTime;
        }

}
