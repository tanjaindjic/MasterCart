package com.pma.mastercart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pma.mastercart.model.Product;


public class ProductAdapter extends BaseAdapter {

    private final Context mContext;
    private final Product[] products;

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
          convertView = layoutInflater.inflate(R.layout.product_linearlayout, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.product_thumbnail);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.product_name);
        final TextView priceTextView = (TextView)convertView.findViewById(R.id.product_price);

        imageView.setImageResource(product.getImageResource());
        nameTextView.setText(mContext.getString(product.getName()));
        priceTextView.setText(mContext.getString(product.getPrice()) + "$");

        return convertView;
    }


}
