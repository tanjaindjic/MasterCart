package com.pma.mastercart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pma.mastercart.adapter.MessageListAdapter;
import com.pma.mastercart.asyncTasks.GetMessagesTask;
import com.pma.mastercart.asyncTasks.GetUserTask;
import com.pma.mastercart.asyncTasks.SendTask;
import com.pma.mastercart.model.Conversation;
import com.pma.mastercart.model.DTO.MessageDTO;
import com.pma.mastercart.model.Message;
import com.pma.mastercart.model.Shop;
import com.pma.mastercart.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConversationActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private String messagesString;
    private String conversationId;
    private Conversation con;
    private Button sendMessage;
    private EditText message;
 /*   User currentUser = new User("John", "Doe", "jd@gmail.com", "johndoe", "Vase Stajica 6", R.drawable.ic_dummy);
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
    private List<Message> messagesFromConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        Toolbar back_toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(back_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        conversationId = intent.getStringExtra("conversationId");
        //con = intent.getParcelableExtra("conversation");

        messagesFromConversation = new ArrayList<Message>();
        AsyncTask<String, Void, List<Message>> task = new GetMessagesTask().execute(conversationId);
        try {
            List<Message> listMessage = task.get();
            messagesFromConversation = listMessage;
        } catch (InterruptedException e) {
            messagesFromConversation = null;
        } catch (ExecutionException e) {
            messagesFromConversation = null;
        }

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, messagesFromConversation);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

        message = (EditText) findViewById(R.id.edittext_chatbox);
        sendMessage = (Button) findViewById(R.id.button_chatbox_send);
        sendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                User currentUser = null;
                SharedPreferences sharedpreferences = view.getContext().getSharedPreferences(MainActivity.PREFS, 0);
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
                messagesString = message.getText().toString();
                MessageDTO m = new MessageDTO();
                m.setInitiator(currentUser.getEmail());
                m.setContent(messagesString);
                m.setConversationId(conversationId);

                AsyncTask<MessageDTO, Void, Message> task = new SendTask().execute(m);
                try {
                    Message ms = task.get();
                    if(ms!=null) {
                        messagesFromConversation.add(ms);
                        mMessageAdapter.updateResults((ArrayList<Message>) messagesFromConversation);
                        message.setText("");

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }

        });
    }
}