package com.example.hanna.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hanna on 03.12.2016.
 */


public class SongAdapter extends BaseAdapter {
    private ArrayList<Song> songs;
    private LayoutInflater songInf;
    private Type type;




    public SongAdapter(Context c, ArrayList<Song> theSongs,Type type){
        songs=theSongs;
        songInf=LayoutInflater.from(c);
        this.type = type;
    }


    public void unSelectedIndex(int ind){

    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //map to song layout
        LinearLayout songLay;

        if(type == Type.LIST)
            songLay  = (LinearLayout)songInf.inflate(R.layout.list_song, parent, false);
        else
            songLay = (LinearLayout)songInf.inflate(R.layout.playlist_song, parent, false);

        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.songTitle);
        TextView artistView = (TextView)songLay.findViewById(R.id.songPerformer);

        //get song using position
        Song currSong = songs.get(position);

        //get title and artist strings
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getPerformer());

        //set position as tag
        songLay.setTag(position);

        return songLay;
    }
}
