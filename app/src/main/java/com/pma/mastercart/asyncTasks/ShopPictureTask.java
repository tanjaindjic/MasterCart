package com.pma.mastercart.asyncTasks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import com.pma.mastercart.AddShopActivity;
import com.pma.mastercart.EditShopActivity;
import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OfflineActivity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShopPictureTask extends AsyncTask<Object, Void, Boolean> {
    private boolean valid;
    @Override
    protected Boolean doInBackground(Object... objects) {
        valid = true;
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);

        List<String> list = new ArrayList<>();
        list.add((String) objects[0]);
        list.add((String) objects[1]);
        HttpEntity<List<String>> requestEntity = new HttpEntity<>(list);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(MainActivity.URL+"uploadShop", HttpMethod.POST, requestEntity, String.class);
        }catch (RestClientException e){
            valid=false;
            return null;
        }
        if(response.getStatusCode()== HttpStatus.OK)
            return false;
        return true;
    }


    @Override
    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
        if(!valid){
            Intent homepage = new Intent(MainActivity.appContext, OfflineActivity.class);
            homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.appContext.startActivity(homepage);
        }
        if(AddShopActivity.dialog!=null)
            AddShopActivity.dialog.dismiss();
        if(EditShopActivity.dialog!=null)
            EditShopActivity.dialog.dismiss();

    }
}
