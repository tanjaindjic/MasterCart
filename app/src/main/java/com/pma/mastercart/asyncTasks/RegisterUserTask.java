package com.pma.mastercart.asyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.AddUserDTO;
import com.pma.mastercart.model.DTO.UserDTO;
import com.pma.mastercart.model.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RegisterUserTask extends AsyncTask<AddUserDTO, Void, User> {

    private Context context;

    public RegisterUserTask(Context context){
        this.context = context;
    }

    @Override
    protected User doInBackground(AddUserDTO... users) {
        HttpHeaders requestHeaders = new HttpHeaders();
        // Sending a JSON or XML object i.e. "application/json" or "application/xml"
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        //body.add("name", strings[0]);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpEntity<?> httpEntity = new HttpEntity<Object>(users[0], requestHeaders);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


        // Make the network request, posting the message, expecting a String in response from the server
        ResponseEntity<User> response = restTemplate.exchange(MainActivity.URL+"user", HttpMethod.POST, httpEntity, User.class);
        if(response.getBody()==null){
            return null;
        }
        User u = response.getBody();
        return u;

    }
}
