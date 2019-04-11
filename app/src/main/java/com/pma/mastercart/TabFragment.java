package com.pma.mastercart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pma.mastercart.adapter.ProductAdapter;
import com.pma.mastercart.adapter.ShopAdapter;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

public class TabFragment extends Fragment {

    int position;
    private TextView category;
    private TextView sort;
    private TextView sort2;
    private Spinner category_spinner;
    private Spinner sort_spinner;
    private Spinner sort_spinner2;
    private ProductAdapter productsAdapter;

    //za sad dok ne krenemo sa SQLite

    private Product[] products = {
            new Product(1, R.string.dummy1, R.drawable.ic_phone, R.string.dummyPrice),
            new Product(2, R.string.dummy2, R.drawable.ic_earphones, R.string.dummyPrice),
            new Product(3, R.string.dummy3, R.drawable.ic_microsd, R.string.dummyPrice),
            new Product(4, R.string.dummy3, R.drawable.ic_charger, R.string.dummyPrice),
            new Product(5, R.string.dummy3, R.drawable.ic_charger, R.string.dummyPrice),
            new Product(6, R.string.dummy3, R.drawable.ic_earphones, R.string.dummyPrice),
            new Product(7, R.string.dummy3, R.drawable.ic_phone, R.string.dummyPrice),
            new Product(8, R.string.dummy3, R.drawable.ic_phone, R.string.dummyPrice),

    };

    private Shop[] shops = {
            new Shop(1, R.string.dummy1, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(2, R.string.dummy2, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(3, R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(4, R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(5, R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(6, R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(7, R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),
            new Shop(8, R.string.dummy3, R.drawable.ic_dummy, R.string.dummyLocation),

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
        category_spinner = (Spinner) view.findViewById(R.id.category_spinner);
        sort_spinner = (Spinner) view.findViewById(R.id.sort_spinner);
        sort_spinner2 = (Spinner) view.findViewById(R.id.sort_spinner2);


        if(position==0){

            GridView gridView = (GridView)view.findViewById(R.id.products_grid_view);
            productsAdapter = new ProductAdapter(view.getContext(), products);
            gridView.setAdapter(productsAdapter);


            //OVO NE RADI AKO U GRID VIEW IMA NEKI DRUGI CLICKABLE VIEW
           /* gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product product = products[position];
                    //open new activity to view this product
                    Intent intent = new Intent(view.getContext(), ViewProductActivity.class);
                    intent.putExtra("PRODUCT_ID", String.valueOf(product.getId()));
                    intent.putExtra("PRODUCT_NAME", getResources().getString(product.getName()));
                    intent.putExtra("PRODUCT_PRICE", String.valueOf(product.getPrice()));
                    intent.putExtra("PRODUCT_PIC", String.valueOf(product.getImageResource()));
                    startActivity(intent);
                }
            });
*/

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