package com.pma.mastercart;
/////Set your content view before you call findviewbyId methods.//////
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pma.mastercart.adapter.HomePageTabsAdapter;
import com.pma.mastercart.adapter.ProductAdapter;
import com.pma.mastercart.adapter.ShopAdapter;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.RetrieveProductListTask;
import com.pma.mastercart.asyncTasks.RetrieveProductsTask;
import com.pma.mastercart.asyncTasks.RetrieveShopListTask;
import com.pma.mastercart.asyncTasks.RetrieveShopsTask;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.ShopListDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;
import com.pma.mastercart.model.enums.Role;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    public static ViewPager viewPager;
    public static String URL = "http://192.168.1.8:8096/";
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
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private Context ctx;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    private void loadData(Long i) throws ExecutionException, InterruptedException {
        AsyncTask<Long, Void, ArrayList<Product>> task = new RetrieveProductsTask().execute(i);
        products = task.get();
        AsyncTask<Long, Void, ArrayList<Shop>> task2 = new RetrieveShopsTask().execute(i);
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
   /*     long productUpdates = this.getIntent().getLongExtra("productUpdate", -1);
        long shopUpdates = this.getIntent().getLongExtra("shopUpdate", -1);
        Long pU = null;
        Long sU = null;
        if(productUpdates!=-1)
            pU = Long.valueOf(productUpdates);
        if(shopUpdates!=-1)
            sU = Long.valueOf(shopUpdates);*/
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
        this.getIntent().removeExtra("productUpdate");
        this.getIntent().removeExtra("shopUpdate");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
      //  adapter = new HomePageTabsAdapter(getSupportFragmentManager());
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    /*    tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkLocationPermission();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
            }
        }
        else {
            buildGoogleApiClient();
        }
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
        if(currentUser!=null)
            if(currentUser.getRole().equals(Role.KUPAC))
                cart_toolbar_button.setVisibility(View.VISIBLE);
            else cart_toolbar_button.setVisibility(View.GONE);
        else cart_toolbar_button.setVisibility(View.GONE);

        cart_toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser==null){
                    Toast.makeText(ctx, "You need to login first.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(i);
            }
        });

        ImageButton inbox_toolbar_button = (ImageButton) findViewById(R.id.inbox_toolbar_button);
        inbox_toolbar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser==null){
                    Toast.makeText(ctx, "You need to login first.", Toast.LENGTH_SHORT).show();
                    return;
                }

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
                    if(p.getName().toLowerCase().contains(query.toLowerCase())) {
                        searchProducts.add(p);
                    }
                }
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                products = searchProducts;
                LinearLayout v = (LinearLayout) viewPager.getChildAt(0);
                GridView gw = (GridView) v.getChildAt(1);
                ProductAdapter productsAdapter = new ProductAdapter(MainActivity.appContext, products.toArray(new Product[products.size()]));
                gw.setAdapter(productsAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }


        });
        search_field.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toolbar.collapseActionView();
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                try {
                    if(currentUser!=null) {
                        if (currentUser.getRole().equals(Role.PRODAVAC))
                            loadData(currentUser.getId());
                        else loadData(-1L);
                    }
                    else loadData(-1L);
                    LinearLayout v = (LinearLayout) viewPager.getChildAt(0);
                    GridView gw = (GridView) v.getChildAt(1);
                    ProductAdapter productsAdapter = new ProductAdapter(MainActivity.appContext, products.toArray(new Product[products.size()]));
                    gw.setAdapter(productsAdapter);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
                            case R.id.nav_add_wallet:
                                i = new Intent(getApplicationContext(),AddWalletActivity.class);
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



    }


    private void logOut() {
        SharedPreferences sharedpreferences = getSharedPreferences(PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove("AuthToken");
            editor.clear();
            editor.commit();
            onRestart();
        }
    }




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
            if(currentUser.getImageResource().length>0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(currentUser.getImageResource(), 0, currentUser.getImageResource().length);
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_pic_sidebar)).setImageBitmap(bitmap);
            }else{
                ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_pic_sidebar)).setImageResource(R.mipmap.ic_launcher);
            }



            if(currentUser.getRole().equals(Role.ADMIN)){
                navigationView.getMenu().findItem(R.id.nav_add_category).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_edit_category).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_add_shop).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_cart).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_orders).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_wallet).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_add_wallet).setVisible(true);
            }
            else if(currentUser.getRole().equals(Role.KUPAC)){
                navigationView.getMenu().findItem(R.id.nav_add_category).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_edit_category).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_add_shop).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_cart).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_orders).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_wallet).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_add_wallet).setVisible(false);

            }
            else if(currentUser.getRole().equals(Role.PRODAVAC)){
                navigationView.getMenu().findItem(R.id.nav_add_category).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_edit_category).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_add_shop).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_cart).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_orders).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_wallet).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_add_wallet).setVisible(false);

            }
        }else {
            currentUserFirstName="";
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name_sidebar)).setText(currentUserFirstName);
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
            navigationView.getMenu().findItem(R.id.nav_add_wallet).setVisible(false);
            ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_pic_sidebar)).setImageResource(R.mipmap.ic_launcher);


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
            case R.id.mail:
                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Contact");
                alertDialog.setMessage("If you want to contact us, write us an email on admin@gmail.com, or call us on 021-444-555");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(100000);
        mLocationRequest.setFastestInterval(100000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("lat", String.valueOf(location.getLatitude()));
        editor.putString("lon", String.valueOf(location.getLongitude()));
        editor.apply();
        editor.commit();

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
}