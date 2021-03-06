package com.pma.mastercart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.pma.mastercart.adapter.CartAdapter;
import com.pma.mastercart.adapter.FavoritesAdapter;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.makeOrderTask;
import com.pma.mastercart.asyncTasks.makesOrderTask;
import com.pma.mastercart.model.CartItem;
import com.pma.mastercart.model.DTO.CartItemDTO;
import com.pma.mastercart.model.Order;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.User;

import java.util.ArrayList;
import java.util.List;
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
    private ImageView image_empty_cart;
    private Button cart_start_shoping;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_fragment);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        image_empty_cart = (ImageView) findViewById(R.id.image_empty_cart);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        user = null;
        cart_buy_button = (Button) findViewById(R.id.cart_buy_button);
        cart_buy_button.setOnClickListener(this);
        cart_start_shoping = (Button) findViewById(R.id.cart_start_shoping);
        cart_start_shoping.setOnClickListener(this);
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
            // The URL for making the POST request
            try {
                user = task.get();
                if (user.getCartItems().size() == 0) {
                    image_empty_cart.setVisibility(View.VISIBLE);
                    cart_buy_button.setVisibility(View.INVISIBLE);
                    cart_start_shoping.setVisibility(View.VISIBLE);
                } else{
                    image_empty_cart.setVisibility(View.INVISIBLE);
                    cart_buy_button.setVisibility(View.VISIBLE);
                    cart_start_shoping.setVisibility(View.INVISIBLE);
                }
            } catch (InterruptedException e) {
                finish();
            } catch (ExecutionException e) {
                finish();
            }
        } else {
            finish();
        }
        items = user.getCartItems();


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
                List<Object> objekti = new ArrayList<Object>();
                for(CartItem item : items) {
                    CartItemDTO cartItemDTO = new CartItemDTO(item.getId(), item.getQuantity(), item.getTotal(), item.getItem().getId());
                   // Object[] objects = {cartItemDTO, sharedpreferences.getString("AuthToken", null)};
                    objekti.add(cartItemDTO);

                    // The URL for making the POST request
                }
                List<Object> authToken = new ArrayList<Object>();
                authToken.add(sharedpreferences.getString("AuthToken", null));
                AsyncTask<List<Object>, Void, Order> task = new makesOrderTask().execute(objekti,authToken);
                    try {
                        Order resp = task.get();
                        if(resp==null){
                            success=false;
                        }
                    } catch (InterruptedException e) {
                    } catch (ExecutionException e) {
                    }

                if(success){
                    Toast.makeText(this, "Successfully made an order.", Toast.LENGTH_SHORT).show();
                    items.removeAll(items);
                    cartAdapter.notifyDataSetChanged();
                    image_empty_cart.setVisibility(View.VISIBLE);
                    cart_buy_button.setVisibility(View.INVISIBLE);
                    cart_start_shoping.setVisibility(View.VISIBLE);
                }
                else
                    Toast.makeText(this, "There is not enough money to proceed order.", Toast.LENGTH_SHORT).show();

             } else
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        if(v==cart_start_shoping){
            finish();
        }
    }
}