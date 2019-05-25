package com.pma.mastercart.asyncTasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.DTO.UserDTO;
import com.pma.mastercart.model.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class GetUserTask extends AsyncTask<String, Void, User> {

    @Override
    protected User doInBackground(String... token) {
        HttpHeaders requestHeaders = new HttpHeaders();

        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Authorization", token[0]);

        // Populate the Message object to serialize and headers in an
        // HttpEntity object to use for the request
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<User> response = restTemplate.exchange(MainActivity.URL+"loggedUser", HttpMethod.GET, requestEntity,
                User.class);
        if(response.getBody()==null){
            return null;
        }
        User u = response.getBody();
        return u;
    }
}
