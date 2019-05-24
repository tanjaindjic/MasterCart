package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.DTO.ProductListDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class RetrieveProductsTask extends AsyncTask<String, Void, ArrayList<Product>> {

    private Product[] products;


    @Override
    protected ArrayList<Product> doInBackground(String... strings) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<Product[]> response = restTemplate.getForEntity(MainActivity.URL+"product", Product[].class);
        products = response.getBody();
        return new ArrayList<>(Arrays.asList(products));
    }
}
