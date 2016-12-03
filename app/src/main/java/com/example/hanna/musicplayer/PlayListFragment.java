package com.example.hanna.musicplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hanna on 22.11.2016.
 */

public class PlayListFragment extends ListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<Song> songs;
    private ViewGroup rootView;
    private DeviceSongs deviceSongs;


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
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        songs =deviceSongs.ListAllSongs();
        SongAdapter songAdt = new SongAdapter(getActivity(), songs ,Type.PLAYLIST);
        setListAdapter(songAdt);
    }



    @Override
    public void onListItemClick(ListView l, View view, int position, long id){
        ViewGroup viewg=(ViewGroup)view;
        TextView tv=(TextView)viewg.findViewById(R.id.songTitle);
        Toast.makeText(getActivity(), tv.getText().toString(),Toast.LENGTH_LONG).show();
    }

    public void onPlayClick(View view){
        Toast.makeText(getActivity(),"Play!",Toast.LENGTH_LONG).show();
    }

}
