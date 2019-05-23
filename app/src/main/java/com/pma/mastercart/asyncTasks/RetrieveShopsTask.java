package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.model.DTO.ShopListDTO;
import com.pma.mastercart.model.Shop;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class RetrieveShopsTask extends AsyncTask<String, Void, ArrayList<Shop>> {

    private Shop[] shops;
    @Override
    protected ArrayList<Shop> doInBackground(String... strings) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<Shop[]> response = restTemplate.getForEntity("http://10.0.2.2:8096/shop", Shop[].class);
        shops = response.getBody();
        return new ArrayList<>(Arrays.asList(shops));
    }
}
