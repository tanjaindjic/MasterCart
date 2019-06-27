package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.Message;

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

public class GetMessagesTask extends AsyncTask<String, Void, List<Message>> {
    private Message[] messages;

    @Override
    protected List<Message> doInBackground(String... strings) {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(3000);
        HttpHeaders requestHeaders = new HttpHeaders();

        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        // Populate the Message object to serialize and headers in an
        // HttpEntity object to use for the request
        HttpEntity<String> requestEntity = new HttpEntity<String>(requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<Message[]> response = null;
        messages = new Message[0];
        try {
            response = restTemplate.exchange(MainActivity.URL + "conversation/getMessage/"+strings[0], HttpMethod.GET, requestEntity,
                    Message[].class);
        }catch (RestClientException e){
            return new ArrayList<>(Arrays.asList(messages));
        }

        messages = response.getBody();
        return new ArrayList<>(Arrays.asList(messages));
    }
}
