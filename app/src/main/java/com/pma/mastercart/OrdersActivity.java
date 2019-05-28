package com.pma.mastercart;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.pma.mastercart.adapter.OrdersAdapter;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.model.Order;
import com.pma.mastercart.model.User;

import java.util.concurrent.ExecutionException;

public class OrdersActivity extends AppCompatActivity {

    private Order[] orders;
    private OrdersAdapter ordersAdapter;
    private User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_fragment);

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

        Order[] orders = user.getOrders().toArray(new Order[user.getOrders().size()]);
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView listView = (ListView) findViewById(R.id.orders_list_view);
        ordersAdapter = new OrdersAdapter(this, orders);
        listView.setAdapter(ordersAdapter);
    }

}