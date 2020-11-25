package com.e.notesmaker.AndroidRoom.Databse_container;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Notes.class},version = 1)
public abstract class Mdatabase extends RoomDatabase{
    public abstract Dao_Int getdao();
    private static Mdatabase minstance;
    public static Mdatabase getinstance(Context context){
        if (minstance==null){
            synchronized (Mdatabase.class){
                if (minstance==null){
                    minstance= Room.databaseBuilder(context.getApplicationContext(),
                            Mdatabase.class,"notes_database").build();
                }
            }
        }
        return minstance;
    }
}
