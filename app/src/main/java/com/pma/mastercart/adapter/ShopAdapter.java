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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.AddProductActivity;
import com.pma.mastercart.EditShopActivity;
import com.pma.mastercart.MainActivity;
import com.pma.mastercart.MapsActivity;
import com.pma.mastercart.R;
import com.pma.mastercart.ViewShopActivity;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;
import com.pma.mastercart.model.enums.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ShopAdapter  extends BaseAdapter {

    private final Context mContext;
    private Shop[] shops;
    private ImageButton shop_details;
    private ImageButton shop_location;
    private ImageButton add_product;
    private ImageButton edit_shop;
    private Shop shop;
    private TextView nameTextView;
    private TextView locationTextView;
    private ImageView pic;
    private RatingBar ratingBar;


    public ShopAdapter(Context mContext, Shop[] shops) {
        this.mContext = mContext;
        this.shops = shops;

    }

    @Override
    public int getCount() {
        return shops.length;
    }

    @Override
    public Object getItem(int position) {
        return shops[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        shop = shops[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.shop_layout, null);
        }

        nameTextView = (TextView)convertView.findViewById(R.id.shop_name);
        nameTextView.setText(shop.getName());
        locationTextView = (TextView)convertView.findViewById(R.id.shop_address);
        locationTextView.setText(shop.getLocation());
        pic = (ImageView)convertView.findViewById(R.id.shop_thumbnail);
        if(shop.getImageResource()==null){
            pic.setImageResource(R.drawable.ic_dummy);
        }else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(shop.getImageResource(), 0, shop.getImageResource().length);
            pic.setImageBitmap(bitmap);
        }
        ratingBar = (RatingBar) convertView.findViewById(R.id.shop_rating);
        ratingBar.setRating((float) shop.getRating());

        shop_details = (ImageButton)convertView.findViewById(R.id.shop_details);
        shop_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open new activity to view this product

                ArrayList parcelableList = new ArrayList();
                parcelableList.add(shops[position]);
                Intent intent = new Intent(mContext, ViewShopActivity.class);
                intent.putParcelableArrayListExtra("shop", parcelableList);
                mContext.startActivity(intent);
            }

        });

        shop_location = (ImageButton) convertView.findViewById(R.id.shop_location);
        shop_location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //open new activity to view location
                Intent intent = new Intent(mContext, MapsActivity.class);
                intent.putExtra("lat", shops[position].getLat());
                intent.putExtra("lon", shops[position].getLng());
                mContext.startActivity(intent);
            }

        });

        add_product = (ImageButton)convertView.findViewById(R.id.add_product);
        add_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open new activity to view this product
                Intent intent = new Intent(mContext, AddProductActivity.class);
                intent.putExtra("idShop", shops[position].getId());
                mContext.startActivity(intent);
            }

        });

        edit_shop = (ImageButton)convertView.findViewById(R.id.edit_shop);
        edit_shop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open new activity to view this product
                Intent intent = new Intent(mContext, EditShopActivity.class);
                intent.putExtra("idShop", shops[position].getId());
                intent.putExtra("editShopEditing", shops[position]);
                mContext.startActivity(intent);
            }

        });



        User currentUser = null;
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.PREFS, 0);
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
            edit_shop.setVisibility(View.GONE);
            add_product.setVisibility(View.GONE);
        }
        else if(currentUser.getRole().equals(Role.PRODAVAC)){
            edit_shop.setVisibility(View.GONE);
            add_product.setVisibility(View.GONE);
            if(shop.getSeller().getId()==currentUser.getId()){
                edit_shop.setVisibility(View.VISIBLE);
                add_product.setVisibility(View.VISIBLE);
            }
        }else{
            edit_shop.setVisibility(View.GONE);
            add_product.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        ArrayList<Shop> shopsMain = MainActivity.shops;
        shops = shopsMain.toArray(new Shop[shopsMain.size()]);

    }

}
