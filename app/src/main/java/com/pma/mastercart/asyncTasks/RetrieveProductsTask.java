package com.pma.mastercart.asyncTasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OfflineActivity;
import com.pma.mastercart.adapter.OnLoadDataListener;
import com.pma.mastercart.model.DTO.ProductListDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class RetrieveProductsTask extends AsyncTask<Long, Void, ArrayList<Product>> {

    private Product[] products;
    private boolean valid;


    @Override
    protected ArrayList<Product> doInBackground(Long... longs) {
        valid = true;
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        products = new Product[0];
        ResponseEntity<Product[]> response = null;
        try {
            if(longs[0]==-1L)
                response = restTemplate.getForEntity(MainActivity.URL + "product", Product[].class);
            else
                response = restTemplate.getForEntity(MainActivity.URL + "product/seller/" + longs[0], Product[].class);
        }catch (RestClientException e){
            valid=false;
            Log.e("OFFLINE", e.getMessage());
            return new ArrayList<>(Arrays.asList(products));
        }
        if(response.getStatusCode()== HttpStatus.OK)
            products = response.getBody();
        return new ArrayList<>(Arrays.asList(products));
    }


    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        super.onPostExecute(products);
        if(!valid){
            Intent homepage = new Intent(MainActivity.appContext, OfflineActivity.class);
            homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.appContext.startActivity(homepage);
        }
        else {
            ProductListDTO dto = new ProductListDTO(products);
            ((OnLoadDataListener) MainActivity.adapter.getItem(0)).onLoad(dto);
        }
    }
}
