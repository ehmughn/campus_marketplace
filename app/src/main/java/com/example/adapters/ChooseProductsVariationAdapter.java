package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.objects.Variation;
import com.example.static_classes.EncodeImage;
import com.example.testproject2.R;

import java.util.ArrayList;

public class ChooseProductsVariationAdapter extends RecyclerView.Adapter<ChooseProductsVariationAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Variation> variations;

    public ChooseProductsVariationAdapter(Context context, ArrayList<Variation> variations) {
        this.context = context;
        this.variations = variations;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_chooseproduct_variations, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Variation variation = variations.get(position);
        holder.imageView_variationImage.setImageBitmap(EncodeImage.decodeFromStringBlob(variation.getImage()));
        holder.textView_variationName.setText(variation.getName());
    }

    @Override
    public int getItemCount() {
        return variations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView_variationImage;
        public TextView textView_variationName;

        public ViewHolder(View variationView) {
            super(variationView);
            imageView_variationImage = variationView.findViewById(R.id.chooseProductVariation_imageView_variationImage);
            textView_variationName = variationView.findViewById(R.id.chooseProductVariation_textView_variationName);
        }
    }
}
