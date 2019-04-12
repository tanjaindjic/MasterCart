package com.pma.mastercart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        Payment payment1 = new Payment(1,10, new Date());
        Payment payment2 = new Payment(2,20, new Date());
        Payment payment3 = new Payment(3,30, new Date());
        ArrayList<Payment> payments = new ArrayList<Payment>();
        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);
        Wallet wallet1 = new Wallet(1,10000.00,payments);
        paymentAdapter = new PaymentAdapter(this, wallet1.getHistory());
        TextView tvName = (TextView) this.findViewById(R.id.wallet_value);
        tvName.setText(String.valueOf(wallet1.getBalance()) + "$");
        ListView listView = (ListView) findViewById(R.id.payments_list_view1);
        listView.setAdapter(paymentAdapter);
    }
}
