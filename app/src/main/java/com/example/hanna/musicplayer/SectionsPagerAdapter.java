package com.example.hanna.musicplayer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Hanna on 22.11.2016.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private ListFragment listFragment;
    private PlayListFragment playListFragment;
    private Activity activity;

    public SectionsPagerAdapter(FragmentManager fm, Activity activity)
    {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 1){
            listFragment  = ListFragment.newInstance();
            return listFragment;
        }
        else{
            playListFragment = PlayListFragment.newInstance();
            return playListFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "List";
            case 0:
                return "PlayList";
        }
        return null;
    }

    public Fragment getPage( int position){
        if(position == 1){
            return listFragment;
        }
        else{
            return playListFragment;
        }
    }

}

