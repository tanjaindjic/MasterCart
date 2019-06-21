package com.pma.mastercart.asyncTasks;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;

import com.pma.mastercart.LoginActivity;
import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OfflineActivity;
import com.pma.mastercart.adapter.OnLoadDataListener;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.ProductListDTO;
import com.pma.mastercart.model.DTO.UserDTO;
import com.pma.mastercart.model.Product;

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

import java.util.ArrayList;

public class LoginUserTask extends AsyncTask<Object, Void, UserDTO> {

    private Context context;
    private boolean valid;

    public LoginUserTask(Context context){
        this.context = context;
    }

    @Override
    protected UserDTO doInBackground(Object... objects) {
        valid = true;
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        HttpHeaders requestHeaders = new HttpHeaders();
        UserDTO userDTO = (UserDTO) objects[0];
        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Populate the Message object to serialize and headers in an
        // HttpEntity object to use for the request
        HttpEntity<UserDTO> requestEntity = new HttpEntity<UserDTO>(userDTO);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<UserDTO> response = null;
        UserDTO u = new UserDTO();
        try {
            response = restTemplate.exchange(MainActivity.URL+"login", HttpMethod.POST, requestEntity,
                UserDTO.class);
        }catch (RestClientException e){
            valid=false;
            return u;
        }
        if(response.getStatusCode()== HttpStatus.OK)
            u = response.getBody();

        SharedPreferences pref = context.getSharedPreferences(MainActivity.PREFS, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("AuthToken", u.getPassword());
        editor.apply();
        editor.commit();
        return u;
    }

    @Override
    protected void onPostExecute(UserDTO sserDTO) {
        super.onPostExecute(sserDTO);
        if(!valid){
            Intent homepage = new Intent(MainActivity.appContext, OfflineActivity.class);
            homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MainActivity.appContext.startActivity(homepage);
        }
    }

}
