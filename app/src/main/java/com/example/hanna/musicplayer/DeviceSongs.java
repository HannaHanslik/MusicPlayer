package com.example.hanna.musicplayer;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hanna on 03.12.2016.
 */

public class DeviceSongs {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private ArrayList<Song> songs;
    private Activity activity;

    public DeviceSongs(Activity activity){
        this.activity = activity;
        songs = new ArrayList<Song>();
    }

    public ArrayList ListAllSongs(){
        try {

            /*if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }*/
            ContentResolver musicResolver = activity.getContentResolver();
            Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
            if(musicCursor!=null && musicCursor.moveToFirst()){
                //get columns
                int titleColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.TITLE);

                int idColumn = musicCursor.getColumnIndex
                        (MediaStore.Audio.Media._ID);

                int urlColumn = musicCursor.getColumnIndex
                        (MediaStore.Audio.Media.DATA);

                int artistColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.ARTIST);
                //add songs to list
                do {
                    long thisId = musicCursor.getLong(idColumn);
                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisArtist = musicCursor.getString(artistColumn);
                    String thisUrl = musicCursor.getString(urlColumn);

                    songs.add(new Song(thisUrl, thisTitle, thisArtist));
                }
                while (musicCursor.moveToNext());
            }
        }
        catch (Exception ex){
             Log.d("MyLog",ex.getMessage());
        }
        return songs;
    }

}
