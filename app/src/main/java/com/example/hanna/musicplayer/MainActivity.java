package com.example.hanna.musicplayer;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LogWriter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements  ListFragment.OnListSendListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ArrayList<Song>  playlist;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }


    public void buttonOnClickPlay(View v){
        ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).playOnClick(v);
    }

    public void buttonOnClickPause(View v){
        ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).pauseOnClick(v);
    }

    public void buttonOnClickStop(View v){
        ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).stopOnClick(v);
    }

    public void buttonOnClickNextSong(View v){
        ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).playNextSong(v);
    }

    public void buttonOnClickPrevSong(View v){
        ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).playPrevSong(v);
    }


    public String getNextSongPath(){
        return ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).getUrlNextSong();
    }

    public String getPrevSongPath(){
        return ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).getUrlPrevSong();
    }

    public String getRandomSongPath(){
        return ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).getRandomUrlSong();
    }

    public int getCountSongs(){
        return ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).getCountSoundsInPlaylist();
    }

    public int getCurrentSongId(){
        return ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).getCurrSongId();
    }

    public void onToggle(View view) {
        ((RadioGroup)view.getParent()).check(view.getId());
        ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).updateButtons();
    }

    @Override
    public void onListSend(ArrayList<Song> playlist) {
        this.playlist = playlist;
    }

    public ArrayList<Song> getPlaylist() {
        return playlist;
    }
}
