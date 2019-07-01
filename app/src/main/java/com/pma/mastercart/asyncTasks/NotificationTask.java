package com.pma.mastercart.asyncTasks;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.R;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.User;
import com.pma.mastercart.service.NotificationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

public class NotificationTask extends AsyncTask<User, Void, Product[]> {
    @Override
    protected Product[] doInBackground(User... users) {
        String requested_method = "LoadBU";
        String bu_status = "1";
        Log.i("SERVICE", "USAO");

       if(users[0]!=null) {

           SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
           simpleClientHttpRequestFactory.setConnectTimeout(3000);
           RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
           restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
           restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
           ResponseEntity<Product[]> response = null;
           Product[] products = new Product[0];
           try {
               response = restTemplate.getForEntity(MainActivity.URL + "notification/" + users[0].getId(), Product[].class);

               Log.i("SERVICE", "DOBIO");
               if (response.getStatusCode() == HttpStatus.OK) {
                   products = response.getBody();

               }

           } catch (RestClientException e) {

           }
           return products;
       } else return null;

    }
}
