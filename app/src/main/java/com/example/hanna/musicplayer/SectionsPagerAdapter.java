package com.example.hanna.musicplayer;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Hanna on 22.11.2016.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private ListFragment listFragment;
    private PlayListFragment playListFragment;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            listFragment  = ListFragment.newInstance(1);
            return listFragment;
        }
        else{
            playListFragment = PlayListFragment.newInstance(1);
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
            case 0:
                return "List";
            case 1:
                return "PlayList";
        }
        return null;
    }

    public Fragment getPage( int position){
        if(position == 0){
            return listFragment;
        }
        else{
            return playListFragment;
        }
    }

}

