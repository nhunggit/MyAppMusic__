package com.example.myappmusic;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class FavoriteSongProvider extends ContentProvider {
    private static  final String DB_SONGS="db_song1";
    private static final String AUTHORITY="com.example.myappmusic";
    private static final int DB_VERSION=1;
   static final String CONTENT_PATH="listsongs";
    static final Uri CONTENT_URI=Uri.parse("content://" + AUTHORITY + "/" +CONTENT_PATH );
    private static final String TABLE_FAVORITESONGS="favaritesongs";
    static final String ID_FAVORITE="id";
    static final String ID_PROVIDER="id_provider";
    static final String FAVORITE="is_favorite";
    static final String COUNT="count_of_play";
    static final String CREATE_TABLE_FAVORITESONGS=
            "create table " +TABLE_FAVORITESONGS+"("+
                    ID_FAVORITE+ "integer primary key autoincrement,"+
                    ID_PROVIDER +"integer,"+
                    FAVORITE+"integer default 0,"+
                    COUNT +"integer default 0);";
    private static HashMap<String, String> hashMap;
    private static UriMatcher sUriMacher;
    private static final int URI_ALL_ITEM_CODE=1;
    private static final int URI_ONE_ITEM_CODE=2;
    private SQLiteDatabase database;
    private  static class FavoriteSongDatabase extends SQLiteOpenHelper{

        public FavoriteSongDatabase(@Nullable Context context) {
            super(context, DB_SONGS, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_FAVORITESONGS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_FAVORITESONGS);
            onCreate(db);
        }
    }
    @Override
    public boolean onCreate() {
        FavoriteSongDatabase mFavoriteSongDatabase= new FavoriteSongDatabase(getContext());
        database=mFavoriteSongDatabase.getWritableDatabase();
        if(database==null)
            return false;
        return  true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder=new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_FAVORITESONGS);
        switch (sUriMacher.match(uri)){
            case URI_ALL_ITEM_CODE:
                queryBuilder.setProjectionMap(hashMap);
                break;
            case URI_ONE_ITEM_CODE:
                queryBuilder.appendWhere(ID_FAVORITE+"="+ uri.getPathSegments().get(1));
                break;
        }
        if(sortOrder==null||sortOrder==""){
            sortOrder=ID_FAVORITE;
        }
        Cursor cursor=queryBuilder.query(database, projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMacher.match(uri)){
            case URI_ALL_ITEM_CODE:
                return "com.examle.dir" ;
            case URI_ONE_ITEM_CODE:
                return "com.example.item";
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
