package com.pma.mastercart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.adapter.CommentAdapter;
import com.pma.mastercart.asyncTasks.AddCommentTask;
import com.pma.mastercart.model.Comment;
import com.pma.mastercart.model.DTO.CommentDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ViewShopActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Comment> comments;
    private ImageButton shop_location;
    private TextView name;
    private TextView address;
    private Shop shop;
    private ListView listView;
    private CommentAdapter commentAdapter;
    private RatingBar rating;
    private EditText add_comment_shop;
    private Button shop_comment_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_shop_view);
        getIntent().removeExtra("shopUpdate");

        Intent intent = getIntent();
        ArrayList parcelableList = intent.getParcelableArrayListExtra("shop");
        shop = (Shop) parcelableList.get(0);

        comments = (ArrayList<Comment>) shop.getComments();
        listView = (ListView) findViewById(R.id.shop_comments_list);
        commentAdapter = new CommentAdapter(this, comments);
        name = (TextView) findViewById(R.id.single_shop_name);
        name.setText(shop.getName());
        address = (TextView) findViewById(R.id.single_shop_address);
        address.setText(shop.getLocation());
        rating = (RatingBar) findViewById(R.id.single_shop_rating);
        rating.setRating((float) shop.getRating());

        add_comment_shop = (EditText) findViewById(R.id.add_comment_shop);
        //TODO ovo se ne prenosi

        listView.setAdapter(commentAdapter);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        back_toolbar.setTitle(shop.getName());

        ImageView pic = (ImageView) findViewById(R.id.single_shop_thumbnail); //TODO ucitati sliku
        Bitmap bitmap = BitmapFactory.decodeByteArray(shop.getImageResource(), 0, shop.getImageResource().length);
        pic.setImageBitmap(bitmap);


        shop_location = (ImageButton) findViewById(R.id.single_shop_location);
        shop_location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //TODO proslediti pravu lokaciju prodavnice
                //open new activity to view location
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                intent.putExtra("lat", shop.getLat());
                intent.putExtra("lon", shop.getLng());
                startActivity(intent);
            }

        });

        shop_comment_button = (Button) findViewById(R.id.shop_comment_button);
        shop_comment_button.setOnClickListener(this);

    }


    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v==shop_comment_button){
            String commentText = add_comment_shop.getText().toString().trim();
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
            if(commentText.isEmpty() || !sharedpreferences.contains("AuthToken")){
                return;
            }
            CommentDTO comment = new CommentDTO((long) 0, shop.getId(), null, commentText, 0, "");
            Object[] objects={comment, sharedpreferences.getString("AuthToken", null)};
            AsyncTask<Object, Void, Comment> task = new AddCommentTask().execute(objects);
            try {
                Comment done = task.get();
                ArrayList<Shop> s = new ArrayList<>();
                if(done!=null) {
                    shop.getComments().add(done);
                    commentAdapter.updateResults((ArrayList<Comment>) shop.getComments());
                    s.add(shop);
                    getIntent().removeExtra("shop");
                    getIntent().putParcelableArrayListExtra("shop", s);
                }
                add_comment_shop.setText("");
                ArrayList<Long> ids = new ArrayList<>();
                ids.add(shop.getId());
                Intent intent = getIntent();
                intent.removeExtra("shopUpdate");
                intent.putExtra("shopUpdate", ids);
            } catch (InterruptedException e) {
                finish();
            } catch (ExecutionException e) {
                finish();
            }
        }
    }
}
