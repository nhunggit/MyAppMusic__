package com.example.myappmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
private ArrayList<Song> mListSong=new ArrayList<>();
private ArrayList<Song> mSong;
private LayoutInflater mInflater;
private Context mcontext;
private OnClickItemView monClickItemView;

    public SongAdapter(OnClickItemView monClickItemView, Context context) {
        mInflater=LayoutInflater.from(mcontext);
        mcontext=context;
        this.monClickItemView = monClickItemView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.row_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(mSong!=null){
            Song current=mSong.get(position);
            holder.mStt.setText(current.getId()+"");
            holder.mNameSong.setText(current.getTitle());
            SimpleDateFormat formatTime=new SimpleDateFormat("mm:ss");
            holder.mHours.setText(formatTime.format(current.getDuration()));
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mNameSong, mHours;
        ImageButton mMore;
        TextView mStt;
        ConstraintLayout mConstraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mConstraintLayout=itemView.findViewById(R.id.constraintLayoutItem);
            mNameSong=itemView.findViewById(R.id.namesong);
            mHours=itemView.findViewById(R.id.hours);
            mStt=itemView.findViewById(R.id.stt);
            mMore=itemView.findViewById(R.id.more);
        }
    }
    interface OnClickItemView{
        void clickItem(Song song);
    }
}
