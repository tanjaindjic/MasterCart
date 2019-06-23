package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OrdersActivity;
import com.pma.mastercart.model.Comment;
import com.pma.mastercart.model.DTO.ReviewDTO;

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

public class SendReviewTask  extends AsyncTask<ReviewDTO, Void, String> {
    @Override
    protected String doInBackground(ReviewDTO... comments) {

    SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
    final String url = MainActivity.URL + "review";

    HttpHeaders requestHeaders = new HttpHeaders();

    // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

    // Populate the Message object to serialize and headers in an
    // HttpEntity object to use for the request
    HttpEntity<ReviewDTO> requestEntity = new HttpEntity<ReviewDTO>(comments[0]);

    // Create a new RestTemplate instance
    RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


    // Make the network request, posting the message, expecting a String in response from the server
    ResponseEntity<Boolean> response = null;
        try {
        response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                Boolean.class);
    }catch (RestClientException e){
        Log.e("REVIEW", "Error sending review");
        return "Failed to send your review.";
    }
        if(response.getStatusCode()== HttpStatus.OK)
        Log.i("REVIW", "SUccess");
        return "Review successfuly sent.";

}

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        OrdersActivity.alertDialog.setTitle("");
        OrdersActivity.alertDialog.setMessage(s);
        OrdersActivity.alertDialog.show();

    }
}
