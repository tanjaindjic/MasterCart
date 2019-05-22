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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.single_product_view);
        Intent intent = getIntent();
        String singleProductId = intent.getStringExtra("PRODUCT_ID");

        listView = (ListView) findViewById(R.id.comments_list);
        commentAdapter = new CommentAdapter(this, comments);
        listView.setAdapter(commentAdapter);

        getFirebaseProduct(singleProductId);
        super.onCreate(savedInstanceState);


        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        back_toolbar.setTitle(intent.getStringExtra("PRODUCT_NAME"));
        TextView name = (TextView) findViewById(R.id.single_product_name);
        name.setText(intent.getStringExtra("PRODUCT_NAME"));
        TextView price = (TextView) findViewById(R.id.single_product_price);
        price.setText(Double.toString(intent.getDoubleExtra("PRODUCT_PRICE", -1)) + "$");
        ImageView pic = (ImageView) findViewById(R.id.single_product_thumbnail);
       // pic.setImageResource(intent.getIntExtra("PRODUCT_PIC",-1));

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

    private void getFirebaseProduct(final String singleProductId) {
        proizvodi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren())
                    if (item.getKey()==singleProductId) {
                        product = item.getValue(Product.class);
                        comments = (Comment[]) product.getComments().toArray();
                    }

                commentAdapter= new CommentAdapter(getApplicationContext(), comments);
                listView.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
