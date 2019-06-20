package com.pma.mastercart;

import com.pma.mastercart.asyncTasks.AddCategoryTask;
import com.pma.mastercart.asyncTasks.LoginUserTask;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.UserDTO;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;


public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addCategoryButton;
    private EditText editTextNameCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addCategoryButton = (Button) findViewById(R.id.btnAddCategory);
        addCategoryButton.setOnClickListener(this);
        editTextNameCategory = (EditText) findViewById(R.id.editTextNameCategory);
    }


    @Override
    public void onClick(View view) {
        int response = 2;
        try {
            response = addCategory();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(view==addCategoryButton){
            switch(response){
                case 0:
                    editTextNameCategory.setText("");
                    Toast.makeText(this, "Category successfully added.", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    editTextNameCategory.requestFocus();
                    break;
                case 3:
                    Intent i = new Intent(getApplicationContext(), OfflineActivity.class);
                    startActivity(i);
                    break;
                default:
                    break;

            }
        }
    }

    private int addCategory() throws ExecutionException, InterruptedException {

        String nazivKategorije = editTextNameCategory.getText().toString().trim();

        if(nazivKategorije.isEmpty()){
            editTextNameCategory.setError("can not be empty");
            return 2;
        }
        SharedPreferences pref = this.getSharedPreferences(MainActivity.PREFS, 0); // 0 - for private mode
        String[] strings = {nazivKategorije, pref.getString("AuthToken", "")};

        AsyncTask<String, Void, Category> task = new AddCategoryTask().execute(strings);
        Category response = task.get();
        if(response==null)
            return 1;
        if(response.getId()==(long)-1)
            return 3;
        return 0;
    }
}
