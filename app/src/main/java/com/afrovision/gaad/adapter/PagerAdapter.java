package com.afrovision.gaad.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.afrovision.gaad.fragment.LearningLeaders;
import com.afrovision.gaad.fragment.SkillLeaders;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    static ArrayList<FragmentProperties> fragments = new ArrayList<>();
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment f,String name) {
        fragments.add (new FragmentProperties (f,name));
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new LearningLeaders();
        switch (position) {
            case 0:
                fragment = new LearningLeaders();
                break;
            case 1:
                fragment = new SkillLeaders();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size ();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get (position).getFragmentName ();
    }


    public void clear() {
        fragments.clear ();
    }

}

class FragmentProperties{
    public Fragment fragment;
    public String fragmentName;

    public FragmentProperties(Fragment fragment , String fragmentName) {
        this.fragment = fragment;
        this.fragmentName = fragmentName;
    }
    public String getFragmentName() {
        return fragmentName;
    }
}


