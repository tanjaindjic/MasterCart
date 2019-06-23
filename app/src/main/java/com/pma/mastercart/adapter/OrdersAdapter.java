package com.pma.mastercart.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OrdersActivity;
import com.pma.mastercart.R;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.SendReviewTask;
import com.pma.mastercart.model.Comment;
import com.pma.mastercart.model.DTO.ReviewDTO;
import com.pma.mastercart.model.Order;
import com.pma.mastercart.model.User;
import com.pma.mastercart.model.enums.OrderStatus;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class OrdersAdapter extends BaseAdapter {

    private final Context mContext;
    private final Order[] orders;
    private ImageButton add_cart;
    public static ImageButton rate;
    private String comment;
    private float shopRating;
    private float productRating;
    private RatingBar productRatingBar;
    private RatingBar shopRatingBar;
    private EditText editText;


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
                //TODO dodati u korpu
                Toast.makeText(mContext, "Item added to cart.", Toast.LENGTH_SHORT).show();
            }
        });


        rate = (ImageButton)convertView.findViewById(R.id.rate_order);
        if(order.getOrderStatus().equals(OrderStatus.DELIVERED))
            rate.setVisibility(View.VISIBLE);
        else rate.setVisibility(View.GONE);
        rate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.review_layout, null);
                builder.setView(dialogView);
                builder.setCancelable(true);
                productRatingBar = (RatingBar)dialogView.findViewById(R.id.productRatingBar);
                shopRatingBar = (RatingBar)dialogView.findViewById(R.id.shopRatingBar);
                editText = (EditText) dialogView.findViewById(R.id.editText);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comment = editText.getText().toString();
                        productRating = productRatingBar.getRating();
                        shopRating = shopRatingBar.getRating();
                        sendReview(v, order, comment, productRating, shopRating);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });



        return convertView;
    }

    private void sendReview(View v, Order order, String comment, float productRating, float shopRating) {
        User currentUser = null;
        SharedPreferences sharedpreferences = v.getContext().getSharedPreferences(MainActivity.PREFS, 0);
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
        ReviewDTO c = new ReviewDTO(order.getProduct().getShop().getId(), order.getProduct().getId(), currentUser.getFirstName(), comment, new Date(System.currentTimeMillis()), shopRating, productRating, order.getId());
        SendReviewTask task = new SendReviewTask();
        task.execute(c);



    }

}