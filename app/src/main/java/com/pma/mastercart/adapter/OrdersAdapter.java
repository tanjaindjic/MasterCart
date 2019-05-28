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
import com.pma.mastercart.model.Order;
import com.pma.mastercart.model.Product;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

public class OrdersAdapter extends BaseAdapter {

    private final Context mContext;
    private final Order[] orders;
    private ImageButton add_cart;

    // 1
    public OrdersAdapter(Context context, Order[] orders) {
        this.mContext = context;
        this.orders = orders;
    }

    // 2
    @Override
    public int getCount() {
        return orders.length;
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
        final Order order = orders[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.orders_layout, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.order_product_thumbnail);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.order_product_name);
        final TextView priceTextView = (TextView)convertView.findViewById(R.id.order_product_price);
        final TextView dateTextView = (TextView)convertView.findViewById(R.id.order_product_date);
        final TextView statusTextView = (TextView)convertView.findViewById(R.id.order_product_status);

        imageView.setImageResource(R.drawable.ic_charger);
        nameTextView.setText(order.getProduct().getName());
        priceTextView.setText("Price: " + Double.toString(order.getPrice()) + "$");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String strDate = sdf.format(order.getTime());
        dateTextView.setText("Date: " + strDate);
        statusTextView.setText("Status: " + StringUtils.capitalize(order.getOrderStatus().name().toLowerCase()));

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