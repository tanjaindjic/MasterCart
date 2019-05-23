package com.pma.mastercart;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pma.mastercart.adapter.ProductAdapter;
import com.pma.mastercart.adapter.RetrieveProductsTask;
import com.pma.mastercart.adapter.RetrieveShopsTask;
import com.pma.mastercart.adapter.ShopAdapter;
import com.pma.mastercart.model.DTO.ProductListDTO;
import com.pma.mastercart.model.DTO.ShopListDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TabFragment extends Fragment {

    int position;
    private TextView category;
    private TextView sort;
    private TextView sort2;
    private Spinner category_spinner;
    private Spinner sort_spinner;
    private Spinner sort_spinner2;
    private ProductAdapter productsAdapter;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Shop> shops = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReferenceFromUrl("https://mastercart-4c01a.firebaseio.com/");
    private DatabaseReference prodavnice = myRef.child("prodavnice");
    private DatabaseReference proizvodi = myRef.child("proizvodi");
    private ShopAdapter shopAdapter;
    private ProgressDialog progress;
    private GridView gridView;
    private ListView listView;


    public static Fragment getInstance(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView)view.findViewById(R.id.products_grid_view);
        listView = (ListView) view.findViewById(R.id.shop_list_view);
        productsAdapter = new ProductAdapter(getContext(), products.toArray(new Product[products.size()]));
        gridView.setAdapter(productsAdapter);
        shopAdapter = new ShopAdapter(view.getContext(), shops.toArray(new Shop[shops.size()]));
        listView.setAdapter(shopAdapter);
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Syncing with Database");
        progress.setCancelable(false);
        progress.show();

        try {
            loadData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        category = (TextView) view.findViewById(R.id.category);
        sort = (TextView) view.findViewById(R.id.sort);
        sort2 = (TextView) view.findViewById(R.id.sort2);
        category_spinner = (Spinner) view.findViewById(R.id.category_spinner);
        sort_spinner = (Spinner) view.findViewById(R.id.sort_spinner);
        sort_spinner2 = (Spinner) view.findViewById(R.id.sort_spinner2);


        if(position==0){
            listView.setVisibility(View.GONE);
            category.setVisibility(View.VISIBLE);
            sort.setVisibility(View.VISIBLE);
            sort2.setVisibility(View.GONE);
            category_spinner.setVisibility(View.VISIBLE);
            sort_spinner.setVisibility(View.VISIBLE);
            sort_spinner2.setVisibility(View.GONE);
        }else{
            gridView.setVisibility(View.GONE);
            category.setVisibility(View.GONE);
            sort.setVisibility(View.GONE);
            category_spinner.setVisibility(View.GONE);
            sort_spinner.setVisibility(View.GONE);
            sort2.setVisibility(View.VISIBLE);
            sort_spinner2.setVisibility(View.VISIBLE);

        }


    }

    private void loadData() throws ExecutionException, InterruptedException {
        AsyncTask<String, Void, ArrayList<Product>> task = new RetrieveProductsTask().execute("sta god");
        products = task.get();
        productsAdapter = new ProductAdapter(getContext(), products.toArray(new Product[products.size()]));
        gridView.setAdapter(productsAdapter);

        AsyncTask<String, Void, ArrayList<Shop>> task2 = new RetrieveShopsTask().execute("sta god");
        shops = task2.get();
        shopAdapter = new ShopAdapter(getContext(), shops.toArray(new Shop[shops.size()]));
        listView.setAdapter(shopAdapter);

        progress.dismiss();
    }
}