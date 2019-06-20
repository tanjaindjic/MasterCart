package com.pma.mastercart.asyncTasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OfflineActivity;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.Comment;
import com.pma.mastercart.model.Product;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class AddCommentTask extends AsyncTask<Object, Void, Comment> {
    private boolean valid;
    @Override
    protected Comment doInBackground(Object... objects) {
        valid = true;
        HttpHeaders requestHeaders = new HttpHeaders();
        String token = objects[1].toString();

        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Authorization", token);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<?> httpEntity = new HttpEntity<Object>(objects[0], requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<Comment> response = null;
        Comment comment = null;
        try {
            response = restTemplate.exchange(MainActivity.URL+"comment", HttpMethod.POST, httpEntity, Comment.class);
        }catch (RestClientException e){
            valid = false;
            comment = new Comment();
            return comment;
        }
        if(response.getStatusCode()== HttpStatus.OK)
            comment = response.getBody();
        return comment;
    }

    @Override
    protected void onPostExecute(Comment comment) {
        super.onPostExecute(comment);
        if(!valid){
            Intent homepage = new Intent(MainActivity.appContext, OfflineActivity.class);
            homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.appContext.startActivity(homepage);
        }
    }
}
