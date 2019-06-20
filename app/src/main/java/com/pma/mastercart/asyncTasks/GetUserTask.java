package com.pma.mastercart.asyncTasks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OfflineActivity;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.UserDTO;
import com.pma.mastercart.model.User;

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

public class GetUserTask extends AsyncTask<String, Void, User> {
    private boolean valid;
    @Override
    protected User doInBackground(String... token) {
        valid = true;
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(3000);
        HttpHeaders requestHeaders = new HttpHeaders();

        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Authorization", token[0]);

        // Populate the Message object to serialize and headers in an
        // HttpEntity object to use for the request
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<User> response = null;
        User u = null;
        try {
           response = restTemplate.exchange(MainActivity.URL + "loggedUser", HttpMethod.GET, requestEntity,
                    User.class);
        }catch (RestClientException e){
            valid=false;
            return u;
        }
        if(response.getStatusCode()== HttpStatus.OK)
            u = response.getBody();
        return u;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        if(!valid){
            Intent homepage = new Intent(MainActivity.appContext, OfflineActivity.class);
            homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.appContext.startActivity(homepage);
        }
    }
}
