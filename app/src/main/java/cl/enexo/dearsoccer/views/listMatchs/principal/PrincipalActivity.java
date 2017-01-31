package cl.enexo.dearsoccer.views.listMatchs.principal;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import cl.enexo.dearsoccer.R;
import cl.enexo.dearsoccer.adapters.ViewPagerAdapter;
import cl.enexo.dearsoccer.views.config.ConfigActivity;
import cl.enexo.dearsoccer.views.createMatch.NewMatchActivity;
import cl.enexo.dearsoccer.views.listMatchs.fragments.created.CreatedFragment;
import cl.enexo.dearsoccer.views.listMatchs.fragments.match.MatchFragment;
import cl.enexo.dearsoccer.views.listMatchs.fragments.toplay.ToPlayFragment;
/**
 * Created by Kevin on 21-01-2017.
 */
public class PrincipalActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private int[] tabIcons = {R.drawable.searchgreen, R.drawable.addwhite, R.drawable.ballwhite};
    private int[] tabIcons2 = {R.drawable.searchwhite, R.drawable.addgreen, R.drawable.ballwhite};
    private int[] tabIcons3 = {R.drawable.searchwhite, R.drawable.addwhite, R.drawable.ballgreen};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_principal);
            LinearLayout btnadd = (LinearLayout) findViewById(R.id.btnaddconfigprincipal);
            btnadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),NewMatchActivity.class);
                    intent.putExtra("NAME","");
                    intent.putExtra("DATE","");
                    intent.putExtra("HOUR","00:00");
                    intent.putExtra("PLACE","");
                    intent.putExtra("DURATION", "");
                    intent.putExtra("PRICE", "");
                    intent.putExtra("TYPE","");
                    intent.putExtra("DESCRIPTION", "");
                    intent.putExtra("LAT", "0.0");
                    intent.putExtra("LON", "0.0");
                    intent.putExtra("COUNTRY","");
                    intent.putExtra("CITY","");
                    intent.putExtra("LOCALITY","");
                    intent.putExtra("FROM","Principal");
                    startActivity(intent);
                }
            });
            LinearLayout lineaconfig = (LinearLayout) findViewById(R.id.btnconfigprincipal);
            lineaconfig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(PrincipalActivity.this, ConfigActivity.class);
                    i.putExtra("WHERE","CONFIG");
                    startActivity(i);}
            });
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
            tabLayout = (TabLayout) findViewById(R.id.tabLayout);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            viewPagerAdapter.addFragments(new MatchFragment(), "Partidos");
            viewPagerAdapter.addFragments(new CreatedFragment(), "Creados");
            viewPagerAdapter.addFragments(new ToPlayFragment(), "Participando");
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setOffscreenPageLimit(3);
            tabLayout.setupWithViewPager(viewPager);
            setupTabIcons();
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if(tab.getPosition() == 0) {
                        setupTabIcons();}
                    else if(tab.getPosition() == 1) {
                        setupTabIcons2();}
                    else{
                        setupTabIcons3();}
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }
                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
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