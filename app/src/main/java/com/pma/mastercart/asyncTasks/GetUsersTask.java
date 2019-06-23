package com.pma.mastercart.asyncTasks;

import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.model.User;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class GetUsersTask extends AsyncTask<Void, Void, ArrayList<User>> {
    @Override
    protected ArrayList<User> doInBackground(Void... voids) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<User[]> response = restTemplate.getForEntity(MainActivity.URL+"usersForWallet", User[].class);
        User[] users = response.getBody();
        return new ArrayList<>(Arrays.asList(users));
    }
}
