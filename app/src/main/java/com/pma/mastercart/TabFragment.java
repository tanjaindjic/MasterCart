package com.pma.mastercart;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pma.mastercart.adapter.CategoryAdapter;
import com.pma.mastercart.adapter.OnLoadDataListener;
import com.pma.mastercart.adapter.ProductAdapter;
import com.pma.mastercart.asyncTasks.CategorySortTask;
import com.pma.mastercart.asyncTasks.GetCategoriesTask;
import com.pma.mastercart.asyncTasks.RetrieveProductsTask;
import com.pma.mastercart.asyncTasks.RetrieveShopsTask;
import com.pma.mastercart.adapter.ShopAdapter;
import com.pma.mastercart.model.Category;
import com.pma.mastercart.model.DTO.ProductListDTO;
import com.pma.mastercart.model.DTO.ShopDTO;
import com.pma.mastercart.model.DTO.ShopListDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private ArrayList<Product> products = new ArrayList<>();
    private ProductAdapter productsAdapter;
    public static GridView gridView;
    public static GridView gridView2;
    private ShopAdapter shopAdapter;
    public static ArrayList<Category> categs;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Shop> shops = new ArrayList<>();


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
        gridView2 = (GridView) view.findViewById(R.id.shop_list_view);
        shopAdapter = new ShopAdapter(view.getContext(), MainActivity.shops.toArray(new Shop[MainActivity.shops.size()]));
        gridView2.setAdapter(shopAdapter);

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
            gridView2.setVisibility(View.GONE);
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
        sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0://best match
                        products = sortId();
                        break;
                    case 1://Price: Low
                        products = sortPriceLow();
                        break;
                    case 2://Price: High
                        products = sortPriceHigh();
                        break;
                    case 3://Rating
                        products = sortRating();
                        break;
                }
                ViewPager viewPager = (ViewPager)parent.getParent().getParent().getParent();
                LinearLayout v = (LinearLayout) viewPager.getChildAt(0);
                GridView gw = (GridView) v.getChildAt(1);
                productsAdapter = new ProductAdapter(MainActivity.appContext, products.toArray(new Product[products.size()]));
                gw.setAdapter(productsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        sort_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0://best match
                        shops = sortSId();
                        break;
                    case 1://Name Asc
                        shops = sortNameAsc();
                        break;
                    case 2://Name Desc
                        shops = sortNameDesc();
                        break;
                    case 3://Rating
                        shops = sortSRating();
                        break;
                }
           
                onLoad(new ShopListDTO(shops));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private ArrayList<Shop> sortSRating() {
        ArrayList<Shop> retVal = shops;
        Collections.sort(retVal, new Comparator<Shop>() {
            @Override
            public int compare(Shop s1, Shop s2) {
                return Double.compare(s2.getRating(), s1.getRating());
            }
        });
        return retVal;
    }

    private ArrayList<Shop> sortNameDesc() {
        ArrayList<Shop> retVal = shops;
        Collections.sort(retVal, new Comparator<Shop>() {
            @Override
            public int compare(Shop s1, Shop s2) {
                return s2.getName().compareTo(s1.getName());
            }
        });
        return retVal;
    }
    private ArrayList<Shop> sortNameAsc() {
        ArrayList<Shop> retVal = shops;
        Collections.sort(retVal, new Comparator<Shop>() {
            @Override
            public int compare(Shop s1, Shop s2) {
                return s1.getName().compareTo(s2.getName());
            }
        });
        return retVal;
    }

    private ArrayList<Shop> sortSId() {
        ArrayList<Shop> retVal = shops;
        Collections.sort(retVal, new Comparator<Shop>() {
            @Override
            public int compare(Shop s1, Shop s2) {
                return Long.compare(s1.getId(), s2.getId());
            }
        });
        return retVal;
    }

    private ArrayList<Product> sortId() {
        ArrayList<Product> retVal = products;
        Collections.sort(retVal, new Comparator<Product>() {
            @Override
            public int compare(Product c1, Product c2) {
                return Long.compare(c1.getId(), c2.getId());
            }
        });
        return retVal;
    }

    private ArrayList<Product> sortRating() {
        ArrayList<Product> retVal = products;
        Collections.sort(retVal, new Comparator<Product>() {
            @Override
            public int compare(Product c1, Product c2) {
                return Double.compare(c2.getRating(), c1.getRating());
            }
        });
        return retVal;
    }

    private ArrayList<Product> sortPriceHigh() {
        ArrayList<Product> retVal = products;
        Collections.sort(retVal, new Comparator<Product>() {
            @Override
            public int compare(Product c1, Product c2) {
                return Double.compare(c2.getPrice(), c1.getPrice());
            }
        });
        return retVal;
    }

    private ArrayList<Product> sortPriceLow() {
        ArrayList<Product> retVal = products;
        Collections.sort(retVal, new Comparator<Product>() {
            @Override
            public int compare(Product c1, Product c2) {
                return Double.compare(c1.getPrice(), c2.getPrice());
            }
        });
        return retVal;
    }

    private void addCategoriesOnSpinner() throws ExecutionException, InterruptedException {
        AsyncTask<Void, Void, ArrayList<Category>> task = new GetCategoriesTask().execute();
        categs = task.get();
        shops = MainActivity.shops;

        List<String> vrednosti = new ArrayList<String>();
        for(Category c0 : categs){
            vrednosti.add(c0.getName());
        }
        categoryAdapter = new CategoryAdapter(getContext(), android.R.layout.simple_spinner_item, vrednosti);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(categoryAdapter);

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                products = new ArrayList<>();
                if(!parent.getItemAtPosition(position).toString().equals(TabFragment.categs.get(0).getName())){

                    for(Product p : MainActivity.products)
                        if(p.getCategory().getName().equals(parent.getItemAtPosition(position).toString()))
                            products.add(p);

                }else
                    products = MainActivity.products;

                //debug na meta meta meta level
                ViewPager viewPager = (ViewPager)parent.getParent().getParent().getParent();
                LinearLayout v = (LinearLayout) viewPager.getChildAt(0);
                GridView gw = (GridView) v.getChildAt(1);
                productsAdapter = new ProductAdapter(MainActivity.appContext, products.toArray(new Product[products.size()]));
                gw.setAdapter(productsAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onLoad(Object data) {
        if(data instanceof ProductListDTO){
            products = ((ProductListDTO) data).getProducts();
            productsAdapter = new ProductAdapter(MainActivity.appContext, products.toArray(new Product[products.size()]));
            if(gridView!=null)
                gridView.setAdapter(productsAdapter);

        }else if(data instanceof ShopListDTO){
            shops = ((ShopListDTO) data).getShops();
            shopAdapter = new ShopAdapter(MainActivity.appContext, shops.toArray(new Shop[shops.size()]));
            if(gridView2!=null)
                gridView2.setAdapter(shopAdapter);

        }
    }
}