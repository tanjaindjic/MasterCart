package com.pma.mastercart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.asyncTasks.LoginUserTask;
import com.pma.mastercart.model.DTO.UserDTO;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView goToRegistration;
    private Button loginButton;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml


        setContentView(R.layout.login);
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        goToRegistration = (TextView) findViewById(R.id.link_to_register);
        goToRegistration.setOnClickListener(this);
        loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(this);
        editTextEmail = (EditText) findViewById(R.id.login_email);
        editTextPassword = (EditText) findViewById(R.id.login_password);
    }

    @Override
    public void onClick(View v) {
        if(v == goToRegistration){
            Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(i);
        }
        else if(v == loginButton) {
            try {
                if(login()){
                    finish();
                    progressDialog.dismiss();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean login() throws ExecutionException, InterruptedException {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return false;
        }


        UserDTO userDTO = new UserDTO(email,password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Syncing with Database");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Object[] objects = new Object[]{userDTO, progressDialog};
        AsyncTask<Object, Void, UserDTO> task = new LoginUserTask(this).execute(objects);
        // The URL for making the POST request
        UserDTO user= task.get();
        if(user==null){
            progressDialog.dismiss();
            Toast.makeText(this, "Wrong credentials, log in failed", Toast.LENGTH_SHORT).show();
            return false;
        }

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            return true;
        }

        return false;

    }
}
