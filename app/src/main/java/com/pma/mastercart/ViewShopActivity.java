package com.pma.mastercart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.mastercart.adapter.CommentAdapter;
import com.pma.mastercart.asyncTasks.AddCommentTask;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.SendMessageTask;
import com.pma.mastercart.model.Comment;
import com.pma.mastercart.model.DTO.CommentDTO;
import com.pma.mastercart.model.DTO.ConversationDTO;
import com.pma.mastercart.model.Product;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;
import com.pma.mastercart.model.enums.Role;

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
    private EditText messageText;
    public static ImageButton message;
    private String messages;
    private String shopId;
    private LinearLayout comment_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_shop_view);
        getIntent().removeExtra("shopUpdate");

        comment_layout = (LinearLayout) findViewById(R.id.comment_layout);
        User user = null;
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
            // The URL for making the POST request
            try {
                user = task.get();
                if(user==null)
                    comment_layout.setVisibility(View.GONE);
                else if(user.getRole().equals(Role.ADMIN))
                    comment_layout.setVisibility(View.GONE);
            } catch (InterruptedException e) {
                finish();
            } catch (ExecutionException e) {
                finish();
            }
        } else {
            finish();
        }

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

        ImageView pic = (ImageView) findViewById(R.id.single_shop_thumbnail);
        if(shop.getImageResource()!=null)
            if(shop.getImageResource().length>0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(shop.getImageResource(), 0, shop.getImageResource().length);
                pic.setImageBitmap(bitmap);
            }else pic.setImageResource(R.drawable.ic_dummy);
        else pic.setImageResource(R.drawable.ic_dummy);

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


        message = (ImageButton) findViewById(R.id.message);
        if(user!=null)
            if(user.getRole().equals(Role.KUPAC))
                message.setVisibility(View.VISIBLE);
            else message.setVisibility(View.GONE);

        else message.setVisibility(View.GONE);
        message.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.message_layout, null);
                builder.setView(dialogView);
                builder.setCancelable(true);
                messageText = (EditText) dialogView.findViewById(R.id.messageText);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        messages = messageText.getText().toString();
                        shopId = shop.getId().toString();
                        sentMessage(view,messages,shopId);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }

        });




    }


    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    private void sentMessage(View v, String message, String shopId) {
        User currentUser = null;
        SharedPreferences sharedpreferences = v.getContext().getSharedPreferences(MainActivity.PREFS, 0);
        if (sharedpreferences.contains("AuthToken")) {
            AsyncTask<String, Void, User> task = new GetUserTask().execute(sharedpreferences.getString("AuthToken", null));
            // The URL for making the POST request
            try {
                User user= task.get();
                currentUser = user;
            } catch (InterruptedException e) {
                currentUser = null;
            } catch (ExecutionException e) {
                currentUser = null;
            }
        }
        ConversationDTO c = new ConversationDTO(shopId,message, currentUser.getEmail().toString());
        AsyncTask<ConversationDTO, Void, String> task= new SendMessageTask().execute(c);




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
