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
import com.example.objects.Reviews;
import com.example.static_classes.EncodeImage;
import com.example.static_classes.ShowCurrentPost;
import com.example.testproject2.PostActivity;
import com.example.testproject2.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PostReviewAdapter extends RecyclerView.Adapter<PostReviewAdapter.ViewHolder> {

    private Context context;
    private Post post;

    private ArrayList<Reviews> reviews;

    public PostReviewAdapter(Context context, Post post, ArrayList<Reviews> reviews) {
        this.context = context;
        this.post = post;
        this.reviews = reviews;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_post_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reviews review = reviews.get(position);
        holder.textView_name.setText(review.getName());
        switch(review.getStars()) {
            case 1:
                holder.imageView_star1.setImageResource(R.drawable.star_full);
                holder.imageView_star2.setImageResource(R.drawable.star_empty);
                holder.imageView_star3.setImageResource(R.drawable.star_empty);
                holder.imageView_star4.setImageResource(R.drawable.star_empty);
                holder.imageView_star5.setImageResource(R.drawable.star_empty);
                break;
            case 2:
                holder.imageView_star1.setImageResource(R.drawable.star_full);
                holder.imageView_star2.setImageResource(R.drawable.star_full);
                holder.imageView_star3.setImageResource(R.drawable.star_empty);
                holder.imageView_star4.setImageResource(R.drawable.star_empty);
                holder.imageView_star5.setImageResource(R.drawable.star_empty);
                break;
            case 3:
                holder.imageView_star1.setImageResource(R.drawable.star_full);
                holder.imageView_star2.setImageResource(R.drawable.star_full);
                holder.imageView_star3.setImageResource(R.drawable.star_full);
                holder.imageView_star4.setImageResource(R.drawable.star_empty);
                holder.imageView_star5.setImageResource(R.drawable.star_empty);
                break;
            case 4:
                holder.imageView_star1.setImageResource(R.drawable.star_full);
                holder.imageView_star2.setImageResource(R.drawable.star_full);
                holder.imageView_star3.setImageResource(R.drawable.star_full);
                holder.imageView_star4.setImageResource(R.drawable.star_full);
                holder.imageView_star5.setImageResource(R.drawable.star_empty);
                break;
            case 5:
                holder.imageView_star1.setImageResource(R.drawable.star_full);
                holder.imageView_star2.setImageResource(R.drawable.star_full);
                holder.imageView_star3.setImageResource(R.drawable.star_full);
                holder.imageView_star4.setImageResource(R.drawable.star_full);
                holder.imageView_star5.setImageResource(R.drawable.star_full);
                break;
        }
        holder.textView_variation.setText("Variation: " + review.getVariation());
        holder.textView_comment.setText(review.getComment());
        holder.imageView_picture1.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getVariations().get(0).getImage()));
        holder.imageView_picture2.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getVariations().get(0).getImage()));
        holder.textView_helpful.setText("Helpful(" + review.getHelpful_likes() + ")");
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layout;
        public TextView textView_name;
        public ImageView imageView_star1;
        public ImageView imageView_star2;
        public ImageView imageView_star3;
        public ImageView imageView_star4;
        public ImageView imageView_star5;
        public TextView textView_variation;
        public TextView textView_comment;
        public ImageView imageView_picture1;
        public ImageView imageView_picture2;
        public TextView textView_helpful;
        public  ImageView imageView_like;

        public ViewHolder(View reviewView) {
            super(reviewView);
            layout = reviewView.findViewById(R.id.postReview_layout);
            textView_name = reviewView.findViewById(R.id.postReview_textView_name);
            imageView_star1 = reviewView.findViewById(R.id.postReview_imageView_star1);
            imageView_star2 = reviewView.findViewById(R.id.postReview_imageView_star2);
            imageView_star3 = reviewView.findViewById(R.id.postReview_imageView_star3);
            imageView_star4 = reviewView.findViewById(R.id.postReview_imageView_star4);
            imageView_star5 = reviewView.findViewById(R.id.postReview_imageView_star5);
            textView_variation = reviewView.findViewById(R.id.postReview_textView_variation);
            textView_comment = reviewView.findViewById(R.id.postReview_textView_comment);
            imageView_picture1 = reviewView.findViewById(R.id.postReview_imageView_picture1);
            imageView_picture2 = reviewView.findViewById(R.id.postReview_imageView_picture2);
            textView_helpful = reviewView.findViewById(R.id.postReview_textView_helpful);
            imageView_like = reviewView.findViewById(R.id.postReview_imageView_like);
        }
    }
}
