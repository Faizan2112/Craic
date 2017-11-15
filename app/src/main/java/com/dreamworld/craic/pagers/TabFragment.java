package com.dreamworld.craic.pagers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamworld.craic.R;
import com.dreamworld.craic.fragments.AllContentFragment;
import com.dreamworld.craic.fragments.FavouriteFragment;

/**
 * Created by faizan on 10/11/2017.
 */
public class TabFragment extends Fragment {
    public static TabLayout tabLayout;

    public static ViewPager viewPager;
    // number of tabs going to be used
    public static int int_items = 2;
    //create key and string value
    private static final String FLAG_CURRENT_STATE = "current_state";

    @Nullable

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            int i = savedInstanceState.getInt(FLAG_CURRENT_STATE);

        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
/**
 *Inflate tab_layout and setup Views.
 */

        View tabView = inflater.inflate(R.layout.fragment_tab_layout, null);
        // @SuppressLint("InflateParams") View tabView =  inflater.inflate(R.layout.fragment_tab_layout,null);

        tabLayout = (TabLayout) tabView.findViewById(R.id.tab_tab_view);
        viewPager = (ViewPager) tabView.findViewById(R.id.tab_view_pager);
        // viewPager.setOffscreenPageLimit(3);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));


        // getChildFragmentManager does not support below V4 version
        final int[] ICONS = new int[]{
                R.drawable.mydiary,
                R.drawable.mydiary,
                };

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(ICONS[0]).setText("All");
                tabLayout.getTabAt(1).setIcon(ICONS[1]).setText("Favourite");
                          }
        });
        return tabView;

    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        /**
         * Return fragment with respect to Position .
         *
         * @Override
         */
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AllContentFragment();
                case 1:
                    return new FavouriteFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;

        }
    }
     public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0 :
                    return "All";
                case 1:
                    return "Favourite" ;



            }
            return null;
        }
}