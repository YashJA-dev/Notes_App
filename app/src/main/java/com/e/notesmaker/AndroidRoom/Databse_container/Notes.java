package com.e.notesmaker.AndroidRoom.Databse_container;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class Notes {
    @PrimaryKey(autoGenerate = true)
    int id ;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "note")
    String note;

    public Notes(String title, String description, String note) {
        this.title = title;
        this.description = description;
        this.note = note;
    }

    public Notes() {

    }

    public Notes(int id,String description,String title,String note) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.note = note;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
