package com.pma.mastercart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pma.mastercart.R;
import com.pma.mastercart.model.Shop;

public class ShopAdapter  extends BaseAdapter {

    private final Context mContext;
    private final Shop[] shops;

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
        final Shop shop = shops[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.shop_linearlayout, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.shop_thumbnail);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.shop_name);
        final TextView locationTextView = (TextView)convertView.findViewById(R.id.shop_location);

        imageView.setImageResource(shop.getImageResource());
        nameTextView.setText(mContext.getString(shop.getName()));
        locationTextView.setText(mContext.getString(shop.getLocation()));

        return convertView;
    }
}
