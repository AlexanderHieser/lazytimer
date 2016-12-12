package something.hackinghieser.lazytimer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import something.hackinghieser.lazytimer.base.BaseFragment;
import something.hackinghieser.lazytimer.fragments.TimeBaseFragment;

/**
 * Created by Alexander Hieser on 03.12.2016.
 */

public class SimpleTimePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<TimeBaseFragment> fragments;
    private TimeBaseFragment currentFragment;

    public SimpleTimePagerAdapter(FragmentManager fm, ArrayList<TimeBaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        currentFragment = fragments.get(position);
        return currentFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).Titel;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public TimeBaseFragment getActualFragment() {
        return currentFragment;
    }
}
