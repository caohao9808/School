package org.fengye.school.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.fengye.school.base.BaseSimpleFragment;

import java.util.ArrayList;

public class VPStateAdapter extends FragmentStatePagerAdapter {

    private ArrayList<BaseSimpleFragment> fragments;

    public VPStateAdapter(FragmentManager fm, ArrayList<BaseSimpleFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public VPStateAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
