package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.Order;

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

import java.util.ArrayList;
import java.util.List;

public class GetFavoriteIdsTask extends AsyncTask<Object, Void,  Boolean> {
    @Override
    protected Boolean doInBackground(Object... objects) {
        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Authorization", objects[0].toString());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        //body.add("name", strings[0]);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<Boolean> response = restTemplate.exchange(MainActivity.URL+"user/favorite/"+objects[1].toString(), HttpMethod.GET, httpEntity, Boolean.class);
        if(response.getBody()==null){
            return null;
        }
        Boolean retVal = response.getBody();
        return retVal;
    }
}
