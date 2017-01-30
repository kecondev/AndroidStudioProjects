package kr.co.devinside.tablayoutdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by DevInside on 2017. 1. 30..
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tab1Fragment tab1 = new Tab1Fragment();
                return  tab1;
            case 1:
                Tab1Fragment tab2 = new Tab1Fragment();
                return  tab2;
            case 2:
                Tab1Fragment tab3 = new Tab1Fragment();
                return  tab3;
            case 3:
                Tab1Fragment tab4 = new Tab1Fragment();
                return  tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
