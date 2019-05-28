package com.pma.mastercart.asyncTasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.Category;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;

import okhttp3.MultipartBody;

public class ProfilePictureTask extends AsyncTask<Object, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Object... objects) {
        HttpHeaders requestHeaders = new HttpHeaders();
        String token = objects[1].toString();

        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        requestHeaders.add("Authorization", token);
        Bitmap mapa = (Bitmap) objects[0];
        String encodedImage = toBase64(mapa);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", mapa);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, requestHeaders);


        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<String> response = restTemplate.exchange(MainActivity.URL+"upload", HttpMethod.POST, requestEntity, String.class);
        if(response.getBody()==null){
            return false;
        }
        return true;
    }

    public String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
