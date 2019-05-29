package com.pma.mastercart;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.pma.mastercart.adapter.CartAdapter;
import com.pma.mastercart.adapter.FavoritesAdapter;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.makeOrderTask;
import com.pma.mastercart.model.CartItem;
import com.pma.mastercart.model.DTO.CartItemDTO;
import com.pma.mastercart.model.Order;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CartActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<CartItem> items;/* = { //TODO povuci sa firebase korpu CurrentUser-a
            new Product(1, R.string.dummy1, R.drawable.ic_phone, R.string.dummyPrice),
            new Product(2, R.string.dummy2, R.drawable.ic_earphones, R.string.dummyPrice),
            new Product(3, R.string.dummy3, R.drawable.ic_microsd, R.string.dummyPrice),
            new Product(4, R.string.dummy3, R.drawable.ic_charger, R.string.dummyPrice),
            new Product(5, R.string.dummy3, R.drawable.ic_charger, R.string.dummyPrice),
            new Product(6, R.string.dummy3, R.drawable.ic_earphones, R.string.dummyPrice),
            new Product(7, R.string.dummy3, R.drawable.ic_phone, R.string.dummyPrice),
            new Product(8, R.string.dummy3, R.drawable.ic_phone, R.string.dummyPrice),

    };*/
    private CartAdapter cartAdapter;
    private User user;
    private Button cart_buy_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_fragment);

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
        items = user.getCartItems();
        cart_buy_button = (Button) findViewById(R.id.cart_buy_button);
        cart_buy_button.setOnClickListener(this);

        ListView listView = (ListView) findViewById(R.id.cart_list_view);
        cartAdapter = new CartAdapter(this, items);
        listView.setAdapter(cartAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v==cart_buy_button){
            if(items.size()==0){
                Toast.makeText(this, "There is nothing to buy.", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
            if (sharedpreferences.contains("AuthToken")) {
                boolean success = true;
                for(CartItem item : items){
                    CartItemDTO cartItemDTO = new CartItemDTO(item.getId(), item.getQuantity(), item.getTotal(), item.getItem().getId());
                    Object[] objects = {cartItemDTO, sharedpreferences.getString("AuthToken", null)};
                    AsyncTask<Object, Void, Order> task = new makeOrderTask().execute(objects);
                    // The URL for making the POST request
                    try {
                        Order resp = task.get();
                        if(resp==null){
                            success=false;
                        }
                    } catch (InterruptedException e) {
                    } catch (ExecutionException e) {
                    }
                 }
                if(success){
                    Toast.makeText(this, "Successfully made an order.", Toast.LENGTH_SHORT).show();
                    items.removeAll(items);
                    cartAdapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

             }
        }
    }
}