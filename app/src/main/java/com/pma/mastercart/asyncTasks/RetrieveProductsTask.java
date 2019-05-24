package com.pma.mastercart.asyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.adapter.OnLoadDataListener;
import com.pma.mastercart.model.DTO.ProductListDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class RetrieveProductsTask extends AsyncTask<ProgressDialog, Void, ArrayList<Product>> {

    private Product[] products;


    @Override
    protected ArrayList<Product> doInBackground(ProgressDialog... progressDialogs) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<Product[]> response = restTemplate.getForEntity(MainActivity.URL+"product", Product[].class);
        products = response.getBody();
        progressDialogs[0].dismiss();
        return new ArrayList<>(Arrays.asList(products));
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        super.onPostExecute(products);
        ProductListDTO dto = new ProductListDTO(products);
        ((OnLoadDataListener) MainActivity.adapter.getItem(0)).onLoad(dto);
    }
}
