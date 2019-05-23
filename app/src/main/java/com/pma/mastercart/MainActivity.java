package com.pma.mastercart;
/////Set your content view before you call findviewbyId methods.//////
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
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pma.mastercart.adapter.HomePageTabsAdapter;
import com.pma.mastercart.model.Comment;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private SearchView searchView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseAuth firebaseAuth;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

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

        ImageButton cart_toolbar_button = (ImageButton) findViewById(R.id.cart_toolbar_button);
        cart_toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(i);
            }
        });

        ImageButton inbox_toolbar_button = (ImageButton) findViewById(R.id.inbox_toolbar_button);
        inbox_toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), InboxActivity.class);
                startActivity(i);
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupNavBar();

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
                            case R.id.nav_add_shop:
                                i = new Intent(getApplicationContext(), AddShopActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_add_category:
                                i = new Intent(getApplicationContext(), AddCategoryActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_edit_category:
                                i = new Intent(getApplicationContext(), EditCategoryActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_profile:
                                i = new Intent(getApplicationContext(), ProfileActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_favorite:
                                i = new Intent(getApplicationContext(), FavoritesActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_cart:
                                i = new Intent(getApplicationContext(), CartActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_orders:
                                i = new Intent(getApplicationContext(), OrdersActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_wallet:
                                i = new Intent(getApplicationContext(), WalletActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_inbox:
                                i = new Intent(getApplicationContext(), InboxActivity.class);
                                startActivity(i);
                                break;
                            case R.id.nav_logout:
                                firebaseAuth.signOut();
                                setupNavBar();
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
    public void onResume(){
        super.onResume();
        setupNavBar();
    }

    private void setupNavBar() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(currentUser!=null) {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_add_category).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_edit_category).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_add_shop).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_cart).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_orders).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_inbox).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_wallet).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        }else {
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_add_category).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_edit_category).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_add_shop).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_cart).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_orders).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_inbox).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_wallet).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();*/
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.settings:
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
