package com.e.notesmaker.AndroidRoom.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.e.notesmaker.AndroidRoom.Databse_container.Dao_Int;
import com.e.notesmaker.AndroidRoom.Databse_container.Mdatabase;
import com.e.notesmaker.AndroidRoom.Databse_container.Notes;

import java.util.ArrayList;
import java.util.List;

public class M_repo {
    Dao_Int dao;
    LiveData<List<Notes>> mylist;
    public M_repo(Application application){
        Mdatabase mdatabase=Mdatabase.getinstance(application);
        dao=mdatabase.getdao();
        mylist=dao.gallNotes();
    }
    public LiveData<List<Notes>> mylist(){
        return mylist;
    }
    public void insert(Notes notes){
        new AsyncInsert(dao).execute(notes);
    }
    public void deleteAll(){
         new AsyncdeleteAll(dao).execute();
    }
    public void delete(Notes note){
        new Asyncdelete(dao).execute(note);
    }
    public void update(Notes notes){
        new Asyncupdate(dao).execute(notes);
    }
    private class AsyncInsert extends AsyncTask<Notes,Void,Void>{
        Dao_Int dao;
        public AsyncInsert(Dao_Int dao) {
                this.dao=dao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            dao.insert_Note(notes[0]);
            return null;
        }
    }
    private class AsyncdeleteAll extends AsyncTask<Void,Void,Void>{
        Dao_Int dao;
        public AsyncdeleteAll(Dao_Int dao) {
            this.dao=dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
    private class Asyncdelete extends AsyncTask<Notes,Void,Void>{
        Dao_Int dao;
        public Asyncdelete(Dao_Int dao) {
            this.dao=dao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            dao.delete(notes[0]);
            return null;
        }
    }
     private class Asyncupdate extends AsyncTask<Notes,Void,Void>{
        Dao_Int dao;
        public Asyncupdate(Dao_Int dao) {
            this.dao=dao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            dao.update_(notes[0]);
            return null;
        }
    }

}

