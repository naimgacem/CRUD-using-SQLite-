package com.example.miniprojet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Post> {
    private Context context;
    private ArrayList<Post> posts;
    public CustomAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
        this.context = context;
        this.posts = posts;
    }
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView =
                    LayoutInflater.from(context).inflate(R.layout.activity_post, parent, false);
        }
        Post currentPost = posts.get(position);
        TextView usernameTextView =
                listItemView.findViewById(R.id.username_text_view);
        usernameTextView.setText(currentPost.getUsername());
        TextView postContentTextView =
                listItemView.findViewById(R.id.post_content_text_view);
        postContentTextView.setText(currentPost.getPostContent());




        return listItemView;
    }
}