package com.pma.mastercart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewProductActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_view);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        back_toolbar.setTitle(intent.getStringExtra("PRODUCT_NAME"));
        TextView name = (TextView) findViewById(R.id.single_product_name);
        name.setText(intent.getStringExtra("PRODUCT_NAME"));
        TextView price = (TextView) findViewById(R.id.single_product_price);
        price.setText(intent.getStringExtra("PRODUCT_PRICE") + "$");
        ImageView pic = (ImageView) findViewById(R.id.single_product_thumbnail);
        pic.setImageResource(Integer.valueOf(intent.getStringExtra("PRODUCT_PIC")));

        Button buy = (Button) findViewById(R.id.buy_button);




    }
}
