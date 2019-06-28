package com.pma.mastercart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pma.mastercart.asyncTasks.EditShopTask;
import com.pma.mastercart.asyncTasks.RetrieveProductsTask;
import com.pma.mastercart.asyncTasks.RetrieveShopsTask;
import com.pma.mastercart.asyncTasks.ShopPictureTask;
import com.pma.mastercart.model.DTO.ShopDTO;
import com.pma.mastercart.model.Shop;

import org.springframework.util.support.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditShopActivity  extends AppCompatActivity implements View.OnClickListener {

    private Button editShopButton,btnEditPictureShop;
    private EditText editEditTextNameShop,editEditLocationShop,editEditPhoneShop,editEditEmailShop,editEditLongitudeShop,editEditLatitudeShop;
    private static int PICK_IMAGE = 100;
    private static Uri selectedImage;
    private Long idShop;
    private Shop shop;
    private static String filePath;
    private static String file_extn;
    private ImageView imageViewShop;
    private String imagePath;
    public static ProgressDialog dialog;
    private Context ctx;

    public static void onLoad(String id) {
        if(file_extn==null){
            if(AddShopActivity.dialog!=null)
                AddShopActivity.dialog.dismiss();
            if(EditShopActivity.dialog!=null)
                EditShopActivity.dialog.dismiss();
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
                new ShopPictureTask().execute(objects);
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
        setContentView(R.layout.edit_shop);

        ctx = this;

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        idShop = intent.getLongExtra("idShop",0);
        shop = (Shop) intent.getParcelableExtra("editShopEditing");
        editShopButton = (Button) findViewById(R.id.editShopButton);
        editShopButton.setOnClickListener(this);

        btnEditPictureShop = (Button) findViewById(R.id.btnEditPictureShop);
        btnEditPictureShop.setOnClickListener(this);

        editEditTextNameShop = (EditText) findViewById(R.id.editEditTextNameShop);
        editEditTextNameShop.setText(shop.getName());

        editEditLocationShop = (EditText) findViewById(R.id.editEditLocationShop);
        editEditLocationShop.setText(shop.getLocation());

        editEditPhoneShop = (EditText) findViewById(R.id.editEditPhoneShop);
        editEditPhoneShop.setText(shop.getPhone());

        editEditEmailShop = (EditText) findViewById(R.id.editEditEmailShop);
        editEditEmailShop.setText(shop.getEmail());

        editEditLongitudeShop = (EditText) findViewById(R.id.editEditLongitudeShop);
        editEditLongitudeShop.setText(Float.toString(shop.getLng()));

        editEditLatitudeShop = (EditText) findViewById(R.id.editEditLatitudeShop);
        editEditLatitudeShop.setText(Float.toString(shop.getLat()));

        imageViewShop = (ImageView) findViewById(R.id.imageViewShop);
        if(shop.getImageResource()==null){
            imageViewShop.setImageResource(R.drawable.ic_dummy);
        }else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(shop.getImageResource(), 0, shop.getImageResource().length);
            imageViewShop.setImageBitmap(bitmap);
        }
        dialog = new ProgressDialog(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view==editShopButton){
            try {
                editShop();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else if(view==btnEditPictureShop){
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
                imageViewShop.setImageBitmap(bm);

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

    private boolean editShop() throws ExecutionException, InterruptedException {
        if(editEditTextNameShop.getText().toString().trim().isEmpty() || editEditLocationShop.getText().toString().trim().isEmpty() ||
                editEditPhoneShop.getText().toString().trim().isEmpty() || editEditEmailShop.getText().toString().trim().isEmpty() ||
                editEditLatitudeShop.getText().toString().trim().isEmpty() || editEditLongitudeShop.getText().toString().trim().isEmpty()){
            Toast.makeText(ctx, "All data must be entered", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            try{
                Double value= Double.parseDouble(editEditLatitudeShop.getText().toString().trim());
            } catch (Exception e1) {
                //e1.printStackTrace();
                Toast.makeText(ctx, "Latitude has to be (decimal) number", Toast.LENGTH_SHORT).show();
                return false;
            }

            try{
                Double value= Double.parseDouble(editEditLongitudeShop.getText().toString().trim());
            } catch (Exception e1) {
                //e1.printStackTrace();
                Toast.makeText(ctx, "Longitude has to be (decimal) number", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(!isEmailValid(editEditEmailShop.getText().toString().trim())){
                Toast.makeText(ctx, "Email is not valid.", Toast.LENGTH_SHORT).show();
                return false;
            }


        }
        dialog.setMessage("Updating shop...");
        dialog.show();

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(idShop.toString());
        //Toast.makeText(this, shopDTO.getId().toString(), Toast.LENGTH_SHORT).show();
        shopDTO.setName(editEditTextNameShop.getText().toString().trim());
        shopDTO.setLocation(editEditLocationShop.getText().toString().trim());
        shopDTO.setPhone(editEditPhoneShop.getText().toString().trim());
        shopDTO.setEmail(editEditEmailShop.getText().toString().trim());
        shopDTO.setLat(editEditLongitudeShop.getText().toString().trim());
        shopDTO.setLng(editEditLatitudeShop.getText().toString().trim());
       // shopDTO.setImageResource(selectedImage.toString().trim());

        AsyncTask<ShopDTO, Void, ShopDTO> task = new EditShopTask().execute(shopDTO);
       // AsyncTask<String, Void, ArrayList<Shop>> task2 = new RetrieveShopsTask().execute("sta god");
        // The URL for making the POST request
        ShopDTO shopDTO1= task.get();
        if(shopDTO1==null){
            Toast.makeText(this, "Something went wrong. Updating failed", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}