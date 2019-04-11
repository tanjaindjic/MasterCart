package com.pma.mastercart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ProfileActivity extends AppCompatActivity {
    private ImageButton edit_name;
    private ImageButton edit_lastname;
    private ImageButton edit_address;
    private ImageButton edit_email;
    private ImageButton edit_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edit_name = (ImageButton)findViewById(R.id.edit_profile_name_button);
        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = (LinearLayout)findViewById(R.id.edit_profile_name_layout);
                ll.setVisibility(View.VISIBLE);
            }
        });

        edit_lastname = (ImageButton)findViewById(R.id.edit_profile_lastname_button);
        edit_lastname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = (LinearLayout)findViewById(R.id.edit_profile_lastname_layout);
                ll.setVisibility(View.VISIBLE);
            }
        });

        edit_address = (ImageButton)findViewById(R.id.edit_profile_address_button);
        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = (LinearLayout)findViewById(R.id.edit_profile_address_layout);
                ll.setVisibility(View.VISIBLE);
            }
        });

        edit_email = (ImageButton)findViewById(R.id.edit_profile_email_button);
        edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = (LinearLayout)findViewById(R.id.edit_profile_email_layout);
                ll.setVisibility(View.VISIBLE);
            }
        });

        edit_pass = (ImageButton)findViewById(R.id.edit_profile_password_button);
        edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = (LinearLayout)findViewById(R.id.edit_profile_password_layout);
                ll.setVisibility(View.VISIBLE);
            }
        });
    }
}