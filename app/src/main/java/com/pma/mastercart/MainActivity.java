package com.pma.mastercart;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.pma.mastercart.adapter.HomePageTabsAdapter;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private SearchView searchView;
    private TabLayout tabLayout;
    private ViewPager viewPager;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        HomePageTabsAdapter adapter = new HomePageTabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);



        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        switch(menuItem.getItemId()) {
                            case R.id.nav_login:

                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                                break;
                        }
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        drawerLayout.addDrawerListener(
            new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    // Respond when the drawer's position changes
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    // Respond when the drawer is opened
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    // Respond when the drawer is closed
                }

                @Override
                public void onDrawerStateChanged(int newState) {
                    // Respond when the drawer motion state changes
                }
            }
        );
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();*/
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
