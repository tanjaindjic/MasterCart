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

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.R;
import com.pma.mastercart.ViewProductActivity;
import com.pma.mastercart.asyncTasks.AddToCartTask;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.RemoveFromFavs;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoritesAdapter  extends BaseAdapter {

    private final Context mContext;
    private final List<Product> products;
    private ImageButton product_details;
    private ImageButton delete_favorite;
    private ImageButton add_cart;
    private RatingBar ratingBar;

    // 1
    public FavoritesAdapter(Context context, List<Product> products) {
        this.mContext = context;
        this.products = products;
    }

    // 2
    @Override
    public int getCount() {
        return products.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Product product = products.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.favorites_layout, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.favorite_product_thumbnail);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.favorite_product_name);
        final TextView priceTextView = (TextView)convertView.findViewById(R.id.favorite_product_price);
        ratingBar = (RatingBar) convertView.findViewById(R.id.favorite_product_rating);
        double rating = products.get(position).getRating();
        ratingBar.setRating((float) rating);

        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImageResource(), 0, product.getImageResource().length);
        imageView.setImageBitmap(bitmap);
        nameTextView.setText(product.getName());
        priceTextView.setText(Double.toString(product.getPrice()) + "$");

        product_details = (ImageButton)convertView.findViewById(R.id.favorite_product_details);


        product_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open new activity to view this product
                Intent intent = new Intent(mContext, ViewProductActivity.class);
                ArrayList parcelableList = new ArrayList();
                parcelableList.add(products.get(position));
                intent.putParcelableArrayListExtra("product", parcelableList);
                mContext.startActivity(intent);

            }

        });


        delete_favorite = (ImageButton)convertView.findViewById(R.id.delete_favorite);
        delete_favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {
                    Object[] objects = {products.get(position), sharedpreferences.getString("AuthToken", null)};
                    AsyncTask<Object, Void, String> task = new RemoveFromFavs().execute(objects);
                    // The URL for making the POST request
                    try {
                        String resp = task.get();
                        if(resp.equals("done")){
                            products.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(mContext, "Item deleted from favorites.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                    } catch (ExecutionException e) {
                    }
                }
                else
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

        add_cart = (ImageButton)convertView.findViewById(R.id.favorite_add_cart);
        add_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {
                    Object[] objects = {products.get(position), sharedpreferences.getString("AuthToken", null)};
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

        return convertView;
    }


}