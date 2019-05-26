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

    private ProgressDialog pDialog;
    private Context context;

    public LoginUserTask(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Attempting login...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    @Override
    protected UserDTO doInBackground(UserDTO... userDTO) {
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
        ResponseEntity<UserDTO> response = restTemplate.exchange(MainActivity.URL+"login", HttpMethod.POST, requestEntity,
                UserDTO.class);
        if(response.getBody()==null){
            return null;
        }
        UserDTO u = response.getBody();

        SharedPreferences pref = context.getSharedPreferences(MainActivity.PREFS, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("AuthToken", u.getPassword());
        editor.apply();
        editor.commit();
        return u;
    }

    @Override
    protected void onPostExecute(UserDTO userDTO){
        pDialog.dismiss();
        pDialog.cancel();
    }
}
