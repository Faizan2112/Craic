package com.dreamworld.craic.pagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dreamworld.craic.fragments.AllContentFragment;
import com.dreamworld.craic.fragments.FavouriteFragment;

/**
 * Created by faizan on 10/11/2017.
 */

public class MainPager extends FragmentStatePagerAdapter {
    int tabCount;

    public MainPager(FragmentManager fm, int tabCount) {
        super(fm);

        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                AllContentFragment allContentFragment = new AllContentFragment();
                return allContentFragment ;

            case 1:
                FavouriteFragment favouriteFragment = new FavouriteFragment();
                return favouriteFragment ;


           default:
            return null;
        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
