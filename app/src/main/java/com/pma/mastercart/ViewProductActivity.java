package com.pma.mastercart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.pma.mastercart.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ViewProductActivity  extends AppCompatActivity {
    private Comment[] comments = new Comment[0];
    private ImageButton add_favorite;
    private ImageButton add_cart;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReferenceFromUrl("https://mastercart-4c01a.firebaseio.com/");
    private DatabaseReference proizvodi = myRef.child("proizvodi");
    private Product product;
    private ListView listView;
    private CommentAdapter commentAdapter;
    private TextView name;
    private TextView price;
    private TextView description;
    private RatingBar rating;
    private TextView categoryTextView;
    private TextView sizeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_view);

        Intent intent = getIntent();
        ArrayList parcelableList  = intent.getParcelableArrayListExtra("product");
        product = (Product) parcelableList.get(0);

        //TODO ovo se ne prenosi
        comments = product.getComments().toArray(new Comment[product.getComments().size()]);
        listView = (ListView) findViewById(R.id.comments_list);
        commentAdapter = new CommentAdapter(this, comments);
        listView.setAdapter(commentAdapter);
        name = (TextView) findViewById(R.id.single_product_name);
        name.setText(product.getName());
        price = (TextView) findViewById(R.id.single_product_price);
        price.setText("Price: " + Double.toString(product.getPrice())+'$');
        description = (TextView) findViewById(R.id.single_product_description);
        description.setText("Description: " + product.getDescription());
        rating = (RatingBar) findViewById(R.id.single_product_rating);
        rating.setRating((float) product.getRating());
        comments = product.getComments().toArray(new Comment[product.getComments().size()]);
        categoryTextView = (TextView) findViewById(R.id.single_product_category);
        categoryTextView.setText("Category: " + product.getCategory().getName());
        sizeTextView = (TextView) findViewById(R.id.single_product_size);
        sizeTextView.setText("Size: " + product.getSize());
        
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        back_toolbar.setTitle(product.getName());


        //TODO skinuti pravu sliku
        /*ImageView pic = (ImageView) findViewById(R.id.single_product_thumbnail);
        pic.setImageResource(R.drawable.ic_dummy);*/

        add_favorite = (ImageButton) findViewById(R.id.single_add_favorite);
        add_favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Item added to favorites.", Toast.LENGTH_SHORT).show();
            }
        });
        add_cart = (ImageButton)findViewById(R.id.single_add_cart);
        add_cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Item added to cart.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
