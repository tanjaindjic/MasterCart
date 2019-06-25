package com.pma.mastercart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.pma.mastercart.asyncTasks.ProductPictureTask;
import com.pma.mastercart.asyncTasks.ShopPictureTask;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.ProductDTO;
import com.pma.mastercart.model.Product;

import org.springframework.util.support.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener{
    private Button addProduct,btnAddPictureProduct;
    private EditText editTextNameProduct,editTextPriceProduct,editTextDescriptionProduct,editTextQuantityProduct,editTextSizeProduct,editTextDiscountProduct;
    private ImageView imageViewProduct;
    private Long idShop;
    private Spinner spinnerCategoryProduct;
    private Category cat;
    public static ProgressDialog dialog;
    private static String filePath;
    private static String file_extn;
    private String imagePath;

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
        imageViewProduct = (ImageView) findViewById(R.id.imageViewProduct);
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

        dialog = new ProgressDialog(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
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
        }else if(view==btnAddPictureProduct){
        showFileChooser();
        }
    }
    private void showFileChooser(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

                filePath = getPath(selectedImage);
                file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                Bitmap bm = BitmapFactory.decodeFile(filePath);
                imageViewProduct.setImageBitmap(bm);

            }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
    }

    private boolean addProduct(){
        dialog.setMessage("Adding new product...");
        dialog.show();
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

    public static void onLoad(String id) {

        try {
            if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                Bitmap bm = BitmapFactory.decodeFile(filePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String encodedImage = Base64.encodeBytes(imageBytes);


                Object[] objects = {encodedImage, id};
                new ProductPictureTask().execute(objects);
            } else {
                //NOT IN REQUIRED FORMAT
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}