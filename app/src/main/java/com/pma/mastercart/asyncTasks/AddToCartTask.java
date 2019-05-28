package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.Product;

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

public class AddToCartTask extends AsyncTask<Object, Void, String> {


    @Override
    protected String doInBackground(Object... objects){
        String token = objects[1].toString();
        Product product = (Product) objects[0];
        HttpHeaders requestHeaders = new HttpHeaders();

        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Authorization", token);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<String> response = restTemplate.exchange(MainActivity.URL+"user/addToCart/"+product.getId(), HttpMethod.POST, httpEntity, String.class);
        if(response.getBody()==null){
            return "";
        }
        String str = response.getBody();
        return "done";
    }
}
