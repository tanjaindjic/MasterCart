package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.ShopDTO;
import com.pma.mastercart.model.Product;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetCategoriesTask extends AsyncTask<Void, Void, ArrayList<Category>> {
    @Override
    protected ArrayList<Category> doInBackground(Void... voids) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<Category[]> response = restTemplate.getForEntity(MainActivity.URL+"category", Category[].class);
        Category[] categories = response.getBody();
        return new ArrayList<>(Arrays.asList(categories));
    }
}
