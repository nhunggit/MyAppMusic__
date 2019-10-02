package com.example.myappmusic;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> implements Filterable {
private ArrayList<Song> mListSong=new ArrayList<>();
private ArrayList<Song> mSong;
private LayoutInflater mInflater;
private Context context;
private OnClickItemView mClickItemView;
private MediaPlaybackService mMusicService;
private String mTypeSong="";

    public MediaPlaybackService getmMusicService() {
        return mMusicService;
    }

    public ArrayList<Song> getmListSong() {
        return mListSong;
    }

    public void setmListSong(ArrayList<Song> mListSong) {
        this.mListSong = mListSong;
    }

    public void setmMusicService(MediaPlaybackService mMusicService) {
        this.mMusicService = mMusicService;
    }

    public String getmTypeSong() {
        return mTypeSong;
    }

    public void setmTypeSong(String mTypeSong) {
        this.mTypeSong = mTypeSong;
    }

    public SongAdapter(OnClickItemView mClickItemView, Context context){
        mInflater=LayoutInflater.from(context);
        context=context;
        this.mClickItemView=mClickItemView;
    }
    interface OnClickItemView{
        void clickItem(Song song);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.row_recyclerview,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if(mSong!=null){
            final Song current=mSong.get(position);
            holder.mStt.setText(current.getId()+"");
            holder.mNameSong.setText(current.getTitle());
            SimpleDateFormat formatTime=new SimpleDateFormat("mm:ss");
            holder.mHour.setText(formatTime.format(current.getDuration()));

            final Song finalCurrent= current;
            holder.mConstraintLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mClickItemView.clickItem(finalCurrent);
                }
            });
            if(mMusicService!=null){
                mMusicService.setmListSong(mSong);
                if(mMusicService.getmNameSong().equals(mSong.get(position).getTitle())){
                    holder.mStt.setText("");
                    holder.mNameSong.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
                    holder.mStt.setBackgroundResource(R.drawable.ic_equalizer_black_24dp);
                }else{
                    holder.mNameSong.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
                    holder.mStt.setBackgroundResource(R.drawable.ic_equalizer_black_24dp);
                }
            }

            holder.mMore.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu=new PopupMenu(context, holder.mMore);
                    if(mTypeSong.equals("AllSong")){
                        popupMenu.inflate(R.menu.add_song);
                    }
                    if(mTypeSong.equals("FavoriteSong")){
                        popupMenu.inflate(R.menu.remove_song);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.addFavorite:
                                    ContentValues values=new ContentValues();
                                    values.put(Fava)
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mSong!=null)
            return mSong.size();
        else
            return 0;
    }

    public void setSong(ArrayList<Song> songs){
        mSong=songs;
        Log.d("size2", songs.size()+"//");
        notifyDataSetChanged();
    }

    public void updateList(ArrayList<Song> songs){
        mSong=songs;
        mListSong=new ArrayList<>(mSong);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Song> filterList=new ArrayList<>();
            if(constraint==null|| constraint.length()==0){
                filterList.addAll(mListSong);
            }else{
                String filterpattern=unAccent(constraint.toString().toLowerCase().trim());
                for(Song song:mListSong){
                    if(unAccent(song.getTitle().toLowerCase()).contains(filterpattern)){
                        filterList.add(song);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mSong.clear();
            mSong.addAll((Collection<? extends Song>)results.values;
            notifyDataSetChanged();
        }
    };
    public static String unAccent(String s){
        String temp= Normalizer.normalize(s,Normalizer.Form.NFD);
        Pattern pattern=Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ","D").replace("đ","d");
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mNameSong, mHour;
        ImageButton mMore;
        TextView mStt;
        ConstraintLayout mConstraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mConstraintLayout=itemView.findViewById(R.id.constraintLayoutItem);
            mNameSong=itemView.findViewById(R.id.namesong);
            mHour=itemView.findViewById(R.id.hours);
            mStt=itemView.findViewById(R.id.stt);
            mMore=itemView.findViewById(R.id.more);
        }
    }
}
