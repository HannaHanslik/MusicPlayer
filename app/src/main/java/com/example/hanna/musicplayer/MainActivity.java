package com.example.hanna.musicplayer;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
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

    //<uses-permission android:name="android.permission.WRITE_SETTINGS" />    private SectionsPagerAdapter mSectionsPagerAdapter;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private PlayListSaver playListSaver;

    private ArrayList<Song>  playlist;
    private final static String  PLAYLIST = "playlist";

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

        //playListSaver = new PlayListSaver(this);
        //setAutoOrientationEnabled(this,false);
    }

    /*public static void setAutoOrientationEnabled(Context context, boolean enabled)
    {
        Settings.System.putInt( context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }*/



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

    public void buttonDelClick(View v){
        ((PlayListFragment)mSectionsPagerAdapter.getPage(mViewPager.getCurrentItem())).buttonDelClick(v);
    }

    @Override
    public void onListSend(ArrayList<Song> playlist) {

        this.playlist = playlist;
    }

    public ArrayList<Song> getPlaylist() {
        return playlist;
    }
}
