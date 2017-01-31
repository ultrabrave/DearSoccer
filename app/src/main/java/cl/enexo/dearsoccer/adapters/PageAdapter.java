package cl.enexo.dearsoccer.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cl.enexo.dearsoccer.views.listMatchs.fragments.created.CreatedFragment;
import cl.enexo.dearsoccer.views.listMatchs.fragments.match.MatchFragment;
import cl.enexo.dearsoccer.views.listMatchs.fragments.toplay.ToPlayFragment;

/**
 * Created by Kevin on 29-11-2016.
 */

public class PageAdapter extends FragmentPagerAdapter {
    private Context context;

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    public PageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position){
            case 0:
                return MatchFragment.newInstance();
            case 1:
                return CreatedFragment.newInstance();
            case 2:
                return ToPlayFragment.newInstance();
            default:
                return MatchFragment.newInstance();
        }
    }


    @Override
    public int getCount() {return 3;}

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "MATCH";
            case 1:
                return "CREATED";
            case 2:
                return "TO PLAY";
            default:
                return "MATCH";
        }

    }
}
