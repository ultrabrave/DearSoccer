package cl.enexo.dearsoccer.views.listMatchs.principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Date;

import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.adapters.ViewPagerAdapter;
import cl.enexo.dearsoccer.views.config.ConfigActivity;
import cl.enexo.dearsoccer.views.createMatch.NewMatchActivity;
import cl.enexo.dearsoccer.views.listMatchs.fragments.CreatedFragment;
import cl.enexo.dearsoccer.views.listMatchs.fragments.MatchFragment;
import cl.enexo.dearsoccer.views.listMatchs.fragments.ToPlayFragment;

import static android.R.attr.description;
import static android.R.attr.duration;
import static android.R.attr.type;

/**
 * Created by Kevin on 21-01-2017.
 */

public class PrincipalActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    LinearLayout lineaconfig;
    private LinearLayout btnadd;

    private int[] tabIcons = {
            R.drawable.lupagreen,
            R.drawable.maswhitepng,
            R.drawable.pelotawhite2
    };

    private int[] tabIcons2 = {
            R.drawable.lupawhite,
            R.drawable.masverdepng,
            R.drawable.pelotawhite2

    };

    private int[] tabIcons3 = {
            R.drawable.lupawhite,
            R.drawable.maswhitepng,
            R.drawable.pelotaverde
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_principal);
            btnadd = (LinearLayout) findViewById(R.id.btnaddconfigprincipal);

            btnadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),NewMatchActivity.class);
                    Date date = new Date();
                    String hourstring = date.getHours() + ":" + date.getMinutes();
                    i.putExtra("NAME","");
                    i.putExtra("DATE","");
                    i.putExtra("HOUR",hourstring);
                    i.putExtra("PLACE","");
                    i.putExtra("DURATION", "");
                    i.putExtra("PRICE", "");
                    i.putExtra("TYPE","");
                    i.putExtra("DESCRIPTION", "");
                    i.putExtra("LAT", 0.0);
                    i.putExtra("LON", 0.0);
                    i.putExtra("COUNTRY","");
                    i.putExtra("CITY","");
                    i.putExtra("LOCALITY","");
                    startActivity(i);
                }
            });
            lineaconfig = (LinearLayout) findViewById(R.id.btnconfigprincipal);


            lineaconfig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(PrincipalActivity.this, ConfigActivity.class);
                    i.putExtra("WHERE","CONFIG");
                    startActivity(i);
                }
            });
            viewPager = (ViewPager) findViewById(R.id.viewPager);

        }
        catch (Exception e)
        {
            e.getMessage();
        }

        try {

            tabLayout = (TabLayout) findViewById(R.id.tabLayout);

            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            viewPagerAdapter.addFragments(new MatchFragment(), "Partidos");
            viewPagerAdapter.addFragments(new CreatedFragment(), "Creados");
            viewPagerAdapter.addFragments(new ToPlayFragment(), "Participando");
            viewPager.setOffscreenPageLimit(3);

            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setOffscreenPageLimit(3);
            tabLayout.setupWithViewPager(viewPager);

            setupTabIcons();

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if(tab.getPosition() == 0)
                    {
                        setupTabIcons();
                    }
                    if(tab.getPosition() == 1)
                    {
                        setupTabIcons2();
                    }
                    if(tab.getPosition() == 2)
                    {
                        setupTabIcons3();
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupTabIcons2() {
        tabLayout.getTabAt(0).setIcon(tabIcons2[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons2[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons2[2]);
    }

    private void setupTabIcons3() {
        tabLayout.getTabAt(0).setIcon(tabIcons3[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons3[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons3[2]);
    }
}