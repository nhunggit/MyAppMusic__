//package com.example.myappmusic;
//
//import android.Manifest;
//import android.app.ActivityManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//public class ActivityMusic extends AppCompatActivity implements{
//    private MediaPlaybackService mMusicService;
//    private boolean mExitService= false;
//    private  MediaPlaybackFragment mMediaPlaybackFragment=new MediaPlaybackFragment();
//    private ListSongFragment mListSongFragment=new ListSongFragment();
//    String SHARED_PREFERENCES_NAME="com.example.myappmusic";
//    private SharedPreferences mSharedPreferences;
//    public ServiceConnection mServiceConnection=new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            MediaPlaybackService.MusicBinder binder=(MediaPlaybackService.MusicBinder) iBinder;
//            mMusicService=binder.getMusicBinder();
//           // mMediaPlaybackFragment.setmMusicService(mMusicService);
//            mExitService=true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            Toast.makeText(mMusicService,"dis",Toast.LENGTH_SHORT).show();
//        }
//    };
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(isMyServiceRunning(MediaPlaybackService.class)){
//            connectService();
//        }else{
//            startService();
//            connectService();
//        }
//    }
//    public void startService(){
//        Intent intent= new Intent(ActivityMusic.this,MediaPlaybackService.class);
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
//            startService(intent);
//        }
//    }
//    public void connectService(){
//        Intent intent= new Intent(ActivityMusic.this,MediaPlaybackService.class);
//        bindService(intent,mServiceConnection,0);
//    }
//    private boolean isMyServiceRunning(Class<?> serviceClass){
//        ActivityManager manager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        for(ActivityManager.RunningServiceInfo service :manager.getRunningServices(Integer.MAX_VALUE)){
//            if(serviceClass.getName().equals(service.service.getClassName())){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unbindService(mServiceConnection);
//    }
//
//    //    @Override
////    public void onCreate(@Nullable Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////        initPermission();
////
////        //Toolbar
////        DrawerLayout drawer=findViewById(R.id.drawer_layout);
////
////    }
////    public void initPermission(){
////        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
////            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
////                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
////                    Toast.makeText(ActivityMusic.this,"Permission isn't granted",Toast.LENGTH_SHORT).show();
////                }
////                else{
////                    Toast.makeText(ActivityMusic.this,"Permission don't granted and don't show dialog again",Toast.LENGTH_SHORT).show();
////                }
////                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
////            }
////        }
////    }
//}