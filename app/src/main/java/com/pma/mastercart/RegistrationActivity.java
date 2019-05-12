package com.pma.mastercart;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFullname;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView goToLogin;
    private Button registerButton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            finish();
        }
        setContentView(R.layout.registration);
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        goToLogin = (TextView) findViewById(R.id.link_to_login);
        goToLogin.setOnClickListener(this);
        registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(this);

        editTextFullname = (EditText) findViewById(R.id.reg_fullname);
        editTextEmail = (EditText) findViewById(R.id.reg_email);
        editTextPassword = (EditText) findViewById(R.id.reg_password);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        if(v == goToLogin)
            finish();
        else if(v ==  registerButton){
            registerUser();
        }
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registration in progress");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Registration successful.", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    finish();
                                }else Toast.makeText(getApplicationContext(), "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
    }
}
