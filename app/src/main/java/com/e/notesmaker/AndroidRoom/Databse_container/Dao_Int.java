package com.e.notesmaker.AndroidRoom.Databse_container;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface Dao_Int {
    @Insert
    public void insert_Note(Notes notes);
   @Update
    public void update_(Notes notes);
    @Query("select * from Notes order by id desc")
    LiveData<List<Notes>> gallNotes();
    @Query("delete from notes")
    public void deleteAll();
    @Delete
    public void delete(Notes notes);
}