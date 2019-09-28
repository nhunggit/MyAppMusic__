package com.example.myappmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
private ArrayList<Song> mListSong=new ArrayList<>();
private ArrayList<Song> mSong;
private LayoutInflater inflater;
private Context mcontext;
private OnClickItemView monClickItemView;

    public SongAdapter(LayoutInflater inflater, Context mcontext, OnClickItemView monClickItemView) {
        this.inflater = inflater;
        this.mcontext = mcontext;
        this.monClickItemView = monClickItemView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    interface OnClickItemView{
        void clickItem(Song song);
    }
}
