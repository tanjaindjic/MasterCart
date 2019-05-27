package com.pma.mastercart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.asyncTasks.EditCategoryTask;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.UpdateUserTask;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.EditUserDTO;
import com.pma.mastercart.model.User;

import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener  {
    private ImageButton edit_firstname;
    private ImageButton edit_lastname;
    private ImageButton edit_address;
    private ImageButton edit_phone;
    private ImageButton edit_pass;
    private TextView profile_firstName;
    private TextView profile_lastName;
    private TextView profile_address;
    private TextView profile_phone;
    private TextView profile_email;
    private TextView profile_password;
    private User user;
    private Context ctx;
    private Button saveChangesBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        user = null;
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
            // The URL for making the POST request
            try {
                user= task.get();
                if(user==null)
                    finish();
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }

        setContentView(R.layout.profile_layout);
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        profile_firstName = (TextView) findViewById(R.id.profile_name);
        profile_lastName = (TextView) findViewById(R.id.profile_lastname);
        profile_address = (TextView) findViewById(R.id.profile_address);
        profile_email = (TextView) findViewById(R.id.profile_email);
        profile_password = (TextView) findViewById(R.id.profile_password);
        profile_phone = (TextView) findViewById(R.id.profile_phone);

        profile_firstName.setText(user.getFirstName());
        profile_lastName.setText(user.getLastName());
        profile_address.setText(user.getAddress());
        profile_email.setText(user.getEmail());
        profile_password.setText(user.getPassword());
        profile_phone.setText(user.getPhone());

        saveChangesBtn = (Button) findViewById(R.id.btn_save_profile_changes);
        saveChangesBtn.setOnClickListener(this);

        edit_firstname = (ImageButton)findViewById(R.id.edit_profile_name_button);
        edit_firstname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("First Name:", user.getFirstName());
            }
        });

        edit_lastname = (ImageButton)findViewById(R.id.edit_profile_lastname_button);
        edit_lastname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("Last Name:", user.getLastName());
            }
        });

        edit_address = (ImageButton)findViewById(R.id.edit_profile_address_button);
        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("Address:", user.getAddress());
            }
        });

        edit_pass = (ImageButton)findViewById(R.id.edit_profile_password_button);
        edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("Password:", "");
            }
        });

        edit_phone = (ImageButton)findViewById(R.id.edit_profile_phone_button);
        edit_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("Phone:", user.getPhone());
            }
        });

    }

    private void updateData(String label, String value){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_change_profile_data, null);
        builder.setView(view);
        TextView labelaTeksta = (TextView) view.findViewById(R.id.new_profile_name_label);
        labelaTeksta.setText(label);
        EditText noviTekst = (EditText)  view.findViewById(R.id.new_profile_name);
        if(label.equals("Password:"))
            noviTekst.setTransformationMethod(PasswordTransformationMethod.getInstance());
        else
            noviTekst.setText(value);
        Button okButton = (Button) view.findViewById(R.id.button_OK_);
        Button cancelButton = (Button) view.findViewById(R.id.button_cancel);

        AlertDialog alert = builder.create();
        alert.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (label){
                    case "Password:":
                        if(noviTekst.getText().toString().isEmpty()){
                            Toast.makeText(ctx, "Password can not be empty", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        profile_password.setText(noviTekst.getText());
                        break;
                    case "Address:":
                        profile_address.setText(noviTekst.getText());
                        break;
                    case "Last Name:":
                        profile_lastName.setText(noviTekst.getText());
                        break;
                    case "First Name:":
                        profile_firstName.setText(noviTekst.getText());
                        break;
                    case "Phone:":
                        profile_phone.setText(noviTekst.getText());
                        break;
                }
                alert.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v==saveChangesBtn){
            int resulet = 0;
            resulet = saveChanges();
            if(resulet==0){
                Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Something went wrong, changes not saved", Toast.LENGTH_SHORT).show();
        }

    }

    private int saveChanges() {
        String firstName = profile_firstName.getText().toString().trim();
        String lastName = profile_lastName.getText().toString().trim();
        String address = profile_address.getText().toString().trim();
        String email = profile_email.getText().toString().trim();
        String password = profile_password.getText().toString().trim();
        String phone = profile_phone.getText().toString().trim();
        if(email.isEmpty() || password.isEmpty()){
            return 1;
        }
        EditUserDTO editUserDTO = new EditUserDTO(email, password, firstName, lastName, address, phone);
        SharedPreferences pref = this.getSharedPreferences(MainActivity.PREFS, 0); // 0 - for private mode
        Object[] objects = {editUserDTO,  pref.getString("AuthToken", "")};
        AsyncTask<Object, Void, User> task = new UpdateUserTask().execute(objects);
        User response = null;
        try {
            response = task.get();
            if(response==null){
                return 1;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }
}