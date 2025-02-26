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
import com.example.static_classes.EncodeImage;
import com.example.static_classes.ShowCurrentPost;
import com.example.temporary_values.TemporaryAccountList;
import com.example.temporary_values.TemporaryPostList;
import com.example.testproject2.MainActivity;
import com.example.testproject2.PostActivity;
import com.example.testproject2.R;

import org.w3c.dom.Text;

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
//        if(position % 4 == 1 || position % 4 == 2)
//            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.main_yellow));
//        else
//            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.main_blue));
        holder.imageView_image.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getVariations().get(0).getImage()));
        holder.textView_price.setText("₱" + post.getDisplayPrice());
        holder.textView_title.setText(post.getTitle());
        holder.textView_description.setText(post.getDescription());
        holder.textView_sellerName.setText(post.getProduct().getAccount().getName());
        holder.imageView_profilePicture.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getAccount().getImage()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostActivity.class);
                context.startActivity(intent);
                ShowCurrentPost.setImage(post.getProduct().getVariations().get(0).getImage());
                ShowCurrentPost.setPrice(post.getDisplayPrice());
                ShowCurrentPost.setTitle(post.getTitle());
                ShowCurrentPost.setDescription(post.getDescription());
                ShowCurrentPost.setStockCount(post.getDisplayStock());
                ShowCurrentPost.setReviews(post.getReviews());
                ShowCurrentPost.setSeller_id(post.getProduct().getAccount().getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layout;
        public ImageView imageView_image;
        public TextView textView_price;
        public TextView textView_title;
        public TextView textView_description;
        public ImageView imageView_profilePicture;
        public TextView textView_sellerName;

        public ViewHolder(View postView) {
            super(postView);
            layout = postView.findViewById(R.id.homePost_layout);
            imageView_image = postView.findViewById(R.id.homePost_imageView_product_image);
            textView_price = postView.findViewById(R.id.homePost_textView_price);
            textView_title = postView.findViewById(R.id.homePost_textView_title);
            textView_description = postView.findViewById(R.id.homePost_textView_description);
            imageView_profilePicture = postView.findViewById(R.id.homePost_imageView_profile_picture);
            textView_sellerName = postView.findViewById(R.id.homePost_textView_seller_name);
        }
    }
}
