package com.example.myappmusic;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class MediaPlaybackService extends Service {
    private static  final String NOTIFICATIONN_CHANNEL_ID="1";
    private static final String ACTION_PREVIOUS="xxx.yyy.zzz.ACTION_PREVIOUS";
    private static final String ACTION_NEXT="xxx.yyy.zzz.ACTION_NEXT";
    private static final String Action_PLAY="xxx.yyy.ACTION_PLAY";
    private Binder binder=new MusicBinder();
    private MediaPlayer mMediaPlayer=null;
    private Lisenner mLisenner;
    private String mPath="";
    private  String mArtist="";
    private String mNameSong="";
    private int mPositionCurrent=0;
    private int mLoopSong=0;
    private boolean mShuffleSong=false;
    private ArrayList<Song> mListSong=new ArrayList<>();
    private String SHARE_PREFERENCES_NAME="com.example.myappmusic";
    private SharedPreferences mSharePreferences;
    private ConnectServiceFragmentInterface mConnectServiceFragment;

    public static String getNotificationnChannelId() {
        return NOTIFICATIONN_CHANNEL_ID;
    }

    public static String getActionPrevious() {
        return ACTION_PREVIOUS;
    }

    public static String getActionNext() {
        return ACTION_NEXT;
    }

    public static String getAction_PLAY() {
        return Action_PLAY;
    }

    public Binder getBinder() {
        return binder;
    }

    public void setBinder(Binder binder) {
        this.binder = binder;
    }

    public MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }

    public void setmMediaPlayer(MediaPlayer mMediaPlayer) {
        this.mMediaPlayer = mMediaPlayer;
    }

    public Lisenner getmLisenner() {
        return mLisenner;
    }

    public void setmLisenner(Lisenner mLisenner) {
        this.mLisenner = mLisenner;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String getmArtist() {
        return mArtist;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
    }

    public String getmNameSong() {
        return mNameSong;
    }

    public void setmNameSong(String mNameSong) {
        this.mNameSong = mNameSong;
    }

    public int getmPositionCurrent() {
        return mPositionCurrent;
    }

    public void setmPositionCurrent(int mPositionCurrent) {
        this.mPositionCurrent = mPositionCurrent;
    }

    public int getmLoopSong() {
        return mLoopSong;
    }

    public void setmLoopSong(int mLoopSong) {
        this.mLoopSong = mLoopSong;
    }

    public boolean ismShuffleSong() {
        return mShuffleSong;
    }

    public void setmShuffleSong(boolean mShuffleSong) {
        this.mShuffleSong = mShuffleSong;
    }

    public ArrayList<Song> getmListSong() {
        return mListSong;
    }

    public void setmListSong(ArrayList<Song> mListSong) {
        this.mListSong = mListSong;
    }

    public String getSHARE_PREFERENCES_NAME() {
        return SHARE_PREFERENCES_NAME;
    }

    public void setSHARE_PREFERENCES_NAME(String SHARE_PREFERENCES_NAME) {
        this.SHARE_PREFERENCES_NAME = SHARE_PREFERENCES_NAME;
    }

    public SharedPreferences getmSharePreferences() {
        return mSharePreferences;
    }

    public void setmSharePreferences(SharedPreferences mSharePreferences) {
        this.mSharePreferences = mSharePreferences;
    }

    public ConnectServiceFragmentInterface getmConnectServiceFragment() {
        return mConnectServiceFragment;
    }

    public void setmConnectServiceFragment(ConnectServiceFragmentInterface mConnectServiceFragment) {
        this.mConnectServiceFragment = mConnectServiceFragment;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSharePreferences= getSharedPreferences(SHARE_PREFERENCES_NAME, MODE_PRIVATE);
        mPositionCurrent=mSharePreferences.getInt("position",3);
        mNameSong=mSharePreferences.getString("nameSong","Name Song");
        mArtist=mSharePreferences.getString("nameArtist","Name Artist");
        mPath=mSharePreferences.getString("path","");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(isMusicPlay()){
            Log.d("getAction",intent.getAction()+"");
            switch (intent.getAction()){
                case ACTION_PREVIOUS:
                    previousSong();
                    break;
                case ACTION_NEXT:
                   // nextSong();
                    break;
                case Action_PLAY:
                    if(mMediaPlayer.isPlaying()){
                        //pauseSong();
                    }else{
                        //playingSong();
                    }
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public void previousSong(){
        mMediaPlayer.pause();
        if(getmPositionCurrent()<= 300){
            if(mShuffleSong==true){
                mPositionCurrent=actionShuffleSong();
            }else{
                if(mPositionCurrent==mListSong.size()-1)
                    mPositionCurrent=0;
                else mPositionCurrent++;
            }
            mLisenner.onItemLisenner();
            //playSong
        }
    }
    public String getDuration(){
        SimpleDateFormat formatTime= new SimpleDateFormat("mm:ss");
        return formatTime.format(mMediaPlayer.getDuration());
    }
    public int actionShuffleSong(){
        Random rd=new Random();
        int result=rd.nextInt(mListSong.size()-1);
        return result;
    }
    public boolean isMusicPlay(){
        if(mMediaPlayer!=null)
            return true;
        return false;
    }
    public void playSong(int mPositionCurrent) throws IOException {
        mMediaPlayer=new MediaPlayer();
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
        try{
            Log.d("play song",mPositionCurrent+"//"+mListSong.size());
            for(int i=0;i<=mListSong.size()-1;i++){
                if(mListSong.get(i).getId()==mPositionCurrent){
                    Log.d("mPath",mListSong.get(i).getFile());
                    Uri content_uri=Uri.parse(mListSong.get(i).getFile());
                    mMediaPlayer.setDataSource(getApplicationContext(),content_uri);
                    mMediaPlayer.prepare();
                    mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.start();
                    mPath=mListSong.get(i).getFile();
                    mNameSong=mListSong.get(i).getTitle();
                    mArtist=mListSong.get(i).getArtist();
                    showNotification
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    public void showNotification(String nameSong,String artist,String path){
        createNotificationChanel();

        Intent notificationIntent=new Intent(this, ActivityMusic.class)
    }
    @SuppressLint("ObsoleteSdkInt")
    public void createNotificationChanel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel musicServiceChanel=new NotificationChannel(
                    NOTIFICATIONN_CHANNEL_ID,
                    "Music Service Chanel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            musicServiceChanel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(musicServiceChanel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public interface ConnectServiceFragmentInterface{
        void onActionConnectServiceFragment();
    }
    public interface Lisenner{
        void onItemLisenner();
        void actionNotification();
    }
    class MusicBinder extends Binder{
        public MediaPlaybackService getMusicBinder(){
            return  MediaPlaybackService.this;
        }
    }
}
