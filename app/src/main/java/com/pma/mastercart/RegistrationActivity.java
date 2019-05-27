package com.pma.mastercart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.LoginUserTask;
import com.pma.mastercart.asyncTasks.RegisterUserTask;
import com.pma.mastercart.model.CartItem;
import com.pma.mastercart.model.Conversation;
import com.pma.mastercart.model.DTO.AddUserDTO;
import com.pma.mastercart.model.DTO.UserDTO;
import com.pma.mastercart.model.Order;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.User;
import com.pma.mastercart.model.enums.Role;

import java.sql.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView goToLogin;
    private Button registerButton;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")){
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

        editTextFirstName = (EditText) findViewById(R.id.reg_firstname);
        editTextLastName = (EditText) findViewById(R.id.reg_lastname);
        editTextEmail = (EditText) findViewById(R.id.reg_email);
        editTextPassword = (EditText) findViewById(R.id.reg_password);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        if(v == goToLogin)
            finish();
        else if(v ==  registerButton){
            int result = registerUser();
            switch(result){
                case 0:
                    progressDialog.dismiss();
                    Toast.makeText(this, "Account created. User logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case 1:
                    break;
                case 2:
                    progressDialog.dismiss();
                    Toast.makeText(this, "Something went wrong. Registration not possible", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    progressDialog.dismiss();
                    Toast.makeText(this, "User created! Log in please", Toast.LENGTH_SHORT).show();
                    Intent ii = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(ii);
                    finish();
                    break;

            }
        }
    }

    private int registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();

        if(TextUtils.isEmpty(email) || (!isEmailValid(email))){
            Toast.makeText(this, "Please enter valid email address.", Toast.LENGTH_SHORT).show();
            return 1;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return 1;
        }

        progressDialog.setMessage("Registration in progress");
        progressDialog.show();
        AddUserDTO u = new AddUserDTO(email, password, firstName, lastName);
        AsyncTask<AddUserDTO, Void, User> task = new RegisterUserTask(this).execute(u);
        User user = null;
        try {
            user = task.get();
            if(user==null)
                return 2;
            UserDTO userDTO = new UserDTO(user.getEmail(), user.getPassword());
            Object[] objects = new Object[]{userDTO, progressDialog};
            AsyncTask<Object, Void, UserDTO> taskLogin = new LoginUserTask(this).execute(objects);
            if(taskLogin.get()==null){
                return 3;
            }
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {
        }
        return 0;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
