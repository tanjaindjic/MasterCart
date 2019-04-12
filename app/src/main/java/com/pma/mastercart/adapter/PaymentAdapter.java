package com.pma.mastercart.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pma.mastercart.R;
import com.pma.mastercart.model.Payment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends ArrayAdapter<Payment> {

    private Context mContext;
    private List<Payment> moviesList = new ArrayList<>();

    public PaymentAdapter(@NonNull Context context, ArrayList<Payment> list) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.list_view_wallet, null);
        }

        Payment currentMovie = moviesList.get(position);


        TextView amount = (TextView) convertView.findViewById(R.id.amount);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        // Populate the data into the template view using the data object

        amount.setText(String.valueOf(currentMovie.getAmount()));

        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        date.setText(df.format(currentMovie.getDate()));
        // Return the completed view to render on screen
        return convertView;
    }
}
