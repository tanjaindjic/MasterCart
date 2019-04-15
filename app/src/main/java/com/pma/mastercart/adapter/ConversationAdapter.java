package com.pma.mastercart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pma.mastercart.R;
import com.pma.mastercart.model.Conversation;

public class ConversationAdapter extends BaseAdapter {

    private final Context mContext;
    private final Conversation[] conversations;
    private TextView shop_name_conversation;
    private TextView conversation_text;

    // 1
    public ConversationAdapter(Context context, Conversation[] conversations) {
        this.mContext = context;
        this.conversations = conversations;
    }

    // 2
    @Override
    public int getCount() {
        return conversations.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Conversation conversation = conversations[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.inbox_layout, null);
        }

        shop_name_conversation = (TextView)convertView.findViewById(R.id.shop_name_conversation);
        conversation_text = (TextView)convertView.findViewById(R.id.conversation_text);

        shop_name_conversation.setText(conversation.getReciever().getName());
        conversation_text.setText(conversation.getMessages().get(conversation.getMessages().size()-1).getMessage());

        return convertView;
    }


}