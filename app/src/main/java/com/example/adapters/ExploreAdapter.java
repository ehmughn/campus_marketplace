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
import com.example.objects.Variation;
import com.example.objects.VariationForExploreFragment;
import com.example.static_classes.EncodeImage;
import com.example.testproject2.PostActivity;
import com.example.testproject2.R;

import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {

    private Context context;

    private ArrayList<VariationForExploreFragment> variations;

    public ExploreAdapter(Context context, ArrayList<VariationForExploreFragment> variations) {
        this.context = context;
        this.variations = variations;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_explore, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VariationForExploreFragment variation = variations.get(position);
        holder.imageView_image.setImageBitmap(EncodeImage.decodeFromStringBlob(variation.getImage()));
        holder.imageView_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostActivity.class);
                intent.putExtra("postId", variation.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return variations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView_image;
        public ViewHolder(View postView) {
            super(postView);
            imageView_image = postView.findViewById(R.id.explore_imageView_image);
        }
    }
}
