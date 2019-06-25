package com.pma.mastercart.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.EditProductActivity;
import com.pma.mastercart.MainActivity;
import com.pma.mastercart.R;
import com.pma.mastercart.ViewProductActivity;
import com.pma.mastercart.asyncTasks.AddToCartTask;
import com.pma.mastercart.asyncTasks.AddToFavsTask;
import com.pma.mastercart.asyncTasks.GetFavoriteIdsTask;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;
import com.pma.mastercart.model.enums.Role;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ProductAdapter extends BaseAdapter {

    private Context mContext;
    private Product[] products;
    private ImageButton product_details;
    private ImageButton add_favorite;
    private ImageButton add_cart;
    private ImageButton edit_product;
    private ImageView imageView;
    private TextView nameTextView;
    private TextView priceTextView;
    private Product product;
    private RatingBar ratingBar;
    private Shop shop;

    // 1
    public ProductAdapter(Context context, Product[] products) {
        this.mContext = context;
        this.products = products;
    }


    // 2
    @Override
    public int getCount() {
        return products.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return products[position];
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        product = products[position];
        if (convertView == null) {
          final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
          convertView = layoutInflater.inflate(R.layout.product_layout, null);
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImageResource(), 0, product.getImageResource().length);
        imageView = (ImageView)convertView.findViewById(R.id.product_thumbnail);
        imageView.setImageBitmap(bitmap);
        nameTextView = (TextView)convertView.findViewById(R.id.product_name);
        nameTextView.setText(product.getName());
        priceTextView = (TextView)convertView.findViewById(R.id.product_price);
        priceTextView.setText(Double.toString(product.getPrice())+'$');
        ratingBar = (RatingBar) convertView.findViewById(R.id.product_rating);
        ratingBar.setRating((float) product.getRating());

        product_details = (ImageButton)convertView.findViewById(R.id.product_details);
        product_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open new activity to view this product
                Intent intent = new Intent(mContext, ViewProductActivity.class);
                ArrayList parcelableList = new ArrayList();
                parcelableList.add(products[position]);
                intent.putParcelableArrayListExtra("product", parcelableList);
                mContext.startActivity(intent);
            }

        });


        edit_product = (ImageButton)convertView.findViewById(R.id.edit_product);
        edit_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open new activity to view this product
                Intent intent = new Intent(mContext, EditProductActivity.class);
                intent.putExtra("PRODUCT_ID", products[position].getId()); //long
                intent.putExtra("editProductEditing", products[position]);
                mContext.startActivity(intent);
            }

        });


        add_favorite = (ImageButton)convertView.findViewById(R.id.add_favorite);
        add_favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {
                    Object[] objects = {products[position], sharedpreferences.getString("AuthToken", null)};
                    AsyncTask<Object, Void, String> task = new AddToFavsTask().execute(objects);
                    // The URL for making the POST request
                    try {
                        String resp = task.get();
                        if(resp.equals("done")){
                            ((ImageButton)view.findViewById(R.id.add_favorite)).setImageResource(R.drawable.ic_favorite);
                            Toast.makeText(mContext, "Item added to favorites.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                    } catch (ExecutionException e) {
                    }
                }
                else
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
        //postavljanje favorite ikonice
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            Object[] objects = {sharedpreferences.getString("AuthToken", null), products[position].getId()};
            AsyncTask<Object, Void,  Boolean> task = new GetFavoriteIdsTask().execute(objects);
            try {
                Boolean longs= task.get();
                if(longs!= null) {
                    if (longs)
                        add_favorite.setImageResource(R.drawable.ic_favorite);
                    else
                        add_favorite.setImageResource(R.drawable.ic_pick_favorite);
                }
            } catch (InterruptedException e) {

            } catch (ExecutionException e) {

            }
        }


        add_cart = (ImageButton)convertView.findViewById(R.id.add_cart);
        add_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {
                    Object[] objects = {products[position], sharedpreferences.getString("AuthToken", null)};
                    AsyncTask<Object, Void, String> task = new AddToCartTask().execute(objects);
                    // The URL for making the POST request
                    try {
                        String resp = task.get();
                        if(resp.equals("done")){
                            Toast.makeText(mContext, "Item added to cart.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                    } catch (ExecutionException e) {
                    }
                }
                else
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        User currentUser = null;
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
        if(currentUser==null){
            edit_product.setVisibility(View.GONE);
            add_cart.setVisibility(View.GONE);
            add_favorite.setVisibility(View.GONE);
        }
        else if(currentUser.getRole().equals(Role.PRODAVAC)){
            //TODO postaviti uslov za prodavca samo radnje z koje je proizvod
                edit_product.setVisibility(View.VISIBLE);

            add_cart.setVisibility(View.GONE);
            add_favorite.setVisibility(View.GONE);
        }else if(currentUser.getRole().equals(Role.KUPAC)){
            edit_product.setVisibility(View.GONE);
            add_favorite.setVisibility(View.VISIBLE);
            add_cart.setVisibility(View.VISIBLE);
        }else{
            edit_product.setVisibility(View.GONE);
            add_cart.setVisibility(View.GONE);
            add_favorite.setVisibility(View.GONE);
        }
        return convertView;
    }



}
