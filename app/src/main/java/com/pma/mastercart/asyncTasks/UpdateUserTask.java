package com.pma.mastercart.asyncTasks;

import android.content.Intent;
import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OfflineActivity;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.EditUserDTO;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class UpdateUserTask extends AsyncTask<Object, Void, User> {
    private boolean valid;
    @Override
    protected User doInBackground(Object... objects) {
        valid = true;
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        HttpHeaders requestHeaders = new HttpHeaders();


        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Authorization", objects[1].toString());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        //body.add("name", strings[0]);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(objects[0], requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<User> response = null;
        User user = new User();
        try {
            response = restTemplate.exchange(MainActivity.URL+"user", HttpMethod.PUT, httpEntity, User.class);
        }catch (RestClientException e){
            valid=false;
            return user;
        }
        if(response.getStatusCode()== HttpStatus.OK)
            user = response.getBody();
        return user;
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
