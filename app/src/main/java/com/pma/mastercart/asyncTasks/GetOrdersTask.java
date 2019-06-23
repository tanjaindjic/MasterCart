package com.pma.mastercart.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.OrdersActivity;
import com.pma.mastercart.adapter.OnLoadDataListener;
import com.pma.mastercart.adapter.OrdersAdapter;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.Order;
import com.pma.mastercart.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class GetOrdersTask extends AsyncTask<Long, Void, Order[]> {

    private Context mContext;

    public GetOrdersTask (Context context){
        mContext = context;
    }

    @Override
    protected Order[] doInBackground(Long... id) {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Order[] orders = new Order[0];
        ResponseEntity<Order[]> response = null;
        try {
            response = restTemplate.getForEntity(MainActivity.URL+"orders/" + id[0], Order[].class);
        }catch (RestClientException e){

            return orders;
        }
        if(response.getStatusCode()== HttpStatus.OK)
            orders = response.getBody();
        return orders;
    }

    @Override
    protected void onPostExecute(Order[] orders) {
        super.onPostExecute(orders);/*
        ((OnLoadDataListener) OrdersActivity.ordersAdapter.getItem(0)).onLoad(orders);*/
        OrdersActivity.ordersAdapter = new OrdersAdapter(mContext, orders);
        OrdersActivity.listView.setAdapter(OrdersActivity.ordersAdapter);
    }
}
