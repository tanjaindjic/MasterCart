package com.pma.mastercart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.RetrieveProductsTask;
import com.pma.mastercart.asyncTasks.UpdateUserTask;
import com.pma.mastercart.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class SettingsActivity extends AppCompatActivity {
    private Switch notification_switch;
    private TextView payment_info;
    private Context ctx;
    private User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);


        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ctx = this;
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
            // The URL for making the POST request
            try {
                User user= task.get();
                if(user!=null)
                    currentUser = user;
                else{
                    Toast.makeText(ctx, "Please log in first", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.appContext, LoginActivity.class);
                    startActivity(i);
                }
            } catch (InterruptedException e) {
                currentUser = null;
            } catch (ExecutionException e) {
                currentUser = null;
            }
        }
        else{
            Toast.makeText(ctx, "Please log in first", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.appContext, LoginActivity.class);
            startActivity(i);
        }

        notification_switch = (Switch) findViewById(R.id.notification_switch);
        notification_switch.setChecked(currentUser.isNotifications());
        notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    currentUser.setNotifications(true);
                }
                else{
                    currentUser.setNotifications(false);
                }
                String token = sharedpreferences.getString("AuthToken", null);
                Object[] objects = {currentUser, token};
                AsyncTask<Object, Void, User> task = new UpdateUserTask().execute(objects);
                try {
                    if(task.get()!=null){
                        Toast.makeText(ctx, "Settings updated", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        payment_info = (TextView) findViewById(R.id.payment_info);
        payment_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Your payment should be proceeded to the bank account with number 123-493292032-23");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }
}
