package com.example.hanna.musicplayer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Hanna on 22.11.2016.
 */

public class ListFragment extends android.support.v4.app.ListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<Song> songs;
    private ViewGroup rootView;
    private DeviceSongs deviceSongs;

    private String error="";


    public ListFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListFragment newInstance(int sectionNumber) {

        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.list_fragment, container, false);
        deviceSongs = new DeviceSongs(getActivity());
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            songs = deviceSongs.ListAllSongs();
            SongAdapter songAdt = new SongAdapter(getActivity(), songs, Type.LIST);
            setListAdapter(songAdt);
        }
        catch (Exception ex)
        {
            error = ex.getMessage();
        }
    }

    public void onListItemClick(ListView l, View view, int position, long id){
        ViewGroup viewg=(ViewGroup)view;
        TextView tv=(TextView)viewg.findViewById(R.id.songTitle);
        Toast.makeText(getActivity(), tv.getText().toString(),Toast.LENGTH_LONG).show();
    }

}
