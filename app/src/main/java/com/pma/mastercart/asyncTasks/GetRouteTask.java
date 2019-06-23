package com.pma.mastercart.asyncTasks;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.pma.mastercart.MainActivity;
import com.pma.mastercart.MapsActivity;
import com.pma.mastercart.maps.Legs;
import com.pma.mastercart.maps.MapsResponse;
import com.pma.mastercart.maps.Routes;
import com.pma.mastercart.maps.Steps;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class GetRouteTask extends AsyncTask<String, Void, MapsResponse> {

    @Override
    protected MapsResponse doInBackground(String... url) {

        URL requestUrl = null;
        try {
            requestUrl = new URL(url[0]);
            HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = null;

                InputStream inputStream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                Log.d("Test", buffer.toString());
                String response = buffer.toString();
                if(response.contains("error_message"))
                    return null;
                Gson gson = new Gson();
                return gson.fromJson(response, MapsResponse.class);
            }
            else {
                Log.i("ROUTE EXCEPTION", "Unsuccessful HTTP Response Code: " + responseCode);
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPostExecute(MapsResponse mapsResponse) {
        super.onPostExecute(mapsResponse);
        if(mapsResponse==null) {
            Log.e("MAPS ERROR", "Nije prosao zahtev");
            MapsActivity.progressDialog.dismiss();
            MapsActivity.alertDialog.show();
            return;
        }
        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;

        try {
            Routes ruta1 = mapsResponse.getRoutes()[0];
            Legs leggo1 = ruta1.getLegs()[0];
            points = new ArrayList<>();
            LatLng start = new LatLng(Double.parseDouble(leggo1.getStart_location().getLat()), Double.parseDouble(leggo1.getStart_location().getLng()));
            points.add(start);
            lineOptions = new PolylineOptions();


            // Fetching all the points in i-th route
            for (int j = 0; j < leggo1.getSteps().length; j++) {
                Steps next = (leggo1.getSteps())[j];
                LatLng end = new LatLng(Double.parseDouble(next.getEnd_location().getLat()), Double.parseDouble(next.getEnd_location().getLng()));

                points.add(end);
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(10);
            lineOptions.color(Color.RED);

            Log.d("onPostExecute", "onPostExecute lineoptions decoded");


            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                MapsActivity.mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
            MapsActivity.progressDialog.dismiss();
        }catch (Exception e){
            Log.e("MAPS ERROR", "Bad GoogleMaps response");
        }
    }


}