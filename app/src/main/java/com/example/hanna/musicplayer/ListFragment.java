package com.example.hanna.musicplayer;


import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Hanna on 22.11.2016.
 */

public class ListFragment extends android.support.v4.app.ListFragment {

    private ArrayList<Song> songs;
    private ArrayList<Song> selectedSongs;
    private ViewGroup rootView;
    private DeviceSongs deviceSongs;
    OnListSendListener mCallback;
    SongAdapter songAdt;

    public interface OnListSendListener{
        void onListSend(ArrayList<Song> playlist);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnListSendListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Interface playlist send exception");
        }
    }


    public ListFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.list_fragment, container, false);
        deviceSongs = new DeviceSongs(getActivity());
        selectedSongs =  ((MainActivity)getActivity()).getPlaylist();
        if(selectedSongs == null)
            selectedSongs = new ArrayList<>();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            songs = deviceSongs.ListAllSongs();
            songAdt = new SongAdapter(getActivity(), songs, Type.LIST);
            setListAdapter(songAdt);
        }
        catch (Exception ex)
        {
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            if (isVisibleToUser) {
                selectedSongs = ((MainActivity)getActivity()).getPlaylist();
            }
        }
    }

    public void onListItemClick(ListView parent, View view, int position,long arg3) {
        ViewGroup viewg=(ViewGroup)view;
        TextView tv=(TextView)viewg.findViewById(R.id.songTitle);

        if(!selectedSongs.contains(songs.get(position))){
            selectedSongs.add(songs.get(position));
            Toast.makeText(getActivity(), "Add to playlist " + tv.getText().toString(),Toast.LENGTH_SHORT).show();
            mCallback.onListSend(selectedSongs);
        }

        else {
            Toast.makeText(getActivity(),  tv.getText().toString()+ " exists on playlist",Toast.LENGTH_SHORT).show();
        }


    }


}
