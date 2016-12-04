package com.example.hanna.musicplayer;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Hanna on 22.11.2016.
 */

public class PlayListFragment extends ListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<Song> songs;
    private ViewGroup rootView;
    private DeviceSongs deviceSongs;
    private SongPlayer songPlayer;
    private int currPlaySongID;

    public PlayListFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlayListFragment newInstance(int sectionNumber) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.playlist_fragment, container, false);
        deviceSongs = new DeviceSongs(getActivity());
        songPlayer = new SongPlayer((MainActivity)getActivity());

        final ProgressBar progressBarMusic = (ProgressBar)rootView.findViewById(R.id.progressBarMusic);
        progressBarMusic.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    Display display = getActivity().getWindowManager().getDefaultDisplay();

                    Point size = new Point();
                    display.getSize(size);

                    int width = size.x;
                    int x = (int)event.getX();

                    double percentage = (double)x/(double)width;
                    progressBarMusic.setProgress(300);
                    songPlayer.MoveTo(percentage);

                    return true;
                }
                return false;
            }

        });

        ((RadioGroup)rootView.findViewById(R.id.toggleGroup)).setOnCheckedChangeListener(ToggleListener);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        songs =deviceSongs.ListAllSongs();
        SongAdapter songAdt = new SongAdapter(getActivity(), songs ,Type.PLAYLIST);
        setListAdapter(songAdt);
    }


    static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
            }
        }
    };

    @Override
    public void onListItemClick(ListView l, View view, int position, long id){
        ViewGroup viewg=(ViewGroup)view;
        TextView tv=(TextView)viewg.findViewById(R.id.songTitle);
        Toast.makeText(getActivity(), tv.getText().toString(),Toast.LENGTH_LONG).show();

        currPlaySongID = position;
        Song currSong = songs.get(position);
        songPlayer.setSong(currSong.getUrl());
    }

    public void onPlayClick(View view){
        Toast.makeText(getActivity(),"Play!",Toast.LENGTH_LONG).show();
    }

    public String getUrlNextSong(){
        if (songs.size() < (currPlaySongID+2)){
            return "";
        }

        currPlaySongID++;
        Song currSong = songs.get(currPlaySongID);
        return currSong.getUrl();
    }

    public String getUrlPrevSong(){
        if (currPlaySongID == 0)
            return "";

        currPlaySongID--;
        Song currSong = songs.get(currPlaySongID);
        return currSong.getUrl();
    }

    public String getRandomUrlSong(){
        Random generator = new Random();
        int i = generator.nextInt(songs.size());

        Song currSong = songs.get(i);
        return currSong.getUrl();
    }

    public void playOnClick(View v) { songPlayer.start(); }

    public void pauseOnClick(View v){
        songPlayer.pause();
    }

    public void stopOnClick(View v){
        songPlayer.stop();
    }

    public void playNextSong(View v) { songPlayer.nextSong(); }

    public void playPrevSong(View v) { songPlayer.prevSong();}

    @Override
    public void onDestroy(){
        songPlayer.release();
        /*if (songPlayer.isPlaying()) {
            songPlayer.stop();
        }*/
        songPlayer.destroyCallback();
        super.onDestroy();
    }
}
