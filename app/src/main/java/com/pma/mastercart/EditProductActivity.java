package com.pma.mastercart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.pma.mastercart.asyncTasks.EditProductTask;
import com.pma.mastercart.model.DTO.ProductDTO;

public class EditProductActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button editProduct, btnEditPictureProduct;
    private EditText editEditTextNameProduct, editEditTextPriceProduct, editEditTextDescriptionProduct, editEditTextQuantityProduct, editEditTextSizeProduct, editEditTextDiscountProduct;
    private ImageView imageEditViewProduct;
    private Long idProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product);
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
        // productDTO.setOnStock(editTextQuantityProduct.getText().toString().trim());
        productDTO.setSize(editEditTextSizeProduct.getText().toString().trim());
        productDTO.setDiscount(editEditTextDiscountProduct.getText().toString().trim());
        productDTO.setId(idProduct.toString());
        AsyncTask<ProductDTO, Void, ProductDTO> task = new EditProductTask().execute(productDTO);

        return true;
    }
}
