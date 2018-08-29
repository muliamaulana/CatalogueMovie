package com.muliamaulana.cataloguemovie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.muliamaulana.cataloguemovie.fragment.NowPlayingFragment;
import com.muliamaulana.cataloguemovie.fragment.PopularFragment;
import com.muliamaulana.cataloguemovie.fragment.UpcomingFragment;

/**
 * Created by muliamaulana on 18/03/18.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_TABS = 3;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UpcomingFragment();
            case 1:
                return new NowPlayingFragment();
            case 2:
                return new PopularFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}
