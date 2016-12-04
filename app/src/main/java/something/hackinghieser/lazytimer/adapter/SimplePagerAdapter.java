package something.hackinghieser.lazytimer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;

import something.hackinghieser.lazytimer.base.BaseFragment;

/**
 * Created by Alexander Hieser on 03.12.2016.
 */

public class SimplePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> fragments;

    public SimplePagerAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitel();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
