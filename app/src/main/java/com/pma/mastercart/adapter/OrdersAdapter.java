package com.pma.mastercart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.R;
import com.pma.mastercart.ViewProductActivity;
import com.pma.mastercart.model.Product;

public class OrdersAdapter extends BaseAdapter {

    private final Context mContext;
    private final Product[] products;
    private ImageButton add_cart;

    // 1
    public OrdersAdapter(Context context, Product[] products) {
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
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Product product = products[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.orders_layout, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.order_product_thumbnail);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.order_product_name);
        final TextView priceTextView = (TextView)convertView.findViewById(R.id.order_product_price);

        imageView.setImageResource(product.getImageResource());
        nameTextView.setText(mContext.getString(product.getName()));
        priceTextView.setText(mContext.getString(product.getPrice()) + "$");

        add_cart = (ImageButton)convertView.findViewById(R.id.order_product_again);
        add_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Item added to cart.", Toast.LENGTH_SHORT).show();
            }
        });



        return convertView;
    }


}