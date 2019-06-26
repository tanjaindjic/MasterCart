package com.pma.mastercart.asyncTasks;

import android.content.Intent;
import android.os.AsyncTask;

import com.pma.mastercart.EditShopActivity;
import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OfflineActivity;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.ShopDTO;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class EditShopTask extends AsyncTask<ShopDTO, Void, ShopDTO> {
    private boolean valid;

    @Override
    protected ShopDTO doInBackground(ShopDTO... shopDTOS) {
        valid = true;
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        final String url = MainActivity.URL + "shop/edit";

        HttpHeaders requestHeaders = new HttpHeaders();

        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Populate the Message object to serialize and headers in an
        // HttpEntity object to use for the request
        HttpEntity<ShopDTO> requestEntity = new HttpEntity<ShopDTO>(shopDTOS[0]);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<ShopDTO> response = null;
        ShopDTO u =new ShopDTO();
        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,
                ShopDTO.class);
        }catch (RestClientException e){
            valid=false;
            return u;
        }
        if(response.getStatusCode()== HttpStatus.OK)
            u = response.getBody();
        return u;
    }

    @Override
    protected void onPostExecute(ShopDTO shopDTO) {
        super.onPostExecute(shopDTO);
        if(!valid){
            Intent homepage = new Intent(MainActivity.appContext, OfflineActivity.class);
            homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.appContext.startActivity(homepage);
        }else{
            EditShopActivity.onLoad(shopDTO.getId());
        }
    }
}
