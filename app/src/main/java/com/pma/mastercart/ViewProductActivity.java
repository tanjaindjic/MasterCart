package com.pma.mastercart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pma.mastercart.adapter.CommentAdapter;
import com.pma.mastercart.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class ViewProductActivity  extends AppCompatActivity {
    private Comment[] comments = {
            new Comment(1,1,"John Doe", "Super cool product!"),
            new Comment(2,1,"Amy Doe", "Looks nice."),
            new Comment(3,2,"Mary Doe", "Not a good product, disappointed."),
            new Comment(4,1,"Sam Doe", "My phone looks amazing now!"),

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_view);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        int singleProductId = intent.getIntExtra("PRODUCT_ID", -1);
        back_toolbar.setTitle(intent.getStringExtra("PRODUCT_NAME"));
        TextView name = (TextView) findViewById(R.id.single_product_name);
        name.setText(intent.getStringExtra("PRODUCT_NAME"));
        TextView price = (TextView) findViewById(R.id.single_product_price);
        price.setText(getResources().getString(intent.getIntExtra("PRODUCT_PRICE", -1)) + "$");
        ImageView pic = (ImageView) findViewById(R.id.single_product_thumbnail);
        pic.setImageResource(intent.getIntExtra("PRODUCT_PIC",-1));

        Comment[] singleProductCommentsArray = {};
        List<Comment> singleProductCommentsList = new ArrayList<>();
        for(Comment c : comments)
            if(c.getProductId()==singleProductId)
                singleProductCommentsList.add(c);

        singleProductCommentsArray = singleProductCommentsList.toArray(new Comment[singleProductCommentsList.size()]);
        ListView listView = (ListView) findViewById(R.id.comments_list);
        CommentAdapter commentAdapter = new CommentAdapter(this, singleProductCommentsArray);
        listView.setAdapter(commentAdapter);








    }
}
