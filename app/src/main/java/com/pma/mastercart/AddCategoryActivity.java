package com.pma.mastercart;

import com.pma.mastercart.model.Category;


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
        editTextNameCategory = (EditText) findViewById(R.id.editTextNameCategory);
    }


    @Override
    public void onClick(View view) {
        if(view==addCategoryButton){
            switch(addCategory()){
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
                default:
                    break;

            }
        }
    }

    private int addCategory(){

        String nazivKategorije = editTextNameCategory.getText().toString().trim();

        if(nazivKategorije.isEmpty()){
            editTextNameCategory.setError("can not be empty");
            return 2;
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("name", editTextNameCategory.getText().toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<Category> response = restTemplate.postForEntity(MainActivity.URL+"category", request, Category.class);

        if(response==null || (!response.getStatusCode().equals(HttpStatus.OK)))
            return 1;
        return 0;
    }
}
