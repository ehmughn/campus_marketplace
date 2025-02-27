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
import com.example.static_classes.EncodeImage;
import com.example.temporary_values.TemporaryAccountList;
import com.example.testproject2.PostActivity;
import com.example.testproject2.R;

import java.util.ArrayList;

public class UploadProductVariationsAdapter extends RecyclerView.Adapter<UploadProductVariationsAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Variation> variations;

    public UploadProductVariationsAdapter(Context context, ArrayList<Variation> variations) {
        this.context = context;
        this.variations = variations;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_upload_variations, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Variation variation = variations.get(position);
        holder.imageView_image.setImageBitmap(EncodeImage.decodeFromStringBlob(variation.getImage()));
        holder.textView_productName.setText(variation.getName());
        holder.textView_productPrice.setText("₱" + variation.getPrice());
        holder.textView_productStock.setText("Stocks: " + variation.getStock());
    }

    @Override
    public int getItemCount() {
        return variations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layout;
        public ImageView imageView_image;
        public TextView textView_productName;
        public TextView textView_productPrice;
        public TextView textView_productStock;

        public ViewHolder(View productVariationView) {
            super(productVariationView);
            layout = productVariationView.findViewById(R.id.uploadVariation_layout);
            imageView_image = productVariationView.findViewById(R.id.uploadVariations_imageView_productImage);
            textView_productName = productVariationView.findViewById(R.id.uploadVariations_textView_productName);
            textView_productPrice = productVariationView.findViewById(R.id.uploadVariations_textView_productPrice);
            textView_productStock = productVariationView.findViewById(R.id.uploadVariations_textView_productStock);
        }
    }
}
