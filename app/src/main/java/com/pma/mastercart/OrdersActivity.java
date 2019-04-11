package com.pma.mastercart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.ListView;

import com.pma.mastercart.adapter.FavoritesAdapter;
import com.pma.mastercart.adapter.OrdersAdapter;
import com.pma.mastercart.model.Product;

public class OrdersActivity extends AppCompatActivity {

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
    private OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_fragment);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView listView = (ListView) findViewById(R.id.orders_list_view);
        ordersAdapter = new OrdersAdapter(this, products);
        listView.setAdapter(ordersAdapter);
    }
}