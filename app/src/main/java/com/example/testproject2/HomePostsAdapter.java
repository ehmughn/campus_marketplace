package com.example.testproject2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomePostsAdapter extends RecyclerView.Adapter<HomePostsAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Post> posts;

    public HomePostsAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
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
        if(position % 4 == 1 || position % 4 == 2)
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.main_blue));
        else
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.main_yellow));
        holder.imageView_image.setImageResource(post.getImage());
        holder.textView_price.setText(post.getPrice());
        holder.textView_title.setText(post.getTitle());
        holder.textView_stock.setText(post.getStockCount());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView imageView_image;
        public TextView textView_price;
        public TextView textView_title;
        public TextView textView_stock;

        public ViewHolder(View postView) {
            super(postView);
            cardView = postView.findViewById(R.id.homePost_cardView);
            imageView_image = postView.findViewById(R.id.homePost_imageView_image);
            textView_price = postView.findViewById(R.id.homePost_textView_price);
            textView_title = postView.findViewById(R.id.homePost_textView_title);
            textView_stock = postView.findViewById(R.id.homePost_textView_stock);
        }
    }
}
