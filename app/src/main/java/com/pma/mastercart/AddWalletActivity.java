package com.pma.mastercart;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.pma.mastercart.asyncTasks.AddWalletTask;
import com.pma.mastercart.asyncTasks.GetUsersTask;
import com.pma.mastercart.model.DTO.WalletDTO;
import com.pma.mastercart.model.User;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class AddWalletActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner walletSpinner;
    private User us;
    private Button buttonWallet;
    private EditText balance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_wallet);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        buttonWallet = (Button) findViewById(R.id.btnAddWallet);
        buttonWallet.setOnClickListener(this);

        balance = (EditText) findViewById(R.id.walletBalance);

        walletSpinner = (Spinner) findViewById(R.id.walletSpinner);
        walletSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                us = (User) adapterView.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            initializeUI();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void initializeUI() throws ExecutionException, InterruptedException {


        AsyncTask<Void, Void, ArrayList<User>> task = new GetUsersTask().execute();
        ArrayList<User> users =  task.get();


        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(this,android.R.layout.simple_spinner_item,users);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        walletSpinner.setAdapter(arrayAdapter);


    }

    @Override
    public void onClick(View view) {
        if (view == buttonWallet) {
            addWallet();
        }
    }

    private boolean addWallet(){
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setBalance(balance.getText().toString().trim());
        walletDTO.setUserEmail(us.getEmail().toString());
        AsyncTask<WalletDTO, Void, WalletDTO> task = new AddWalletTask().execute(walletDTO);
        return true;

    }
}
