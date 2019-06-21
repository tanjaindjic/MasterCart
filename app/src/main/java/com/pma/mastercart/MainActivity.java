package com.pma.mastercart;
/////Set your content view before you call findviewbyId methods.//////
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pma.mastercart.adapter.HomePageTabsAdapter;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.RetrieveProductListTask;
import com.pma.mastercart.asyncTasks.RetrieveProductsTask;
import com.pma.mastercart.asyncTasks.RetrieveShopListTask;
import com.pma.mastercart.asyncTasks.RetrieveShopsTask;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;
import com.pma.mastercart.model.enums.Role;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    public static ViewPager viewPager;
    public static String URL = "http://192.168.15.147:8096/";
    public static ArrayList<Product> products = new ArrayList();
    public static ArrayList<Shop> shops = new ArrayList();
    public static ProgressDialog progress;
    private GridView gridView;
    public static HomePageTabsAdapter adapter;
    public static Context appContext;
    public static final String PREFS= "MasterCartPrefs";
    private Spinner categorySpinner;
    private ArrayList<Category> categs;
    private TextView userNameTextView;
    private String currentUserFirstName;
    private User currentUser;
    private SearchView search_field;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    private void loadData(Long i) throws ExecutionException, InterruptedException {
        AsyncTask<Long, Void, ArrayList<Product>> task = new RetrieveProductsTask().execute(i);
        products = task.get();

        AsyncTask<String, Void, ArrayList<Shop>> task2 = new RetrieveShopsTask().execute("sta god");
        shops = task2.get();
    }

    private static void updateData(Long productUpdates, Long shopUpdates) throws ExecutionException, InterruptedException {
        if(productUpdates!=null){
            AsyncTask<Long, Void, Product> task = new RetrieveProductListTask().execute(productUpdates);
            Product productU = task.get();
            int index = getIndexProduct(productU.getId());
            if(index!=-1)
                products.set(index, productU);
        }
        if(shopUpdates!=null){
            AsyncTask<Long, Void, Shop> task = new RetrieveShopListTask().execute(shopUpdates);
            Shop shopU = task.get();
            int index = getIndexShops(shopU.getId());
            if(index!=-1)
                shops.set(index, shopU);
        }
    }
    private static int getIndexShops(Long id) {
        for(Shop p : shops){
            if(p.getId()==id)
                return shops.indexOf(p);
        }
        return -1;
    }

    private static int getIndexProduct(Long id) {
        for(Product p : products){
            if(p.getId()==id)
                return products.indexOf(p);
        }
        return -1;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ONTEST", "restart");
        //progress.show();
        //setupNavBar();
        long productUpdates = this.getIntent().getLongExtra("productUpdate", -1);
        long shopUpdates = this.getIntent().getLongExtra("shopUpdate", -1);
        Long pU = null;
        Long sU = null;
        if(productUpdates!=-1)
            pU = Long.valueOf(productUpdates);
        if(shopUpdates!=-1)
            sU = Long.valueOf(shopUpdates);
        try {
            updateData(pU, sU);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.getIntent().removeExtra("productUpdate");
        this.getIntent().removeExtra("shopUpdate");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new HomePageTabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupNavBar();
        appContext = getApplicationContext();
     /*   progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Syncing with Database");
        progress.setCancelable(false);
        progress.show();*/
        Log.d("ONTEST", "create");

        try {
            if(currentUser!=null) {
                if (currentUser.getRole().equals(Role.PRODAVAC))
                    loadData(currentUser.getId());
                else loadData(-1L);
            }
            else loadData(-1L);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new HomePageTabsAdapter(getSupportFragmentManager());
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

        search_field = (SearchView) findViewById(R.id.search_field);
        search_field.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Product> searchProducts = new ArrayList<Product>();
                ArrayList<Shop> searchShops = new ArrayList<Shop>();
                for(Product p : products) {
                    if(p.getName().contains(query)) {
                        searchProducts.add(p);
                    }
                }
                for(Shop s : shops) {
                    if(s.getName().contains(query)) {
                        searchShops.add(s);
                    }
                }
                products=searchProducts;
                shops = searchShops;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


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
                                logOut();
                                setupNavBar();
                                try {
                                    loadData(-1L);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
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


    private void logOut() {
        SharedPreferences sharedpreferences = getSharedPreferences(PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            onRestart();
        }
    }


 /*   @Override
    public void onResume(){
        Log.d("ONTEST", "on resume");
        super.onResume();
        progress.show();
        //setupNavBar();

        try {
            loadData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    private void setupNavBar() {
        currentUser = null;
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
            // The URL for making the POST request
            try {
                User user= task.get();
                currentUser = user;
            } catch (InterruptedException e) {
                currentUser = null;
            } catch (ExecutionException e) {
                currentUser = null;
            }
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(currentUser!=null) {
            currentUserFirstName = currentUser.getFirstName();
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_inbox).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name_sidebar)).setText(currentUserFirstName.toUpperCase());




            if(currentUser.getRole().equals(Role.ADMIN)){
                navigationView.getMenu().findItem(R.id.nav_add_category).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_edit_category).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_add_shop).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_cart).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_orders).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_wallet).setVisible(false);

            }
            else if(currentUser.getRole().equals(Role.KUPAC)){
                navigationView.getMenu().findItem(R.id.nav_add_category).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_edit_category).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_add_shop).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_cart).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_orders).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_wallet).setVisible(true);

            }
            else if(currentUser.getRole().equals(Role.PRODAVAC)){
                navigationView.getMenu().findItem(R.id.nav_add_category).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_edit_category).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_add_shop).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_cart).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_orders).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_wallet).setVisible(false);

            }
        }else {
            currentUserFirstName="";
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