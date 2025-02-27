package com.example.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.objects.Post;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.Decimals;
import com.example.static_classes.EncodeImage;
import com.example.static_classes.ShowCurrentPost;
import com.example.testproject2.PostActivity;
import com.example.testproject2.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomePostsAdapter extends RecyclerView.Adapter<HomePostsAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Post> posts;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    public HomePostsAdapter(Context context, Activity activity, ArrayList<Post> posts) {
        this.context = context;
        this.activity = activity;
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
        holder.imageView_image.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getVariations().get(0).getImage()));
        holder.textView_price.setText("₱" + Decimals.FORMAT_PRICE.format(post.getDisplayPrice()));
        holder.textView_title.setText(post.getTitle());
        holder.textView_description.setText(post.getDescription());
        holder.textView_likeCount.setText(post.getLikeCount() + " liked this post.");
        holder.textView_variantName.setText("Variant: " + post.getProduct().getVariations().get(0).getName());
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
        if(post.isLikedByCurrentUser())
            holder.imageView_like.setImageResource(R.drawable.heart_fill);
        else
            holder.imageView_like.setImageResource(R.drawable.heart_hollow);
        holder.imageView_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(post.isLikedByCurrentUser())
                    unlikePost(holder, post);
                else
                    likePost(holder, post);
            }
        });
    }

    private void unlikePost(ViewHolder holder, Post post) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/unlike_post.php";

        RequestBody body = new FormBody.Builder()
                .add("post_id", Integer.toString(post.getId()))
                .add("liked_by_id", Integer.toString(CurrentAccount.getAccount().getId()))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    final String responseData = response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful() && responseData.contains("success")) {
                                post.setLikeCount(post.getLikeCount() - 1);
                                holder.setIsRecyclable(false);
                                holder.imageView_like.setImageResource(R.drawable.heart_hollow);
                                holder.textView_likeCount.setText(post.getLikeCount() + " liked this post.");
                                holder.setIsRecyclable(true);
                                post.setLikedByCurrentUser(false);
                            } else {
                                Toast.makeText(context, "Unliked Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void likePost(ViewHolder holder, Post post) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/like_post.php";

        RequestBody body = new FormBody.Builder()
                .add("post_id", Integer.toString(post.getId()))
                .add("liked_by_id", Integer.toString(CurrentAccount.getAccount().getId()))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    final String responseData = response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful() && responseData.contains("success")) {
                                post.setLikeCount(post.getLikeCount() + 1);
                                holder.setIsRecyclable(false);
                                holder.imageView_like.setImageResource(R.drawable.heart_fill);
                                holder.textView_likeCount.setText(post.getLikeCount() + " liked this post.");
                                holder.setIsRecyclable(true);
                                post.setLikedByCurrentUser(true);
                            } else {
                                Toast.makeText(context, "Liked Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
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
        public TextView textView_likeCount;
        public TextView textView_variantName;
        public ImageView imageView_profilePicture;
        public TextView textView_sellerName;
        public ImageView imageView_like;

        public ViewHolder(View postView) {
            super(postView);
            layout = postView.findViewById(R.id.homePost_layout);
            imageView_image = postView.findViewById(R.id.homePost_imageView_product_image);
            textView_price = postView.findViewById(R.id.homePost_textView_price);
            textView_title = postView.findViewById(R.id.homePost_textView_title);
            textView_description = postView.findViewById(R.id.homePost_textView_description);
            textView_likeCount = postView.findViewById(R.id.homePost_textView_likeCount);
            textView_variantName = postView.findViewById(R.id.homePost_textView_variant);
            imageView_profilePicture = postView.findViewById(R.id.homePost_imageView_profile_picture);
            textView_sellerName = postView.findViewById(R.id.homePost_textView_seller_name);
            imageView_like = postView.findViewById(R.id.homePost_imageView_like);
        }
    }
}
