package com.e.notesmaker;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.e.notesmaker.AndroidRoom.Databse_container.Notes;

import com.e.notesmaker.AndroidRoom.viewmodel_.ViewModelC;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Costum extends ListAdapter<Notes,Adapter_Costum.Example_view_holder> {
    public Context context;
    public Application application;
    private SetRecycleViewOnClickListener recycleViewOnClickListener;
//    public ArrayList<Notes> mlist = new ArrayList<>();
    ViewModelC viewModelC;
    Dialog dialog;

    public Adapter_Costum( Application application, SetRecycleViewOnClickListener listener) {
        super(DIFF_CALLBACK);
        this.recycleViewOnClickListener=listener;
        this.application = application;
    }
    private static final DiffUtil.ItemCallback<Notes> DIFF_CALLBACK=new DiffUtil.ItemCallback<Notes>() {
        @Override
        public boolean areItemsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getId()==newItem.getId();

        }

        @Override
        public boolean areContentsTheSame(@NonNull Notes oldItem, @NonNull Notes newItem) {
            return oldItem.getNote().equals(newItem.getNote())&&
                               oldItem.getTitle().equals(newItem.getTitle())&&
                                          oldItem.getDescription().equals(newItem.getDescription())&&
                                                        oldItem.getId()==newItem.getId();
        }
    };
    @NonNull
    @Override
    public Example_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_recycle_view, parent, false);
        Example_view_holder example_view_holder = new Example_view_holder(v);
        return example_view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Example_view_holder holder, final int position) {
        final Notes notes = getItem(position);
        holder.title.setText(notes.getTitle());
        holder.discription.setText(notes.getDescription());
        holder.note.setText(notes.getNote());
    }

    public Notes getNoteAt(int position) {
        return getItem(position);
    }

    public class Example_view_holder extends RecyclerView.ViewHolder {
        TextView title, discription, note;
        ImageView delete, edit;

        public Example_view_holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title2);
            discription = itemView.findViewById(R.id.description);
            note = itemView.findViewById(R.id.text_input);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleViewOnClickListener!=null&&getAdapterPosition()!=RecyclerView.NO_POSITION){
                        recycleViewOnClickListener.onClick(getItem(getAdapterPosition()));
                    }

                }
            });
        }
    }
    public interface SetRecycleViewOnClickListener {
        void onClick(Notes notes);
    }
    public void SetClickListener(SetRecycleViewOnClickListener listener){
        this.recycleViewOnClickListener=listener;
    }
}
