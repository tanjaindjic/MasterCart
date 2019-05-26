package com.pma.mastercart;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pma.mastercart.asyncTasks.AddShopTask;
import com.pma.mastercart.model.DTO.ShopDTO;

import java.util.concurrent.ExecutionException;

public class AddShopActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addShopButton,btnAddPictureShop;
    private EditText editTextNameShop,editLocationShop,editPhoneShop,editEmailShop,editLongitudeShop,editLatitudeShop;
    private static int PICK_IMAGE = 100;
    private static Uri selectedImage;

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
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, PICK_IMAGE);
        }
    }

    private boolean addShop() throws ExecutionException, InterruptedException {

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setName(editTextNameShop.getText().toString().trim());
        shopDTO.setLocation(editLocationShop.getText().toString().trim());
        shopDTO.setPhone(editPhoneShop.getText().toString().trim());
        shopDTO.setEmail(editEmailShop.getText().toString().trim());
        shopDTO.setLat(editLatitudeShop.getText().toString().trim());
        shopDTO.setLng(editLongitudeShop.getText().toString().trim());
        shopDTO.setImageResource(selectedImage.toString().trim());

        AsyncTask<ShopDTO, Void, ShopDTO> task = new AddShopTask().execute(shopDTO);
        // The URL for making the POST request
        ShopDTO user= task.get();
        if(user==null){
            Toast.makeText(this, "Wrong inputs, log in failed", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();

            ImageView imageView = (ImageView) findViewById(R.id.imageViewShop);
            imageView.setImageURI(selectedImage);

        }
    }
}
