package com.pma.mastercart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.pma.mastercart.adapter.FavoritesAdapter;
import com.pma.mastercart.adapter.ProductAdapter;
import com.pma.mastercart.model.Product;

public class FavoritesActivity extends AppCompatActivity {

    private Product[] products = {
            new Product(1, R.string.dummy1, R.drawable.ic_phone, R.string.dummyPrice),
            new Product(2, R.string.dummy2, R.drawable.ic_earphones, R.string.dummyPrice),
            new Product(3, R.string.dummy3, R.drawable.ic_microsd, R.string.dummyPrice),
            new Product(4, R.string.dummy3, R.drawable.ic_charger, R.string.dummyPrice),
            new Product(5, R.string.dummy3, R.drawable.ic_charger, R.string.dummyPrice),
            new Product(6, R.string.dummy3, R.drawable.ic_earphones, R.string.dummyPrice),
            new Product(7, R.string.dummy3, R.drawable.ic_phone, R.string.dummyPrice),
            new Product(8, R.string.dummy3, R.drawable.ic_phone, R.string.dummyPrice),
    };
    private FavoritesAdapter favsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_fragment);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        GridView gridView = (GridView)findViewById(R.id.favorites_grid_view);
        favsAdapter = new FavoritesAdapter(this, products);
        gridView.setAdapter(favsAdapter);
    }
}