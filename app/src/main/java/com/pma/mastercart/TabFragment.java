package com.pma.mastercart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

public class TabFragment extends Fragment {

    int position;
    private TextView category;
    private TextView sort;
    private TextView sort2;
    private AppCompatSpinner category_spinner;
    private AppCompatSpinner sort_spinner;
    private AppCompatSpinner sort_spinner2;
    private Product[] products = {
            new Product(R.string.dummy1, R.drawable.ic_dummy, R.string.dummyPrice),
            new Product(R.string.dummy2, R.drawable.ic_dummy, R.string.dummyPrice),
            new Product(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyPrice),
            new Product(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyPrice),
            new Product(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyPrice),
            new Product(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyPrice),
            new Product(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyPrice),
            new Product(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyPrice),

    };

    private Shop[] shops = {
            new Shop(R.string.dummy1, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(R.string.dummy2, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),

    };

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

        category = (TextView) view.findViewById(R.id.category);
        sort = (TextView) view.findViewById(R.id.sort);
        sort2 = (TextView) view.findViewById(R.id.sort2);
        category_spinner = (AppCompatSpinner) view.findViewById(R.id.category_spinner);
        sort_spinner = (AppCompatSpinner) view.findViewById(R.id.sort_spinner);
        sort_spinner2 = (AppCompatSpinner) view.findViewById(R.id.sort_spinner2);


        if(position==0){

            GridView gridView = (GridView)view.findViewById(R.id.products_grid_view);
            ProductAdapter productsAdapter = new ProductAdapter(view.getContext(), products);
            gridView.setAdapter(productsAdapter);


            category.setVisibility(View.VISIBLE);
            sort.setVisibility(View.VISIBLE);
            sort2.setVisibility(View.GONE);
            category_spinner.setVisibility(View.VISIBLE);
            sort_spinner.setVisibility(View.VISIBLE);
            sort_spinner2.setVisibility(View.GONE);
        }else{

            ListView listView = (ListView) view.findViewById(R.id.shop_list_view);
            ShopAdapter shopAdapter = new ShopAdapter(view.getContext(), shops);
            listView.setAdapter(shopAdapter);
            category.setVisibility(View.GONE);
            sort.setVisibility(View.GONE);
            category_spinner.setVisibility(View.GONE);
            sort_spinner.setVisibility(View.GONE);
            sort2.setVisibility(View.VISIBLE);
            sort_spinner2.setVisibility(View.VISIBLE);

        }


    }
}