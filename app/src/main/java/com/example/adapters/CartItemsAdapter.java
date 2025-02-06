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
import com.example.testproject2.PostActivity;
import com.example.testproject2.R;
import com.example.testproject2.databinding.TemplateAddtocartItemsBinding;

import java.util.ArrayList;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Post> items;

    public CartItemsAdapter(Context context, ArrayList<Post> items) {
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TemplateAddtocartItemsBinding templateAddtocartItemsBinding = TemplateAddtocartItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(templateAddtocartItemsBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post item = items.get(position);
        holder.binding.cartItemsImageViewProductImage.setImageResource(item.getImage());
        holder.binding.cartItemsTextViewProductName.setText(item.getTitle());
        holder.binding.cartItemsTextViewProductPrice.setText("₱" + item.getPrice());
        holder.binding.cartItemsImageViewProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TemplateAddtocartItemsBinding binding;

        public ViewHolder(TemplateAddtocartItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
