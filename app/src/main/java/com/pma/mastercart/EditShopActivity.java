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

import com.pma.mastercart.asyncTasks.EditShopTask;
import com.pma.mastercart.asyncTasks.RetrieveShopsTask;
import com.pma.mastercart.model.DTO.ShopDTO;
import com.pma.mastercart.model.Shop;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EditShopActivity  extends AppCompatActivity implements View.OnClickListener {

    private Button editShopButton,btnEditPictureShop;
    private EditText editEditTextNameShop,editEditLocationShop,editEditPhoneShop,editEditEmailShop,editEditLongitudeShop,editEditLatitudeShop;
    private static int PICK_IMAGE = 100;
    private static Uri selectedImage;
    private Long idShop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_shop);
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        idShop = intent.getLongExtra("idShop",0);
        editShopButton = (Button) findViewById(R.id.editShopButton);
        editShopButton.setOnClickListener(this);
        btnEditPictureShop = (Button) findViewById(R.id.btnEditPictureShop);
        btnEditPictureShop.setOnClickListener(this);
        editEditTextNameShop = (EditText) findViewById(R.id.editEditTextNameShop);
        editEditLocationShop = (EditText) findViewById(R.id.editEditLocationShop);
        editEditPhoneShop = (EditText) findViewById(R.id.editEditPhoneShop);
        editEditEmailShop = (EditText) findViewById(R.id.editEditEmailShop);
        editEditLongitudeShop = (EditText) findViewById(R.id.editEditLongitudeShop);
        editEditLatitudeShop = (EditText) findViewById(R.id.editEditLatitudeShop);
        Toast.makeText(this, idShop.toString(), Toast.LENGTH_SHORT).show();

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
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, PICK_IMAGE);
        }
    }

    private boolean editShop() throws ExecutionException, InterruptedException {
        // Toast.makeText(this, idShop.toString(), Toast.LENGTH_SHORT).show();
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
        AsyncTask<String, Void, ArrayList<Shop>> task2 = new RetrieveShopsTask().execute("sta god");
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