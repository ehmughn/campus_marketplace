package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.objects.Product;
import com.example.testproject2.R;

import java.util.ArrayList;

public class ChooseProductsProductAdapter extends RecyclerView.Adapter<ChooseProductsProductAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Product> products;
    private OnProductSelectedListener listener;

    public interface OnProductSelectedListener {
        void onProductSelected(Product product);
    }

    public ChooseProductsProductAdapter(Context context, ArrayList<Product> products, OnProductSelectedListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_chooseproduct_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.textView_productName.setText(product.getName());
        holder.chooseProductsVariationAdapter = new ChooseProductsVariationAdapter(context, product, product.getVariations(), productListener -> {
            listener.onProductSelected(product);
        });
        holder.recyclerView_chooseProductVariation.setAdapter(holder.chooseProductsVariationAdapter);
        holder.recyclerView_chooseProductVariation.setLayoutManager(new LinearLayoutManager(context));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductSelected(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layout;
        public TextView textView_productName;
        public RecyclerView recyclerView_chooseProductVariation;
        public ChooseProductsVariationAdapter chooseProductsVariationAdapter;

        public ViewHolder(View productView) {
            super(productView);
            layout = productView.findViewById(R.id.chooseProductProduct_layout);
            textView_productName = productView.findViewById(R.id.chooseProductProduct_textView_productName);
            recyclerView_chooseProductVariation = productView.findViewById(R.id.chooseProductProduct_recyclerView);
        }
    }
}
