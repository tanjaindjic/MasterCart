package com.pma.mastercart;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.asyncTasks.AddCategoryTask;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.ProfilePictureTask;
import com.pma.mastercart.asyncTasks.UpdateUserTask;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.EditUserDTO;
import com.pma.mastercart.model.User;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener  {
    private ImageButton edit_firstname;
    private ImageButton edit_lastname;
    private ImageButton edit_address;
    private ImageButton edit_phone;
    private ImageButton edit_pass;
    private ImageButton profile_thumbnail_btn;
    private TextView profile_firstName;
    private TextView profile_lastName;
    private TextView profile_address;
    private TextView profile_phone;
    private TextView profile_email;
    private TextView profile_password;
    private ImageView profile_thumbnail;
    private User user;
    private Context ctx;
    private Button saveChangesBtn;
    private static final int STORAGE_PERMITION_CODE = 123;
    private static final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    private Bitmap bitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        user = null;
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
            // The URL for making the POST request
            try {
                user= task.get();
                if(user==null)
                    finish();
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }
        this.getIntent().addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        setContentView(R.layout.profile_layout);
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        profile_firstName = (TextView) findViewById(R.id.profile_name);
        profile_lastName = (TextView) findViewById(R.id.profile_lastname);
        profile_address = (TextView) findViewById(R.id.profile_address);
        profile_email = (TextView) findViewById(R.id.profile_email);
        profile_password = (TextView) findViewById(R.id.profile_password);
        profile_phone = (TextView) findViewById(R.id.profile_phone);

        profile_thumbnail = (ImageView) findViewById(R.id.profile_thumbnail);
        profile_thumbnail.getLayoutParams().height = 400;
        profile_thumbnail.getLayoutParams().width = 400;

        profile_firstName.setText(user.getFirstName());
        profile_lastName.setText(user.getLastName());
        profile_address.setText(user.getAddress());
        profile_email.setText(user.getEmail());
        profile_password.setText(user.getPassword());
        profile_phone.setText(user.getPhone());

        profile_thumbnail_btn = (ImageButton) findViewById(R.id.profile_thumbnail_btn);
        profile_thumbnail_btn.setOnClickListener(this);

        saveChangesBtn = (Button) findViewById(R.id.btn_save_profile_changes);
        saveChangesBtn.setOnClickListener(this);

        edit_firstname = (ImageButton)findViewById(R.id.edit_profile_name_button);
        edit_firstname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("First Name:", user.getFirstName());
            }
        });

        edit_lastname = (ImageButton)findViewById(R.id.edit_profile_lastname_button);
        edit_lastname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("Last Name:", user.getLastName());
            }
        });

        edit_address = (ImageButton)findViewById(R.id.edit_profile_address_button);
        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("Address:", user.getAddress());
            }
        });

        edit_pass = (ImageButton)findViewById(R.id.edit_profile_password_button);
        edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("Password:", "");
            }
        });

        edit_phone = (ImageButton)findViewById(R.id.edit_profile_phone_button);
        edit_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("Phone:", user.getPhone());
            }
        });

    }

    private void updateData(String label, String value){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_change_profile_data, null);
        builder.setView(view);
        TextView labelaTeksta = (TextView) view.findViewById(R.id.new_profile_name_label);
        labelaTeksta.setText(label);
        EditText noviTekst = (EditText)  view.findViewById(R.id.new_profile_name);
        if(label.equals("Password:"))
            noviTekst.setTransformationMethod(PasswordTransformationMethod.getInstance());
        else
            noviTekst.setText(value);
        Button okButton = (Button) view.findViewById(R.id.button_OK_);
        Button cancelButton = (Button) view.findViewById(R.id.button_cancel);

        AlertDialog alert = builder.create();
        alert.show();
        requestStoragePermision();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (label){
                    case "Password:":
                        if(noviTekst.getText().toString().isEmpty()){
                            Toast.makeText(ctx, "Password can not be empty", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        profile_password.setText(noviTekst.getText());
                        break;
                    case "Address:":
                        profile_address.setText(noviTekst.getText());
                        break;
                    case "Last Name:":
                        profile_lastName.setText(noviTekst.getText());
                        break;
                    case "First Name:":
                        profile_firstName.setText(noviTekst.getText());
                        break;
                    case "Phone:":
                        profile_phone.setText(noviTekst.getText());
                        break;
                }
                alert.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v==saveChangesBtn){
            int resulet = 0;
            resulet = saveChanges();
            if(resulet==0){
                Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Something went wrong, changes not saved", Toast.LENGTH_SHORT).show();
        }
        if(v==profile_thumbnail_btn){
            showFileChooser();
        }

    }

    private void requestStoragePermision(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            return;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMITION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_thumbnail.setImageBitmap(bitmap);

                Uri selectedImageUri  = data.getData();
            //    String selectedPath = getPath(selectedImageUri);



                Uri selectedImage = data.getData();
                String wholeID = DocumentsContract.getDocumentId(selectedImage);
                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];
                String[] column = { MediaStore.Images.Media.DATA };
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";
                Cursor cursor = getContentResolver().
                        query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                column, sel, new String[]{ id }, null);
                String filePath = "";
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
              //  setImageFromIntent(filePath);



                int returnCode = uploadImage(filePath);
                Toast.makeText(this, "return code "+returnCode, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMITION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();

        }
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Select picture"), PICK_IMAGE_REQUEST);
    }

    private String getPath(Uri uri){
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String retVal = cursor.getString(column_index);
        return retVal;
    }

    private int uploadImage(String sourceFileUri){
        int day, month, year;
        int second, minute, hour;
        GregorianCalendar date = new GregorianCalendar();

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);

        String name=(hour+""+minute+""+second+""+day+""+(month+1)+""+year);
        String tag=name+".jpg";
        String fileName = sourceFileUri.replace(sourceFileUri,tag);

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        int serverResponseCode = 0;

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist :"+sourceFileUri);

            runOnUiThread(new Runnable() {
                public void run() {

                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(MainActivity.URL+"upload");

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);




                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {
                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" C:/wamp/wamp/www/uploads";
                           // Toast.makeText(this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                       // Toast.makeText(this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
            }
            return serverResponseCode;
    }

    private int saveChanges() {
        String firstName = profile_firstName.getText().toString().trim();
        String lastName = profile_lastName.getText().toString().trim();
        String address = profile_address.getText().toString().trim();
        String email = profile_email.getText().toString().trim();
        String password = profile_password.getText().toString().trim();
        String phone = profile_phone.getText().toString().trim();
        if(email.isEmpty() || password.isEmpty()){
            return 1;
        }
        EditUserDTO editUserDTO = new EditUserDTO(email, password, firstName, lastName, address, phone);
        SharedPreferences pref = this.getSharedPreferences(MainActivity.PREFS, 0); // 0 - for private mode
        Object[] objects = {editUserDTO,  pref.getString("AuthToken", "")};
        AsyncTask<Object, Void, User> task = new UpdateUserTask().execute(objects);
        User response = null;
        try {
            response = task.get();
            if(response==null){
                return 1;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }
}