package com.pma.mastercart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.EditProductActivity;
import com.pma.mastercart.R;
import com.pma.mastercart.ViewProductActivity;
import com.pma.mastercart.model.Product;


public class ProductAdapter extends BaseAdapter {

    private Context mContext;
    private Product[] products;
    private ImageButton product_details;
    private ImageButton add_favorite;
    private ImageButton add_cart;
    private ImageButton edit_product;

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
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Product product = products[position];
        if (convertView == null) {
          final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
          convertView = layoutInflater.inflate(R.layout.product_layout, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.product_thumbnail);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.product_name);
        final TextView priceTextView = (TextView)convertView.findViewById(R.id.product_price);

        ImageView pic = (ImageView) convertView.findViewById(R.id.product_thumbnail); //TODO ucitati sliku
        pic.setImageResource(R.drawable.ic_dummy);

        priceTextView.setText(Double.toString(product.getPrice()) + "$");

        product_details = (ImageButton)convertView.findViewById(R.id.product_details);
        product_details.setTag(product.getId());


        product_details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open new activity to view this product
                Intent intent = new Intent(mContext, ViewProductActivity.class);
                intent.putExtra("PRODUCT_ID", Long.toString(product.getId())); //string
                intent.putExtra("PRODUCT_NAME", product.getName()); //string
                mContext.startActivity(intent);
            }

        });

        edit_product = (ImageButton)convertView.findViewById(R.id.edit_product);
        edit_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //open new activity to view this product
                Intent intent = new Intent(mContext, EditProductActivity.class);
                intent.putExtra("PRODUCT_ID", Long.toString(product.getId())); //int
                intent.putExtra("PRODUCT_NAME", product.getName()); //string
                intent.putExtra("PRODUCT_PRICE", product.getPrice());//int
                intent.putExtra("PRODUCT_PIC", product.getImageResource());//int
                mContext.startActivity(intent);
            }

        });


        add_favorite = (ImageButton)convertView.findViewById(R.id.add_favorite);
        add_favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Item added to favorites.", Toast.LENGTH_SHORT).show();
            }
        });

        add_cart = (ImageButton)convertView.findViewById(R.id.add_cart);
        add_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Item added to cart.", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }


}
