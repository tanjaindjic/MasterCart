package com.pma.mastercart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.pma.mastercart.adapter.FavoritesAdapter;
import com.pma.mastercart.adapter.ProductAdapter;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;
import com.pma.mastercart.model.enums.Role;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FavoritesActivity extends AppCompatActivity {

    private Product[] products;
    private FavoritesAdapter favsAdapter;
    private User user;
    private SearchView favorites_search_field;
    private Context ctx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_fragment);

        ctx = this;

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        user=null;
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
            // The URL for making the POST request
            try {
                user= task.get();
            } catch (InterruptedException e) {
                finish();
            } catch (ExecutionException e) {
                finish();
            }
        }
        else{
            finish();
        }
        final ArrayList<Product>[] products = new ArrayList[]{user.getFavorites()};
        GridView gridView = (GridView)findViewById(R.id.favorites_grid_view);
        favsAdapter = new FavoritesAdapter(this, products[0]);
        gridView.setAdapter(favsAdapter);

        favorites_search_field = (SearchView) findViewById(R.id.favorites_search_field);
        favorites_search_field.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Product> searchFavorites = new ArrayList<Product>();
                for(int i=0; i<products[0].size(); i++) {
                    if(products[0].get(i).getName().toLowerCase().contains(query.toLowerCase())) {
                        searchFavorites.add(products[0].get(i));
                    }
                }
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                products[0] = searchFavorites;
                GridView gridView = (GridView)findViewById(R.id.favorites_grid_view);
                favsAdapter = new FavoritesAdapter(ctx, products[0]);
                gridView.setAdapter(favsAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        favorites_search_field.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {
                    AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
                    // The URL for making the POST request
                    try {
                        user= task.get();
                    } catch (InterruptedException e) {
                        finish();
                    } catch (ExecutionException e) {
                        finish();
                    }
                }
                else{
                    finish();
                }
                final ArrayList<Product>[] products = new ArrayList[]{user.getFavorites()};
                GridView gridView = (GridView)findViewById(R.id.favorites_grid_view);
                favsAdapter = new FavoritesAdapter(ctx, products[0]);
                gridView.setAdapter(favsAdapter);
                return true;
            }
        });
    }
}