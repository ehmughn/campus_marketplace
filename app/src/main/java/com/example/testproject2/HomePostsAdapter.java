package com.example.testproject2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomePostsAdapter extends RecyclerView.Adapter<HomePostsAdapter.ViewHolder> {

    private ArrayList<Post> posts;

    public HomePostsAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_home_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView_image;
        public TextView textView_price;
        public TextView textView_title;
        public TextView textView_stock;

        public ViewHolder(View postView) {
            super(postView);
            imageView_image = postView.findViewById(R.id.homePost_imageView_image);
            textView_price = postView.findViewById(R.id.homePost_textView_price);
            textView_title = postView.findViewById(R.id.homePost_textView_title);
            textView_stock = postView.findViewById(R.id.homePost_textView_stock);
        }
    }
}
