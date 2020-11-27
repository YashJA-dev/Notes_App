package com.e.notesmaker;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.e.notesmaker.AndroidRoom.Databse_container.Notes;

import com.e.notesmaker.AndroidRoom.viewmodel_.ViewModelC;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOverlay;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter_Costum mAdapter;
    Adapter_Costum.SetRecycleViewOnClickListener listener;
    FloatingActionButton actionButton;
    Dialog dialog;
    ArrayList<Notes> mlist;
    EditText discription, title, note;
    Button goback, show;
    ViewModelC viewModelC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.tollbar_costum));


        recyclerView = findViewById(R.id.recycle);//recycle view initialization
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//recycle view layout conected
        recyclerView.setHasFixedSize(true);
        mAdapter = new Adapter_Costum(getApplication(), listener);
        recyclerView.setAdapter(mAdapter);
        actionButton = findViewById(R.id.fab);//floting action bar button
        dialog = new Dialog(this);//dialog initialization
        setCLickListener();
        viewModelC = ViewModelProviders.of(this).get(ViewModelC.class);//viewmodel initialization
        viewModelC.getMylist().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                  mAdapter.submitList(notes);
            }
        });


        //swipe function implementation
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    final Notes notes = mAdapter.getNoteAt(viewHolder.getAdapterPosition());
                    viewModelC.delete(notes);
                    Snackbar.make(findViewById(R.id.coordinat_la), "Item Deleted", Snackbar.LENGTH_SHORT)
                            .setAction("undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    viewModelC.insert(notes);
                                }
                            }).show();
                } else if (ItemTouchHelper.RIGHT == direction) {
                    final Notes notes = mAdapter.getNoteAt(viewHolder.getAdapterPosition());
                    viewModelC.delete(notes);
                    Snackbar.make(findViewById(R.id.coordinat_la), "Item Deleted", Snackbar.LENGTH_SHORT)
                            .setAction("undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    viewModelC.insert(notes);
                                }
                            }).show();
                }
            }
        }).attachToRecyclerView(recyclerView);

        //onclick listener on floting action bar button

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
    }
    public void setCLickListener () {
        mAdapter.SetClickListener(new Adapter_Costum.SetRecycleViewOnClickListener() {
            @Override
            public void onClick(final Notes notes) {
                dialog.setContentView(R.layout.popup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                discription = dialog.findViewById(R.id.description_edit);
                title = dialog.findViewById(R.id.title_edit);
                note = dialog.findViewById(R.id.new_edit);
                String discription_s = notes.getDescription();
                String title_S = notes.getTitle();
                String note_s = notes.getNote();
                title.setText(title_S);
                discription.setText(discription_s);
                note.setText(note_s);
                show = dialog.findViewById(R.id.show);
                goback = dialog.findViewById(R.id.goback);
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String discription_s2 = discription.getText().toString();
                        String title_S2 = title.getText().toString();
                        String note_s2 = note.getText().toString();
                        int id = notes.getId();
                        viewModelC.update(new Notes(id, discription_s2, title_S2, note_s2));
                        dialog.dismiss();
                    }
                });
                goback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    //popup used to add items
    public void popup() {
        dialog.setContentView(R.layout.popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        discription = dialog.findViewById(R.id.description_edit);
        title = dialog.findViewById(R.id.title_edit);
        note = dialog.findViewById(R.id.new_edit);
        show = dialog.findViewById(R.id.show);
        goback = dialog.findViewById(R.id.goback);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String discription_s = discription.getText().toString();
                String title_S = title.getText().toString();
                String note_s = note.getText().toString();
                if (discription_s.matches("") || title_S.matches("") ||
                        note_s.matches("")) {
                    Snackbar.make(findViewById(R.id.coordinat_la), "invalid addition of note", Snackbar.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    viewModelC.insert(new Notes(title_S, discription_s, note_s));
                    dialog.dismiss();
                }

            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_mainu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll_menu:
                viewModelC.getMylist().observe(MainActivity.this, new Observer<List<Notes>>() {
                    @Override
                    public void onChanged(List<Notes> notes) {
                        mlist=(ArrayList<Notes>) notes;
                    }
                });
                if(mlist.size()==0){
                    Snackbar.make(findViewById(R.id.coordinat_la),"No Note Avilable!\nClick on create button .",Snackbar.LENGTH_INDEFINITE).setActionTextColor(Color.WHITE)
                            .setAction("Create", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    popup();
                                }
                            }).show();
                }else {
                    viewModelC.deleteAll();
                    Toast.makeText(MainActivity.this, "all notes deleted", Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }

}