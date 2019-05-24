package com.pma.mastercart.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.DTO.UserDTO;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class LoginUserTask extends AsyncTask<UserDTO, Void, UserDTO> {

    @Override
    protected UserDTO doInBackground(UserDTO... userDTO) {
        final String url = MainActivity.URL + "login";

        HttpHeaders requestHeaders = new HttpHeaders();

        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Populate the Message object to serialize and headers in an
        // HttpEntity object to use for the request
        HttpEntity<UserDTO> requestEntity = new HttpEntity<UserDTO>(userDTO[0]);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                UserDTO.class);
        if(response.getBody()==null){
            return null;
        }
            UserDTO u = response.getBody();
        return u;
    }
}
