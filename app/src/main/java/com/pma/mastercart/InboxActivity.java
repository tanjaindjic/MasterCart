package com.pma.mastercart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pma.mastercart.adapter.ConversationAdapter;
import com.pma.mastercart.model.Conversation;
import com.pma.mastercart.model.Message;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class InboxActivity extends AppCompatActivity {


    /*User currentUser = new User("John", "Doe", "jd@gmail.com", "johndoe", "Vase Stajica 6", R.drawable.ic_dummy);
    Shop shop1 = new Shop(1, R.string.dummy1, R.drawable.ic_shop, R.string.dummyLocation);
    Shop shop2 = new Shop(2, R.string.dummy2, R.drawable.ic_shop, R.string.dummyLocation);
    Shop shop3 = new Shop(3, R.string.dummy3, R.drawable.ic_shop, R.string.dummyLocation);
    List<Message> messageList1 = new ArrayList<>(Arrays.asList(new Message(1, currentUser, "Hello", new Date(System.currentTimeMillis())),
            new Message(2, shop1, "Welcome to my shop!", new Date(System.currentTimeMillis()))));
    List<Message> messageList2 = new ArrayList<>(Arrays.asList(new Message(1, currentUser, "Hello", new Date(System.currentTimeMillis())),
            new Message(2, shop2, "Welcome to my shop!", new Date(System.currentTimeMillis()))));
    List<Message> messageList3 = new ArrayList<>(Arrays.asList(new Message(1, currentUser, "Hello", new Date(System.currentTimeMillis())),
            new Message(2, shop3, "Welcome to my shop!", new Date(System.currentTimeMillis()))));

    private Conversation[] conversations = {
            new Conversation(1, shop1 ,currentUser, messageList1),
            new Conversation(2, shop2, currentUser, messageList2),
            new Conversation(3, shop3, currentUser, messageList3),
    };*/
    private Conversation[] conversations; //TODO povuci sa firebase konverzacije za CurrentUser-a
    private ConversationAdapter conversationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_fragment);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView listView = (ListView) findViewById(R.id.conversation_list_view);
        conversationAdapter = new ConversationAdapter(this, conversations);
        listView.setAdapter(conversationAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //This is automatically generated by the OnItemClickListener, the parameters are shown as the default name says, so we can assign the "position" in the next progress.
                // TODO Auto-generated method stub
                Conversation selected = conversations[position];
                Intent intent = new Intent(view.getContext(), ConversationActivity.class);
                intent.putExtra("conversationPosition", position);
                startActivity(intent);
            }

        });
    }



}