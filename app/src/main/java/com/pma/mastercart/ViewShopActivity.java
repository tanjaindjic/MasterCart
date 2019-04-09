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

public class ViewShopActivity extends AppCompatActivity {
    private Comment[] comments = {
            new Comment(1,1,"John Doe", "Awesome store!"),
            new Comment(2,1,"Amy Doe", "Very good service."),
            new Comment(3,2,"Mary Doe", "Bad quality products, disappointed."),
            new Comment(4,1,"Sam Doe", "My phone looks amazing now!"),

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_shop_view);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        int singleShopId = intent.getIntExtra("SHOP_ID", -1);
        back_toolbar.setTitle(intent.getStringExtra("SHOP_NAME"));
        TextView name = (TextView) findViewById(R.id.single_shop_name);
        name.setText(intent.getStringExtra("SHOP_NAME"));
        TextView address = (TextView) findViewById(R.id.single_shop_address);
        address.setText(intent.getStringExtra("SHOP_ADDRESS"));
        ImageView pic = (ImageView) findViewById(R.id.single_shop_thumbnail);
        pic.setImageResource(R.drawable.ic_shop);

        Comment[] singleShopCommentsArray = {};
        List<Comment> singleShopCommentsList = new ArrayList<>();
        for(Comment c : comments)
            if(c.getItemId()==singleShopId)
                singleShopCommentsList.add(c);

        singleShopCommentsArray = singleShopCommentsList.toArray(new Comment[singleShopCommentsList.size()]);
        ListView listView = (ListView) findViewById(R.id.shop_comments_list);
        CommentAdapter commentAdapter = new CommentAdapter(this, singleShopCommentsArray);
        listView.setAdapter(commentAdapter);

    }

}