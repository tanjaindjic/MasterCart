package com.pma.mastercart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.adapter.CommentAdapter;
import com.pma.mastercart.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity  extends AppCompatActivity {
    private Comment[] comments;/* = { //TODO povuci sa firebase komentare za proizvod
            new Comment(1,1,"John Doe", "Super cool product!"),
            new Comment(2,1,"Amy Doe", "Looks nice."),
            new Comment(3,2,"Mary Doe", "Not a good product, disappointed."),
            new Comment(4,1,"Sam Doe", "My phone looks amazing now!"),

    };*/
    private ImageButton add_favorite;
    private ImageButton add_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_product_view);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String singleProductId = intent.getStringExtra("PRODUCT_ID");
        back_toolbar.setTitle(intent.getStringExtra("PRODUCT_NAME"));
        TextView name = (TextView) findViewById(R.id.single_product_name);
        name.setText(intent.getStringExtra("PRODUCT_NAME"));
        TextView price = (TextView) findViewById(R.id.single_product_price);
        price.setText(getResources().getString(intent.getIntExtra("PRODUCT_PRICE", -1)) + "$");
        ImageView pic = (ImageView) findViewById(R.id.single_product_thumbnail);
        pic.setImageResource(intent.getIntExtra("PRODUCT_PIC",-1));

       /* Comment[] singleProductCommentsArray = {};
        List<Comment> singleProductCommentsList = new ArrayList<>();
        for(Comment c : comments)
            if(c.getItemId()==singleProductId)
                singleProductCommentsList.add(c);

        singleProductCommentsArray = singleProductCommentsList.toArray(new Comment[singleProductCommentsList.size()]);*/
        ListView listView = (ListView) findViewById(R.id.comments_list);
        CommentAdapter commentAdapter = new CommentAdapter(this, comments);
        listView.setAdapter(commentAdapter);

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
