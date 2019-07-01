package com.pma.mastercart;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.pma.mastercart.asyncTasks.EditProductTask;
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

public class EditProductActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button editProduct, btnEditPictureProduct;
    private EditText editEditTextNameProduct, editEditTextPriceProduct,
            editEditTextDescriptionProduct, editEditTextQuantityProduct, editEditTextSizeProduct, editEditTextDiscountProduct;
    private ImageView imageEditViewProduct;
    private Long idProduct;
    private Spinner editSpinnerCategoryProduct;
    private Category cat;
    private Context ctx;
    private Product product;
    private static String filePath;
    private static String file_extn;
    private ImageView imageViewProduct;
    private String imagePath;
    public static ProgressDialog dialog;

    public static void onLoad(String id) {
        if(file_extn==null){
            if(AddProductActivity.dialog!=null)
                AddProductActivity.dialog.dismiss();
            if(EditProductActivity.dialog!=null)
                EditProductActivity.dialog.dismiss();
            return;
        }
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
        product = intent.getParcelableExtra("editProductEditing");

        editProduct = (Button) findViewById(R.id.editProduct);
        editProduct.setOnClickListener(this);

        btnEditPictureProduct = (Button) findViewById(R.id.btnEditPictureProduct);
        btnEditPictureProduct.setOnClickListener(this);

        editEditTextNameProduct = (EditText) findViewById(R.id.editEditTextNameProduct);
        editEditTextNameProduct.setText(product.getName());

        editEditTextPriceProduct = (EditText) findViewById(R.id.editEditTextPriceProduct);
        editEditTextPriceProduct.setText(Double.toString(product.getPrice()));

        editEditTextDescriptionProduct = (EditText) findViewById(R.id.editEditTextDescriptionProduct);
        editEditTextDescriptionProduct.setText(product.getDescription());

        editEditTextQuantityProduct = (EditText) findViewById(R.id.editEditTextQuantityProduct);
        editEditTextQuantityProduct.setText(Integer.toString(product.getOnStock()));

        editEditTextSizeProduct = (EditText) findViewById(R.id.editEditTextSizeProduct);
        editEditTextSizeProduct.setText(product.getSize());

        editEditTextDiscountProduct = (EditText) findViewById(R.id.editEditTextDiscountProduct);
        editEditTextDiscountProduct.setText(Integer.toString(product.getDiscount()));

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

        imageViewProduct = (ImageView) findViewById(R.id.imageEditViewProduct);
        if(product.getImageResource()==null){
            imageViewProduct.setImageResource(R.drawable.ic_dummy);
        }else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImageResource(), 0, product.getImageResource().length);
            imageViewProduct.setImageBitmap(bitmap);
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
        int obrisi=-1;
        for(int i=0; i<categorije.size(); i++){
            if(categorije.get(i).getName().equals("All"))
                obrisi=i;
        }
        if(obrisi!=-1){
            categorije.remove(obrisi);
        }

        ArrayAdapter<Category> arrayAdapter = new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_item,categorije);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editSpinnerCategoryProduct.setAdapter(arrayAdapter);


    }

    @Override
    public void onClick(View view) {
        if (view == editProduct) {
            if(editEditTextNameProduct.getText().toString().trim().isEmpty() ||
                    editEditTextPriceProduct.getText().toString().trim().isEmpty() ||
                    editEditTextQuantityProduct.getText().toString().trim().isEmpty() ||
                    editEditTextSizeProduct.getText().toString().trim().isEmpty())
                Toast.makeText(this, "Invalid to update.", Toast.LENGTH_SHORT).show();
            else
                editProduct();

        }else if(view==btnEditPictureProduct){
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

    private boolean editProduct(){
        try{
            Double value= Double.parseDouble(editEditTextPriceProduct.getText().toString().trim());
        } catch (Exception e1) {
            //e1.printStackTrace();
            Toast.makeText(ctx, "Price has to be (decimal) number", Toast.LENGTH_SHORT).show();
            return false;
        }
        try{
            Integer value= Integer.parseInt(editEditTextQuantityProduct.getText().toString().trim());
        } catch (Exception e1) {
            //e1.printStackTrace();
            Toast.makeText(ctx, "Quantity is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }
        try{
            Integer value= Integer.parseInt(editEditTextDiscountProduct.getText().toString().trim());
            if(value>99){
                Toast.makeText(ctx, "Discount must be number <100", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e1) {
            //e1.printStackTrace();
            Toast.makeText(ctx, "Discount must be number <100", Toast.LENGTH_SHORT).show();
            return false;
        }
        dialog.setMessage("Updating product...");
        dialog.show();

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


        return true;
    }


}
