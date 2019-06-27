package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.DTO.MessageDTO;
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

public class SendTask extends AsyncTask<MessageDTO, Void, Message> {
    @Override
    protected Message doInBackground(MessageDTO... messageDTOS) {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        final String url = MainActivity.URL + "conversation/message";

        HttpHeaders requestHeaders = new HttpHeaders();

        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Populate the Message object to serialize and headers in an
        // HttpEntity object to use for the request

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<MessageDTO> requestEntity = new HttpEntity<MessageDTO>(messageDTOS[0]);
        // Make the network request, posting the message, expecting a String in response from the server

        ResponseEntity<Message> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                    Message.class);
        }catch (RestClientException e){

        }
        return response.getBody();
    }
}
