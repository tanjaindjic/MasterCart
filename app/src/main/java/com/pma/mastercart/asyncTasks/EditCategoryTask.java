package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.Category;

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

public class EditCategoryTask extends AsyncTask<Object, Void, Category> {
    @Override
    protected Category doInBackground(Object... objects) {
        HttpHeaders requestHeaders = new HttpHeaders();


        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Authorization", objects[1].toString());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        //body.add("name", strings[0]);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(objects[0], requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<Category> response = restTemplate.exchange(MainActivity.URL+"category", HttpMethod.PUT, httpEntity, Category.class);
        if(response.getBody()==null){
            return null;
        }
        Category category = response.getBody();
        return category;
    }
}
