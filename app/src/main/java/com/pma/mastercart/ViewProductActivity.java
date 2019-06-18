package com.pma.mastercart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pma.mastercart.adapter.CommentAdapter;
import com.pma.mastercart.adapter.ProductAdapter;
import com.pma.mastercart.asyncTasks.AddCommentTask;
import com.pma.mastercart.asyncTasks.AddToFavsTask;
import com.pma.mastercart.asyncTasks.GetFavoriteIdsTask;
import com.pma.mastercart.asyncTasks.GetProductTask;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.RemoveFromFavs;
import com.pma.mastercart.model.Comment;
import com.pma.mastercart.model.DTO.CommentDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class ViewProductActivity  extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Comment> comments;
    private ImageButton add_favorite;
    private ImageButton add_cart;
    private Product product;
    private ListView listView;
    private CommentAdapter commentAdapter;
    private TextView name;
    private TextView price;
    private TextView description;
    private RatingBar rating;
    private TextView categoryTextView;
    private TextView sizeTextView;
    private LinearLayout layout_AddComment;
    private EditText editText_add_comment;
    private Button button_sendComment;
    private Long id;
    private ImageView single_add_favorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_view);
        getIntent().removeExtra("productUpdate");
        Intent intent = getIntent();
        ArrayList parcelableList  = intent.getParcelableArrayListExtra("product");
        product = (Product) parcelableList.get(0);
        setTitle(product.getName());
        comments = (ArrayList<Comment>) product.getComments();
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
        categoryTextView = (TextView) findViewById(R.id.single_product_category1);
        categoryTextView.setText("Category: " + product.getCategory().getName());
        sizeTextView = (TextView) findViewById(R.id.single_product_size);
        sizeTextView.setText("Size: " + product.getSize());
        single_add_favorite = (ImageView) findViewById(R.id.single_add_favorite);
        //postavljanje favourite ikonice
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            Object[] objects = {sharedpreferences.getString("AuthToken", null), product.getId()};
            AsyncTask<Object, Void,  Boolean> task = new GetFavoriteIdsTask().execute(objects);
            try {
                Boolean longs= task.get();
                if(longs!= null)
                    if(longs)
                        single_add_favorite.setImageResource(R.drawable.ic_favorite);
            } catch (InterruptedException e) {

            } catch (ExecutionException e) {

            }
        }
        
        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        back_toolbar.setTitle(product.getName());

        layout_AddComment = (LinearLayout) findViewById(R.id.layout_AddComment);
        layout_AddComment.setVisibility(View.INVISIBLE);
        if (sharedpreferences.contains("AuthToken"))
            layout_AddComment.setVisibility(View.VISIBLE);
        editText_add_comment = (EditText) findViewById(R.id.add_comment);

        button_sendComment = (Button) findViewById(R.id.btn_send_comment);
        button_sendComment.setOnClickListener(this);



        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImageResource(), 0, product.getImageResource().length);
        ImageView pic = (ImageView)findViewById(R.id.single_product_thumbnail);
        pic.setImageBitmap(bitmap);


        add_favorite = (ImageButton) findViewById(R.id.single_add_favorite);
        add_favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
                if (sharedpreferences.contains("AuthToken")) {
                    Object[] objects = {product, sharedpreferences.getString("AuthToken", null)};
                    AsyncTask<Object, Void, String> task = new AddToFavsTask().execute(objects);
                    // The URL for making the POST request
                    try {
                        String resp = task.get();
                        if(resp.equals("done")){
                            single_add_favorite.setImageResource(R.drawable.ic_favorite);
                            Toast.makeText(view.getContext(), "Item added to favorites.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                    } catch (ExecutionException e) {
                    }
                }
                else {
                    Toast.makeText(view.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

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

    @Override
    public void onClick(View v) {
        if(v==button_sendComment){
            String commentText = editText_add_comment.getText().toString().trim();
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
            if(commentText.isEmpty() || !sharedpreferences.contains("AuthToken")){
                return;
            }
            CommentDTO comment = new CommentDTO((long) 0, null, product.getId(), commentText, 0, "");
            Object[] objects={comment, sharedpreferences.getString("AuthToken", null)};
            AsyncTask<Object, Void, Comment> task = new AddCommentTask().execute(objects);
            try {
                Comment done = task.get();
                ArrayList<Long> p = new ArrayList<>();
                if(done!=null) {
                    product.getComments().add(done);
                    commentAdapter.updateResults((ArrayList<Comment>) product.getComments());
                    p.add(product.getId());
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.removeExtra("productUpdate");
                    intent.putExtra("productUpdate", p);
                }
                editText_add_comment.setText("");
            } catch (InterruptedException e) {
                finish();
            } catch (ExecutionException e) {
                finish();
            }
        }
    }
}
