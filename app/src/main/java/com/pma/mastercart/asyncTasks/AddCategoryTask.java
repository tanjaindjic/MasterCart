package com.pma.mastercart.asyncTasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.UserDTO;

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

public class AddCategoryTask extends AsyncTask<String, Void, Category> {


    @Override
    protected Category doInBackground(String... strings) {
        HttpHeaders requestHeaders = new HttpHeaders();
        String token = strings[1];


        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Authorization", token);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("name", strings[0]);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<Category> response = restTemplate.exchange(MainActivity.URL+"category", HttpMethod.POST, httpEntity, Category.class);
        if(response.getBody()==null){
            return null;
        }
        Category category = response.getBody();
        return category;
    }
}
