package com.pma.mastercart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.pma.mastercart.adapter.OnLoadDataListener;
import com.pma.mastercart.asyncTasks.AddShopTask;
import com.pma.mastercart.asyncTasks.ProfilePictureTask;
import com.pma.mastercart.asyncTasks.ShopPictureTask;
import com.pma.mastercart.model.DTO.ShopDTO;

import org.springframework.util.support.Base64;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

public class AddShopActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addShopButton,btnAddPictureShop;
    private EditText editTextNameShop,editLocationShop,
            editPhoneShop,editEmailShop,editLongitudeShop,editLatitudeShop, editAddSellerShop;
    private ImageView shopImg;
    private static int PICK_IMAGE = 100;
    private static Uri selectedImage;
    private static String filePath;
    private static String file_extn;
    private String imagePath;
    public static ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shop);
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addShopButton = (Button) findViewById(R.id.btnAddShop);
        addShopButton.setOnClickListener(this);
        btnAddPictureShop = (Button) findViewById(R.id.btnAddPictureShop);
        btnAddPictureShop.setOnClickListener(this);
        editTextNameShop = (EditText) findViewById(R.id.editTextNameShop);
        editLocationShop = (EditText) findViewById(R.id.editLocationShop);
        editPhoneShop = (EditText) findViewById(R.id.editPhoneShop);
        editEmailShop = (EditText) findViewById(R.id.editEmailShop);
        editLongitudeShop = (EditText) findViewById(R.id.editLongitudeShop);
        editLatitudeShop = (EditText) findViewById(R.id.editLatitudeShop);
        editAddSellerShop = (EditText) findViewById(R.id.editAddSellerShop);
        shopImg = (ImageView) findViewById(R.id.imageViewShop);
        dialog = new ProgressDialog(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==addShopButton){
            try {
                addShop();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else if(view==btnAddPictureShop){
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
                shopImg.setImageBitmap(bm);

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


    private boolean addShop() throws ExecutionException, InterruptedException {
        dialog.setMessage("Adding new shop...");
        dialog.show();
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setName(editTextNameShop.getText().toString().trim());
        shopDTO.setLocation(editLocationShop.getText().toString().trim());
        shopDTO.setPhone(editPhoneShop.getText().toString().trim());
        shopDTO.setEmail(editEmailShop.getText().toString().trim());
        shopDTO.setLat(editLatitudeShop.getText().toString().trim());
        shopDTO.setLng(editLongitudeShop.getText().toString().trim());
        shopDTO.setSellerEmail(editAddSellerShop.getText().toString().trim());

        AsyncTask<ShopDTO, Void, ShopDTO> task = new AddShopTask().execute(shopDTO);
        // The URL for making the POST request
        ShopDTO user= task.get();
        if(user==null){
            Toast.makeText(this, "Wrong inputs, log in failed", Toast.LENGTH_SHORT).show();
            return false;
        }
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
                new ShopPictureTask().execute(objects);
            } else {
                //NOT IN REQUIRED FORMAT
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
