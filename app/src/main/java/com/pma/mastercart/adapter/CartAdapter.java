package com.pma.mastercart.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.R;
import com.pma.mastercart.ViewProductActivity;
import com.pma.mastercart.asyncTasks.RemoveFromCartTask;
import com.pma.mastercart.asyncTasks.UpdateItemCartTask;
import com.pma.mastercart.asyncTasks.makeOrderTask;
import com.pma.mastercart.model.CartItem;
import com.pma.mastercart.model.DTO.CartItemDTO;
import com.pma.mastercart.model.Order;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CartAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<CartItem> items;
    private TextView cart_product_name;
    private TextView cart_product_price;
    private TextView cart_product_quantity;
    private TextView total_price;
    private Button buy;
    private ImageButton quantity_down;
    private ImageButton quantity_up;
    private ImageButton cart_remove_product;
    private ImageButton product_details;
    private Button btn_buy_now;

    // 1
    public CartAdapter(Context context, ArrayList<CartItem> items) {
        this.mContext = context;
        this.items = items;
    }

    // 2
    @Override
    public int getCount() {
        return items.size();
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
        final CartItem item = items.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.cart_layout, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.cart_product_thumbnail);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.cart_product_name);
        final TextView priceTextView = (TextView)convertView.findViewById(R.id.cart_product_price);
        final TextView cart_product_quantity = (TextView)convertView.findViewById(R.id.cart_product_quantity);
        final TextView total_price = (TextView)convertView.findViewById(R.id.total_price);


        //imageView.setImageResource(product.getImageResource()); TODO ucitati sliku, ImageResource je path do slike
        nameTextView.setText(item.getItem().getName());
        priceTextView.setText(Double.toString(item.getItem().getPrice()) + "$");
        cart_product_quantity.setText(Integer.toString(item.getQuantity()));
        total_price.setText(Double.toString(item.getTotal()));

        cart_remove_product = (ImageButton)convertView.findViewById(R.id.cart_remove_product);
        cart_remove_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {
                    Object[] objects = {items.get(position), sharedpreferences.getString("AuthToken", null)};
                    AsyncTask<Object, Void, String> task = new RemoveFromCartTask().execute(objects);
                    // The URL for making the POST request
                    try {
                        String resp = task.get();
                        if(resp.equals("done")){
                            items.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(mContext, "Product deleted from cart.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                    } catch (ExecutionException e) {
                    }
                }
                else
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        product_details = (ImageButton)convertView.findViewById(R.id.cart_product_details);


        product_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open new activity to view this product
                Intent intent = new Intent(mContext, ViewProductActivity.class);
                ArrayList parcelableList = new ArrayList();
                parcelableList.add(items.get(position).getItem());
                intent.putParcelableArrayListExtra("product", parcelableList);
                mContext.startActivity(intent);
            }

        });

        quantity_down = (ImageButton)convertView.findViewById(R.id.quantity_down);
        quantity_down.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {
                    if(items.get(position).getQuantity()==1){
                        return;
                    }
                    CartItem item = items.get(position);
                    item.setQuantity(item.getQuantity()-1);
                    item.setTotal(item.getQuantity()*item.getItem().getPrice());
                    CartItemDTO cartItemDTO = new CartItemDTO(item.getId(), item.getQuantity(), item.getTotal(), item.getItem().getId());
                    Object[] objects = {cartItemDTO, sharedpreferences.getString("AuthToken", null)};
                    AsyncTask<Object, Void, String> task = new UpdateItemCartTask().execute(objects);
                    // The URL for making the POST request
                    try {
                        String resp = task.get();
                        if(resp.equals("done")){
                            notifyDataSetChanged();
                        }
                    } catch (InterruptedException e) {
                    } catch (ExecutionException e) {
                    }
                }
                else
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        });

        quantity_up = (ImageButton)convertView.findViewById(R.id.quantity_up);
        quantity_up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {
                    CartItem item = items.get(position);
                    item.setQuantity(item.getQuantity()+1);
                    item.setTotal(item.getQuantity()*item.getItem().getPrice());
                    CartItemDTO cartItemDTO = new CartItemDTO(item.getId(), item.getQuantity(), item.getTotal(), item.getItem().getId());
                    Object[] objects = {cartItemDTO, sharedpreferences.getString("AuthToken", null)};
                    AsyncTask<Object, Void, String> task = new UpdateItemCartTask().execute(objects);
                    // The URL for making the POST request
                    try {
                        String resp = task.get();
                        if(resp.equals("done")){
                            notifyDataSetChanged();
                        }
                    } catch (InterruptedException e) {
                    } catch (ExecutionException e) {
                    }
                }
                else
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        });

        btn_buy_now = (Button)convertView.findViewById(R.id.btn_buy_now);
        btn_buy_now.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = mContext.getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {
                    CartItem item = items.get(position);
                    CartItemDTO cartItemDTO = new CartItemDTO(item.getId(), item.getQuantity(), item.getTotal(), item.getItem().getId());
                    Object[] objects = {cartItemDTO, sharedpreferences.getString("AuthToken", null)};
                    AsyncTask<Object, Void, Order> task = new makeOrderTask().execute(objects);
                    // The URL for making the POST request
                    try {
                        Order resp = task.get();
                        if(resp!=null){
                            items.remove(position);
                            Toast.makeText(mContext, "Successfully made an order.", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
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