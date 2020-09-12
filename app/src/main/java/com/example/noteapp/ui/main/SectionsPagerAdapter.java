package com.example.noteapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.noteapp.Fragments.ArchiveFragment;
import com.example.noteapp.Fragments.CalendarFragment;
import com.example.noteapp.Fragments.NotesFragment;
import com.example.noteapp.Fragments.CheckListFragment;
import com.example.noteapp.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.Archive, R.string.Calendar, R.string.Reminder, R.string.Notes};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment = null;
       switch (position){
           case 0:
               fragment = new ArchiveFragment();
               break;
           case 1:
               fragment = new CalendarFragment();
               break;
           case 2:
               fragment = new CheckListFragment();
               break;
           case 3:
               fragment = new NotesFragment();
               break;
       }
       return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }
}