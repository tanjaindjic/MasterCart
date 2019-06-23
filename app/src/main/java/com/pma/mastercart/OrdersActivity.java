package com.pma.mastercart;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.pma.mastercart.adapter.OnLoadDataListener;
import com.pma.mastercart.adapter.OrdersAdapter;
import com.pma.mastercart.asyncTasks.GetOrdersTask;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.model.Order;
import com.pma.mastercart.model.User;

import java.util.concurrent.ExecutionException;

public class OrdersActivity extends AppCompatActivity implements OnLoadDataListener {

    public static Order[] orders;
    public static OrdersAdapter ordersAdapter;
    private User user;
    public static AlertDialog alertDialog;
    public static ListView listView;


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
        alertDialog = new AlertDialog.Builder(OrdersActivity.this).create();

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        GetOrdersTask t = new GetOrdersTask(OrdersActivity.this);
                        t.execute(user.getId());
                    }
                });

        GetOrdersTask t = new GetOrdersTask(OrdersActivity.this);
        t.execute(user.getId());

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.orders_list_view);
  /*      ordersAdapter = new OrdersAdapter(this, orders);
        listView.setAdapter(ordersAdapter);*/
    }

    @Override
    public void onLoad(Object data) {
        orders = (Order[]) data;
        ordersAdapter = new OrdersAdapter(OrdersActivity.this, orders);
        if(listView!=null)
            listView.setAdapter(ordersAdapter);
    }
}