package com.pma.mastercart.asyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pma.mastercart.MainActivity;
import com.pma.mastercart.TabFragment;
import com.pma.mastercart.adapter.OnLoadDataListener;
import com.pma.mastercart.model.DTO.ProductListDTO;
import com.pma.mastercart.model.Product;

import java.util.ArrayList;

public class CategorySortTask  extends AsyncTask<String, Void, ArrayList<Product>> {

    private ArrayList<Product> products;

    @Override
    protected ArrayList<Product> doInBackground(String... strings) {
        products = new ArrayList<>();
        if(!strings[0].equals(TabFragment.categs.get(0).getName())){

            for(Product p : MainActivity.products)
                if(p.getCategory().getName().equals(strings[0]))
                    products.add(p);

        }else {
            products = MainActivity.products;
        }
        return products;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        super.onPostExecute(products);
        ProductListDTO dto = new ProductListDTO(products);
        ((OnLoadDataListener) MainActivity.adapter.getItem(0)).onLoad(dto);
    }
}
