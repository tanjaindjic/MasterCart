package com.pma.mastercart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment {

    int position;
    private TextView category;
    private TextView sort;
    private AppCompatSpinner category_spinner;
    private AppCompatSpinner sort_spinner;

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
        category_spinner = (AppCompatSpinner) view.findViewById(R.id.category_spinner);
        sort_spinner = (AppCompatSpinner) view.findViewById(R.id.sort_spinner);

        if(position==0){
            category.setVisibility(View.VISIBLE);
            sort.setVisibility(View.VISIBLE);
            category_spinner.setVisibility(View.VISIBLE);
            sort_spinner.setVisibility(View.VISIBLE);
        }else{
            category.setVisibility(View.INVISIBLE);
            sort.setVisibility(View.INVISIBLE);
            category_spinner.setVisibility(View.INVISIBLE);
            sort_spinner.setVisibility(View.INVISIBLE);
        }


    }
}