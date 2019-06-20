package com.pma.mastercart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.pma.mastercart.asyncTasks.LoginUserTask;
import com.pma.mastercart.asyncTasks.OfflineTask;
import com.pma.mastercart.model.DTO.UserDTO;

import java.util.concurrent.ExecutionException;

public class OfflineActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tekst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_page);

        tekst = (TextView) findViewById(R.id.text_offline);
        tekst.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == tekst){
            AsyncTask<Void, Void, Boolean> task = new OfflineTask().execute();
            try {
                Boolean response = task.get();
                if(!response){
                    finish();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
}
