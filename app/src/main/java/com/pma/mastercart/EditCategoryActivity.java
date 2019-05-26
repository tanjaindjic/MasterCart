package com.pma.mastercart;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pma.mastercart.asyncTasks.AddCategoryTask;
import com.pma.mastercart.asyncTasks.EditCategoryTask;
import com.pma.mastercart.asyncTasks.GetCategoriesTask;
import com.pma.mastercart.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EditCategoryActivity extends AppCompatActivity implements View.OnClickListener  {

    private AppCompatSpinner spinner;
    private Button saveChangesBtn;
    private EditText editCategory;
    private ArrayList<Category> categs;
    private Category inChange;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_category);
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinner = (AppCompatSpinner) findViewById(R.id.category_spinner);
        try {
            addCategoriesOnSpinner();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        editCategory = (EditText) findViewById(R.id.edit_text_category);
        saveChangesBtn = (Button) findViewById(R.id.btn_edit_category);

        saveChangesBtn.setOnClickListener(this);

    }

    private void addCategoriesOnSpinner() throws ExecutionException, InterruptedException {
        AsyncTask<Void, Void, ArrayList<Category>> task = new GetCategoriesTask().execute();
        categs = task.get();

        List<String> vrednosti = new ArrayList<String>();
        for(Category c0 : categs){
            vrednosti.add(c0.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, vrednosti);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);



        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }

    @Override
    public void onClick(View v) {
        if(v==saveChangesBtn){
            int resulet = 0;
            try {
                resulet = saveChanges();
                if(resulet==0){
                    Toast.makeText(this, "Category changed successfully", Toast.LENGTH_SHORT).show();
                }
                else if(resulet==1){
                    editCategory.setError("Name can not be empty");
                }
                else
                    Toast.makeText(this, "Something went wrong, changes not saved", Toast.LENGTH_SHORT).show();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private int saveChanges() throws ExecutionException, InterruptedException {
        String newName = editCategory.getText().toString().trim();
        if(newName.isEmpty())
            return 1;
        inChange.setName(newName);
        SharedPreferences pref = this.getSharedPreferences(MainActivity.PREFS, 0); // 0 - for private mode
        Object[] objects = {inChange,  pref.getString("AuthToken", "")};
        AsyncTask<Object, Void, Category> task = new EditCategoryTask().execute(objects);
        Category response = task.get();
        if(response==null){
            return 2;
        }
        addCategoriesOnSpinner();
        return 0;
    }

    private class CustomOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            editCategory.setText(parent.getItemAtPosition(position).toString());
            inChange = categs.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
