package com.pma.mastercart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.pma.mastercart.asyncTasks.AddProductTask;
import com.pma.mastercart.asyncTasks.GetCategoriesTask;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.ProductDTO;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener{
    private Button addProduct,btnAddPictureProduct;
    private EditText editTextNameProduct,editTextPriceProduct,editTextDescriptionProduct,editTextQuantityProduct,editTextSizeProduct,editTextDiscountProduct;
    private ImageView imageViewProduct;
    private Long idShop;
    private Spinner spinnerCategoryProduct;
    private Category cat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        idShop = intent.getLongExtra("idShop",0);
        addProduct = (Button) findViewById(R.id.addProduct);
        addProduct.setOnClickListener(this);
        btnAddPictureProduct = (Button) findViewById(R.id.btnAddPictureProduct);
        btnAddPictureProduct.setOnClickListener(this);
        editTextNameProduct = (EditText) findViewById(R.id.editTextNameProduct);
        editTextPriceProduct = (EditText) findViewById(R.id.editTextPriceProduct);
        editTextDescriptionProduct = (EditText) findViewById(R.id.editTextDescriptionProduct);
        editTextQuantityProduct = (EditText) findViewById(R.id.editTextQuantityProduct);
        editTextSizeProduct = (EditText) findViewById(R.id.editTextSizeProduct);
        editTextDiscountProduct = (EditText) findViewById(R.id.editTextDiscountProduct);
        spinnerCategoryProduct = (Spinner) findViewById(R.id.spinnerCategoryProduct);
        spinnerCategoryProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat = (Category) adapterView.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        try {
            initializeUI();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() throws ExecutionException, InterruptedException {


        AsyncTask<Void, Void, ArrayList<Category>> task = new GetCategoriesTask().execute();
        ArrayList<Category> categorije =  task.get();


        ArrayAdapter<Category> arrayAdapter = new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_item,categorije);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoryProduct.setAdapter(arrayAdapter);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view==addProduct){
            addProduct();
        }
    }

    private boolean addProduct(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(editTextNameProduct.getText().toString().trim());
        productDTO.setPrice(editTextPriceProduct.getText().toString().trim());
        productDTO.setDescription(editTextDescriptionProduct.getText().toString().trim());
        productDTO.setOnStock(editTextQuantityProduct.getText().toString().trim());
        productDTO.setSize(editTextSizeProduct.getText().toString().trim());
        productDTO.setDiscount(editTextDiscountProduct.getText().toString().trim());
        productDTO.setIdShop(idShop.toString());
        productDTO.setIdCategory(cat.getId().toString());
        AsyncTask<ProductDTO, Void, ProductDTO> task = new AddProductTask().execute(productDTO);

        return true;
    }
}