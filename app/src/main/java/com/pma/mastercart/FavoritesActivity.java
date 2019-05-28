package com.pma.mastercart;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.pma.mastercart.adapter.FavoritesAdapter;
import com.pma.mastercart.adapter.ProductAdapter;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FavoritesActivity extends AppCompatActivity {

    private Product[] products;/* = {//TODO povuci sa firebase proizvode koje prati CurrentUser
            new Product(1, R.string.dummy1, R.drawable.ic_phone, R.string.dummyPrice),
            new Product(2, R.string.dummy2, R.drawable.ic_earphones, R.string.dummyPrice),
            new Product(3, R.string.dummy3, R.drawable.ic_microsd, R.string.dummyPrice),
            new Product(4, R.string.dummy3, R.drawable.ic_charger, R.string.dummyPrice),
            new Product(5, R.string.dummy3, R.drawable.ic_charger, R.string.dummyPrice),
            new Product(6, R.string.dummy3, R.drawable.ic_earphones, R.string.dummyPrice),
            new Product(7, R.string.dummy3, R.drawable.ic_phone, R.string.dummyPrice),
            new Product(8, R.string.dummy3, R.drawable.ic_phone, R.string.dummyPrice),
    };*/
    private FavoritesAdapter favsAdapter;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_fragment);

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
        ArrayList<Product> products = user.getFavorites();
        GridView gridView = (GridView)findViewById(R.id.favorites_grid_view);
        favsAdapter = new FavoritesAdapter(this, products);
        gridView.setAdapter(favsAdapter);
    }
}