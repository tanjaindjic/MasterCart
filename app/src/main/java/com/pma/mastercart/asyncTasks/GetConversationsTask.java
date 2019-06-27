package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.Conversation;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetConversationsTask extends AsyncTask<String, Void, List<Conversation>> {
    private Conversation[] conversations;
    @Override
    protected List<Conversation> doInBackground(String... strings) {

        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(3000);
        HttpHeaders requestHeaders = new HttpHeaders();

        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Authorization", strings[0]);

        // Populate the Message object to serialize and headers in an
        // HttpEntity object to use for the request
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Conversation[]> response = null;
        conversations = new Conversation[0];
        try {
            response = restTemplate.exchange(MainActivity.URL + "conversation/getConversations", HttpMethod.GET, requestEntity,
                    Conversation[].class);
        }catch (RestClientException e){
            return new ArrayList<>(Arrays.asList(conversations));
        }

        conversations = response.getBody();
        return new ArrayList<>(Arrays.asList(conversations));
    }
}
