package com.pma.mastercart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.pma.mastercart.asyncTasks.LoginUserTask;
import com.pma.mastercart.asyncTasks.RetrieveShopsTask;
import com.pma.mastercart.model.DTO.UserDTO;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
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

        progressDialog = new ProgressDialog(this);
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
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
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
        progressDialog.setMessage("Logging In");
        progressDialog.show();

        UserDTO userDTO = new UserDTO(email,password);
        AsyncTask<UserDTO, Void, UserDTO> task = new LoginUserTask().execute(userDTO);
        // The URL for making the POST request
        UserDTO user= task.get();
        if(user==null){
            Toast.makeText(this, "Wrong credentials, log in failed", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log.d("LOG", user.getPassword());

        progressDialog.dismiss();
        return true;

    }
}
