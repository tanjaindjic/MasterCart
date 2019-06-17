package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class RetrieveShopListTask extends AsyncTask<Long, Void, Shop> {
    @Override
    protected Shop doInBackground(Long... longs) {
        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        //body.add("name", strings[0]);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(longs[0], requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<Shop> response = restTemplate.exchange(MainActivity.URL+"shop/list", HttpMethod.POST, httpEntity, Shop.class);
        if(response.getBody()==null){
            return null;
        }
        Shop shops = response.getBody();
        return shops;
    }
}
