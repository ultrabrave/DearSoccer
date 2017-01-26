package cl.enexo.dearsoccer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Kevin on 21-01-2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();
    ArrayList<ImageView> imagen = new ArrayList<>();

    public void addFragments(Fragment fragments, String titles)
    {
        this.fragments.add(fragments);
        this.tabTitles.add(titles);
    }

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

}