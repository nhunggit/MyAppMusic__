package com.example.myappmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.WordViewHolder> {
private ArrayList<Song> mListSong=new ArrayList<>();
private ArrayList<Song> mSong;
private LayoutInflater mInflater;
private Context mcontext;

    public SongAdapter(ArrayList<Song> listBaihat, Context context) {
        this.mListSong = listBaihat;
        //this.layout=layout;
        this.mcontext = context;
    }
    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview=inflater.inflate(R.layout.row_recyclerview,parent,false);
        return new WordViewHolder(itemview,this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.mStt.setText(mListSong.get(position).getId()+"");
        holder.mNameSong.setText(mListSong.get(position).getTitle());
        holder.mHour.setText(mListSong.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return mListSong.size();
    }


    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mStt;
        TextView mNameSong;
        TextView mHour;
        ImageButton mMore;
        final SongAdapter songAdapter;


        public WordViewHolder(@NonNull View itemView,SongAdapter songAdapter) {
            super(itemView);
            this.songAdapter=songAdapter;
            mStt=(TextView)itemView.findViewById(R.id.stt);
            mNameSong=(TextView)itemView.findViewById(R.id.namesong);
            mHour=(TextView)itemView.findViewById(R.id.hours);
            mMore=(ImageButton) itemView.findViewById(R.id.more);

        }

        @Override
        public void onClick(View v) {
            int mPosition =getLayoutPosition();
            Song element=mListSong.get(mPosition);
            mListSong.set(mPosition,element);
            songAdapter.notifyDataSetChanged();
        }
    }
}
