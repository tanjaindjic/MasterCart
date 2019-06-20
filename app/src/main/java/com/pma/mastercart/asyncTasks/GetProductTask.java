package com.pma.mastercart.asyncTasks;

import android.content.Intent;
import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OfflineActivity;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.Product;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class GetProductTask extends AsyncTask<Long, Void, Product> {
    private boolean valid;
    @Override
    protected Product doInBackground(Long... longs) {
        valid = true;
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        HttpHeaders requestHeaders = new HttpHeaders();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);

        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        Product product = new Product();
        ResponseEntity<Product> response = null;
        try {
        }catch (RestClientException e){
            valid=false;
            return product;
        }
        if(response.getStatusCode()== HttpStatus.OK)
            product = response.getBody();
        return product;
    }

    @Override
    protected void onPostExecute(Product product) {
        super.onPostExecute(product);
        if(!valid){
            Intent homepage = new Intent(MainActivity.appContext, OfflineActivity.class);
            homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.appContext.startActivity(homepage);
        }
    }
}
