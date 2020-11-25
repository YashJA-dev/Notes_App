package com.e.notesmaker.AndroidRoom.viewmodel_;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.e.notesmaker.AndroidRoom.Databse_container.Notes;
import com.e.notesmaker.AndroidRoom.repository.M_repo;

import java.util.ArrayList;
import java.util.List;

public class ViewModelC extends AndroidViewModel {
    M_repo repo;
    LiveData<List<Notes>> mylist;
    public ViewModelC(@NonNull Application application) {
        super(application);
        repo=new M_repo(application);
        mylist=repo.mylist();
    }
    public LiveData<List<Notes>> getMylist(){
        return mylist;
    }
    public void insert(Notes notes){
        repo.insert(notes);
    }
    public void deleteAll(){
        repo.deleteAll();
    }
    public void delete(Notes notes){
        repo.delete(notes);
    }
    public void update(Notes notes){
        repo.update(notes);
    }
}
