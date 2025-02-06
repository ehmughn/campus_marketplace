package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.objects.Post;
import com.example.static_classes.ShowCurrentPost;
import com.example.temporary_values.TemporaryAccountList;
import com.example.testproject2.PostActivity;
import com.example.testproject2.R;

import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Post> posts;

    public ExploreAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_explore, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.imageView_image.setImageResource(post.getImage());
        holder.imageView_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostActivity.class);
                context.startActivity(intent);
                ShowCurrentPost.setImage(post.getImage());
                ShowCurrentPost.setPrice(Double.parseDouble(post.getPrice()));
                ShowCurrentPost.setTitle(post.getTitle());
                ShowCurrentPost.setDescription(post.getDescription());
                ShowCurrentPost.setStockCount(Integer.parseInt(post.getStockCount()));
                ShowCurrentPost.setReviews(post.getReviews());
                ShowCurrentPost.setSeller_id(post.getPoster_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView_image;
        public ViewHolder(View postView) {
            super(postView);
            imageView_image = postView.findViewById(R.id.explore_imageView_image);
        }
    }
}
