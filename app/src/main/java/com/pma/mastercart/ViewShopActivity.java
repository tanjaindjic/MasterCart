package com.pma.mastercart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_shop_view);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        back_toolbar.setTitle(intent.getStringExtra("SHOP_NAME"));
        TextView name = (TextView) findViewById(R.id.single_shop_name);
        name.setText(intent.getStringExtra("SHOP_NAME"));
        TextView address = (TextView) findViewById(R.id.single_shop_address);
        address.setText(intent.getStringExtra("SHOP_ADDRESS"));
        ImageView pic = (ImageView) findViewById(R.id.single_shop_thumbnail);
        pic.setImageResource(R.drawable.ic_shop);

    }

}
