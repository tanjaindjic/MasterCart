package com.pma.mastercart;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pma.mastercart.adapter.CategoryAdapter;
import com.pma.mastercart.adapter.OnLoadDataListener;
import com.pma.mastercart.adapter.ProductAdapter;
import com.pma.mastercart.asyncTasks.GetCategoriesTask;
import com.pma.mastercart.asyncTasks.RetrieveProductsTask;
import com.pma.mastercart.asyncTasks.RetrieveShopsTask;
import com.pma.mastercart.adapter.ShopAdapter;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.ProductListDTO;
import com.pma.mastercart.model.DTO.ShopListDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pma.mastercart.R.id.title;
import static com.pma.mastercart.R.layout.support_simple_spinner_dropdown_item;

public class TabFragment extends Fragment implements OnLoadDataListener {

    int position;
    private TextView category;
    private TextView sort;
    private TextView sort2;
    private Spinner category_spinner;
    private Spinner sort_spinner;
    private Spinner sort_spinner2;
    private ProductAdapter productsAdapter;
    public static GridView gridView;
    public static ListView listView;
    private ShopAdapter shopAdapter;
    private ArrayList<Category> categs;
    private CategoryAdapter categoryAdapter;


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

        gridView = (GridView) view.findViewById(R.id.products_grid_view);
        productsAdapter = new ProductAdapter(view.getContext(), MainActivity.products.toArray(new Product[MainActivity.products.size()]));
        gridView.setAdapter(productsAdapter);
        listView = (ListView) view.findViewById(R.id.shop_list_view);
        shopAdapter = new ShopAdapter(view.getContext(), MainActivity.shops.toArray(new Shop[MainActivity.shops.size()]));
        listView.setAdapter(shopAdapter);

        category = (TextView) view.findViewById(R.id.category);
        sort = (TextView) view.findViewById(R.id.sort);
        sort2 = (TextView) view.findViewById(R.id.sort2);
        category_spinner = (Spinner) view.findViewById(R.id.category_spinner);
        sort_spinner = (Spinner) view.findViewById(R.id.sort_spinner);
        sort_spinner2 = (Spinner) view.findViewById(R.id.sort_spinner2);

        try {
            addCategoriesOnSpinner();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    private void addCategoriesOnSpinner() throws ExecutionException, InterruptedException {
        AsyncTask<Void, Void, ArrayList<Category>> task = new GetCategoriesTask().execute();
        categs = task.get();

        List<String> vrednosti = new ArrayList<String>();
        vrednosti.add("ALL");
        for(Category c0 : categs){
            vrednosti.add(c0.getName());
        }
                //(this, android.R.layout.simple_spinner_item, vrednosti);
        categoryAdapter = new CategoryAdapter(getContext(), android.R.layout.simple_spinner_item, vrednosti);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(categoryAdapter);

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //TODO: na odabir kateogrije u spineru, prometiti prikaz proizvoda u skladu sa odabranom kategorijom
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void onLoad(Object data) {
        if(data instanceof ProductListDTO){
            ArrayList<Product> prds = ((ProductListDTO) data).getProducts();
            productsAdapter = new ProductAdapter(MainActivity.appContext, prds.toArray(new Product[prds.size()]));
            gridView.setAdapter(productsAdapter);
        }else if(data instanceof ShopListDTO){
            ArrayList<Shop> sps = ((ShopListDTO) data).getShops();
            shopAdapter = new ShopAdapter(MainActivity.appContext, sps.toArray(new Shop[sps.size()]));
            listView.setAdapter(shopAdapter);
        }
    }
}