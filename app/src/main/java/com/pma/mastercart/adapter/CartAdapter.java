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

public class CartAdapter extends BaseAdapter {

    private final Context mContext;
    private final Product[] products;
    private TextView cart_product_name;
    private TextView cart_product_price;
    private Button buy;
    private ImageButton quantity_down;
    private ImageButton quantity_up;
    private ImageButton cart_remove_product;
    private ImageButton product_details;

    // 1
    public CartAdapter(Context context, Product[] products) {
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
            convertView = layoutInflater.inflate(R.layout.cart_layout, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.cart_product_thumbnail);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.cart_product_name);
        final TextView priceTextView = (TextView)convertView.findViewById(R.id.cart_product_price);

        imageView.setImageResource(product.getImageResource());
        nameTextView.setText(mContext.getString(product.getName()));
        priceTextView.setText(mContext.getString(product.getPrice()) + "$");


        cart_remove_product = (ImageButton)convertView.findViewById(R.id.cart_remove_product);
        cart_remove_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Item removed from cart.", Toast.LENGTH_SHORT).show();
            }
        });

        product_details = (ImageButton)convertView.findViewById(R.id.cart_product_details);
        product_details.setTag(Integer.valueOf(product.getId()));

        product_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open new activity to view this product
                Intent intent = new Intent(mContext, ViewProductActivity.class);
                intent.putExtra("PRODUCT_ID", product.getId()); //int
                intent.putExtra("PRODUCT_NAME", view.getResources().getString(product.getName())); //string
                intent.putExtra("PRODUCT_PRICE", product.getPrice());//int
                intent.putExtra("PRODUCT_PIC", product.getImageResource());//int
                mContext.startActivity(intent);
            }

        });

        return convertView;
    }


}