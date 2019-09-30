package com.example.myappmusic;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListSongFragment extends Fragment {

    private RecyclerView mRecyclerView;
    protected SongAdapter songAdapter;
    private TextView mNameSong, mArtist;
    private ImageView mdisk;
    private ConstraintLayout constraintLayout;
    private ImageButton mClickPlay;
    private SharedPreferences mSharedReferences;
    private String Shared_References_Name="com.example.myappmusic";
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.list_song_recyclerview,container, false);
        initView(view);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        setHasOptionsMenu(true);
        mSharedReferences=this.getActivity().getSharedPreferences(Shared_References_Name, Context.MODE_PRIVATE);
        position=mSharedReferences.getInt("position",3);
        mNameSong.setText(mSharedReferences.getString("nameArtist","Name Artist"));

        if(!mSharedReferences.getString("path","").equals(""))
            if(imageArtist(mSharedReferences.getString("path",""))!=null){
                mdisk.setImageBitmap(imageArtist(mSharedReferences.getString("path","")));
            }else
                mdisk.setImageResource(R.drawable.default_cover_art);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public Bitmap imageArtist(String path){
        Log.d("path",path+"//");
        MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(path);
        byte[] data=mediaMetadataRetriever.getEmbeddedPicture();
        if(data!=null){
            return BitmapFactory.decodeByteArray(data,0,data.length);
        }
        return null;
    }
    void initView(View view){
        mRecyclerView=view.findViewById(R.id.recyclerview);
        mArtist=view.findViewById(R.id.Artist);
        mNameSong=view .findViewById(R.id.namePlaySong);
        mNameSong.setSelected(true);
        constraintLayout=view.findViewById(R.id.constraintLayout);
        mClickPlay=view.findViewById(R.id.play);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater=getActivity().getMenuInflater();
        inflater.inflate(R.menu.main,menu);

        MenuItem menuItem=menu.findItem(R.id.app_bar_search);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                songAdapter.
                return false;
            }
        });
    }
}
