package com.pma.mastercart.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ListView;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.R;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.NotificationTask;
import com.pma.mastercart.model.Message;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.User;
import com.pma.mastercart.model.enums.Role;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationService extends Service {
    private User currentUser;
    public Handler handler = null;
    public static Runnable runnable = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        handler.post(runnable = new Runnable() {
            @Override
            public void run() {
                String requested_method = "LoadBU";
                String bu_status = "1";
                Log.i("SERVICE", "USAO");
                Product[] products = new Product[0];
                currentUser = null;
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {

                    Log.i("SERVICE", "IMA");
                    AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
                    try {
                        User user = task.get();
                        currentUser = user;
                        if(currentUser!=null)
                            if(user.getRole().equals(Role.KUPAC) && user.isNotifications())
                                products = new NotificationTask().execute(currentUser).get();
                            else return;
                        else return;
                    } catch (InterruptedException e) {
                        currentUser = null;
                    } catch (ExecutionException e) {
                        currentUser = null;
                    }

                    if(products!=null)
                        Log.i("SERVICE", "IMA " + products.length);
                    if (products.length > 0) {

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("MasterCart")
                                .setContentText(getText(products))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        notificationManager.notify(0, builder.build());

                    }
                }


                handler.postDelayed(runnable, 3000);
            }
        });
        /*
        handler.postDelayed(runnable, 15000);*/
    }

    @Override
    public boolean onUnbind(Intent intent) {

        Log.i("SERVICE", "UNBIND");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       /* handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                String requested_method = "LoadBU";
                String bu_status = "1";
                Log.i("SERVICE", "USAO");

                currentUser = null;
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {

                    Log.i("SERVICE", "IMA");
                    AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
                    // The URL for making the POST request
                    try {
                        User user = task.get();
                        currentUser = user;
                    } catch (InterruptedException e) {
                        currentUser = null;
                    } catch (ExecutionException e) {
                        currentUser = null;
                    }

                    SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
                    simpleClientHttpRequestFactory.setConnectTimeout(3000);
                    RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
                    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    ResponseEntity<Product[]> response = null;
                    Product[] products = new Product[0];
                    try {
                        response = restTemplate.getForEntity(MainActivity.URL + "notification/" + currentUser.getId(), Product[].class);

                        Log.i("SERVICE", "DOBIO");
                        if (response.getStatusCode() == HttpStatus.OK) {
                            Product[] proizvodi = response.getBody();

                            Log.i("SERVICE", "IMA " + proizvodi.length);
                            if (products.length > 0) {

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle("MasterCart")
                                        .setContentText(getText(products))
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                                NotificationManager notificationManager =
                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                                notificationManager.notify(0, builder.build());

                            }
                        }

                    }catch(RestClientException e){

                    }

                    return;

                }
                //return super.onStartCommand(intent, flags, startId);


                handler.postDelayed(runnable, 1000);
            }
        };

        handler.postDelayed(runnable, 1500);*/
        return START_STICKY;


    }

    private String getText(Product[] products) {
        String nazivi = "";
        if (products.length == 1) {
            return products[0].getName() + " is on sale!";
        } else {
            for (int i = 0; i < products.length - 1; i++) {
                nazivi += products[i].getName() + ", ";
            }
            nazivi += products[products.length - 1].getName();
        }
        nazivi += " are on sale!";
        return nazivi;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SERVICE", "DESTROYED");
    }
}
