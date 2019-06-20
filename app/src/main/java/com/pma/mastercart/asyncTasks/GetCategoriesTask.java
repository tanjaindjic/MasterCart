package com.pma.mastercart.asyncTasks;

import android.content.Intent;
import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OfflineActivity;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.ShopDTO;
import com.pma.mastercart.model.Product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetCategoriesTask extends AsyncTask<Void, Void, ArrayList<Category>> {
    private boolean valid;
    @Override
    protected ArrayList<Category> doInBackground(Void... voids) {
        valid = true;
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Category[] categories = new Category[0];
        ResponseEntity<Category[]> response = null;
        try {
            response = restTemplate.getForEntity(MainActivity.URL+"category", Category[].class);
        }catch (RestClientException e){
            valid = false;
            return new ArrayList<>(Arrays.asList(categories));
        }
        if(response.getStatusCode()== HttpStatus.OK)
            categories = response.getBody();
        return new ArrayList<>(Arrays.asList(categories));
    }

    @Override
    protected void onPostExecute(ArrayList<Category> category) {
        super.onPostExecute(category);
        if(!valid){
            Intent homepage = new Intent(MainActivity.appContext, OfflineActivity.class);
            homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.appContext.startActivity(homepage);
        }
    }
}
