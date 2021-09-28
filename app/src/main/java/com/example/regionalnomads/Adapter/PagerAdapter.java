package com.example.regionalnomads.Adapter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private final java.util.List<androidx.fragment.app.Fragment> mFragmentList = new ArrayList<>();
    private final java.util.List<String> mFragmentTitleList = new ArrayList<String>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(androidx.fragment.app.Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }

    @Override
    public androidx.fragment.app.Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
