package com.pma.mastercart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pma.mastercart.adapter.CommentAdapter;
import com.pma.mastercart.model.Comment;
import com.pma.mastercart.model.Shop;

import java.util.ArrayList;
import java.util.List;

public class ViewShopActivity extends AppCompatActivity {
    private Comment[] comments = new Comment[0];
    private ImageButton shop_location;
    private TextView name;
    private TextView address;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReferenceFromUrl("https://mastercart-4c01a.firebaseio.com/");
    private DatabaseReference prodavnice = myRef.child("prodavnice");
    private Shop shop;
    private ListView listView;
    private CommentAdapter commentAdapter;
    private RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_shop_view);

        Intent intent = getIntent();
        ArrayList parcelableList = intent.getParcelableArrayListExtra("shop");
        shop = (Shop) parcelableList.get(0);

        comments = shop.getComments().toArray(new Comment[shop.getComments().size()]);
        listView = (ListView) findViewById(R.id.shop_comments_list);
        commentAdapter = new CommentAdapter(this, comments);
        name = (TextView) findViewById(R.id.single_shop_name);
        name.setText(shop.getName());
        address = (TextView) findViewById(R.id.single_shop_address);
        address.setText(shop.getLocation());
        rating = (RatingBar) findViewById(R.id.single_shop_rating);
        rating.setRating((float) shop.getRating());
        //TODO ovo se ne prenosi

        listView.setAdapter(commentAdapter);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        back_toolbar.setTitle(shop.getName());

        ImageView pic = (ImageView) findViewById(R.id.single_shop_thumbnail); //TODO ucitati sliku
        pic.setImageResource(R.drawable.ic_shop);


        shop_location = (ImageButton) findViewById(R.id.single_shop_location);
        shop_location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //TODO proslediti pravu lokaciju prodavnice
                //open new activity to view location
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                startActivity(intent);
            }

        });

    }


    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
