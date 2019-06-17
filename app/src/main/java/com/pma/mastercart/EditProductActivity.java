package com.pma.mastercart;

import android.content.Context;
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

import com.pma.mastercart.asyncTasks.EditProductTask;
import com.pma.mastercart.asyncTasks.GetCategoriesTask;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.ProductDTO;
import com.pma.mastercart.model.Product;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EditProductActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button editProduct, btnEditPictureProduct;
    private EditText editEditTextNameProduct, editEditTextPriceProduct,
            editEditTextDescriptionProduct, editEditTextQuantityProduct, editEditTextSizeProduct, editEditTextDiscountProduct;
    private ImageView imageEditViewProduct;
    private Long idProduct;
    private Spinner editSpinnerCategoryProduct;
    private Category cat;
    private Context ctx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);
        ctx = this;
        getIntent().removeExtra("productUpdate");
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        idProduct = intent.getLongExtra("PRODUCT_ID", 0);
        editProduct = (Button) findViewById(R.id.editProduct);
        editProduct.setOnClickListener(this);
        btnEditPictureProduct = (Button) findViewById(R.id.btnEditPictureProduct);
        btnEditPictureProduct.setOnClickListener(this);
        editEditTextNameProduct = (EditText) findViewById(R.id.editEditTextNameProduct);
        editEditTextPriceProduct = (EditText) findViewById(R.id.editEditTextPriceProduct);
        editEditTextDescriptionProduct = (EditText) findViewById(R.id.editEditTextDescriptionProduct);
        editEditTextQuantityProduct = (EditText) findViewById(R.id.editEditTextQuantityProduct);
        editEditTextSizeProduct = (EditText) findViewById(R.id.editEditTextSizeProduct);
        editEditTextDiscountProduct = (EditText) findViewById(R.id.editEditTextDiscountProduct);
        editSpinnerCategoryProduct = (Spinner) findViewById(R.id.editSpinnerCategoryProduct);
        editSpinnerCategoryProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
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
        editSpinnerCategoryProduct.setAdapter(arrayAdapter);


    }

    @Override
    public void onClick(View view) {
        if (view == editProduct) {
            editProduct();
        }
    }

    private boolean editProduct(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(editEditTextNameProduct.getText().toString().trim());
        productDTO.setPrice(editEditTextPriceProduct.getText().toString().trim());
        productDTO.setDescription(editEditTextDescriptionProduct.getText().toString().trim());
        productDTO.setOnStock(editEditTextQuantityProduct.getText().toString().trim());
        productDTO.setSize(editEditTextSizeProduct.getText().toString().trim());
        productDTO.setDiscount(editEditTextDiscountProduct.getText().toString().trim());
        productDTO.setId(idProduct.toString());
        productDTO.setIdCategory(cat.getId().toString());
        AsyncTask<ProductDTO, Void, ProductDTO> task = new EditProductTask().execute(productDTO);
        ArrayList<Long> p = new ArrayList<>();
        p.add(idProduct);
        Intent intent = this.getParentActivityIntent();
        intent.removeExtra("productUpdate");
        intent.putExtra("productUpdate", p);
        return true;
    }
}
