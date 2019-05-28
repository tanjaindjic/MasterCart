package com.pma.mastercart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pma.mastercart.R;
import com.pma.mastercart.model.Comment;

import java.util.ArrayList;

public class CommentAdapter   extends BaseAdapter {

    private final Context mContext;
    private ArrayList<Comment> comments;

    public CommentAdapter(Context mContext, ArrayList<Comment> comments) {
        this.mContext = mContext;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Comment comment = comments.get(position);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.comment_linearlayout, null);
        }

        final TextView nameTextView = (TextView)convertView.findViewById(R.id.user_name);
        final TextView commentTextView = (TextView)convertView.findViewById(R.id.user_comment);

        nameTextView.setText(comment.getUser());
        commentTextView.setText(comment.getComment());

        return convertView;
    }


    public void updateResults(ArrayList<Comment> commenti) {
        comments = commenti;
        notifyDataSetChanged();
    }
}