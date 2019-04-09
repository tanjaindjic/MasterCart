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

public class CommentAdapter   extends BaseAdapter {

    private final Context mContext;
    private final Comment[] comments;

    public CommentAdapter(Context mContext, Comment[] comments) {
        this.mContext = mContext;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.length;
    }

    @Override
    public Object getItem(int position) {
        return comments[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Comment comment = comments[position];
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
}