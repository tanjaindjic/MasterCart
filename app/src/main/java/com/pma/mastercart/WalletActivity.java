package com.pma.mastercart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.pma.mastercart.adapter.PaymentAdapter;
import com.pma.mastercart.model.Payment;
import com.pma.mastercart.model.Wallet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WalletActivity extends AppCompatActivity {

    private PaymentAdapter paymentAdapter;
    private Payment[] payments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<Payment> payments = new ArrayList<Payment>();
        Wallet wallet1 = new Wallet(); //TODO naci wallet od CurrentUser-a
        paymentAdapter = new PaymentAdapter(this, wallet1.getHistory());
        TextView tvName = (TextView) this.findViewById(R.id.wallet_value);
        tvName.setText(String.valueOf(wallet1.getBalance()) + "$");
        ListView listView = (ListView) findViewById(R.id.payments_list_view1);
        listView.setAdapter(paymentAdapter);
    }
}
