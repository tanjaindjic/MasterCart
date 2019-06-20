package com.pma.mastercart.asyncTasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OfflineActivity;
import com.pma.mastercart.adapter.OnLoadDataListener;
import com.pma.mastercart.model.DTO.ShopListDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class RetrieveShopsTask extends AsyncTask<String, Void, ArrayList<Shop>> {

    private Shop[] shops;
    private boolean valid;

    @Override
    protected ArrayList<Shop> doInBackground(String... strings) {
        valid = true;
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        shops = new Shop[0];
        ResponseEntity<Shop[]> response = null;
        try {
            response = restTemplate.getForEntity(MainActivity.URL + "shop", Shop[].class);
        }catch (RestClientException e){
            valid=false;
            return new ArrayList<>(Arrays.asList(shops));
        }
        if(response.getStatusCode()== HttpStatus.OK)
            shops = response.getBody();
        return new ArrayList<>(Arrays.asList(shops));

    }
    @Override
    protected void onPostExecute(ArrayList<Shop> shops) {
        super.onPostExecute(shops);
        if(!valid){
            Intent homepage = new Intent(MainActivity.appContext, OfflineActivity.class);
            homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.appContext.startActivity(homepage);
        }
        else {
            ShopListDTO dto = new ShopListDTO(shops);
            ((OnLoadDataListener) MainActivity.adapter.getItem(1)).onLoad(dto);
        }
    }
}
