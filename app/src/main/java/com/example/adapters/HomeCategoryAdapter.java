package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.objects.Categories;
import com.example.objects.Post;
import com.example.testproject2.R;

import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Categories> categories;
    private int selected_position = 0;

    public HomeCategoryAdapter(Context context, ArrayList<Categories> categories) {
        this.context = context;
        this.categories = categories;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Categories category = categories.get(position);
        if(position == selected_position) {
            holder.layout_selected_true.setVisibility(View.VISIBLE);
            holder.textView_selected_true.setVisibility(View.VISIBLE);
            holder.layout_selected_false.setVisibility(View.INVISIBLE);
            holder.textView_selected_false.setVisibility(View.INVISIBLE);
        }
        else {
            holder.layout_selected_true.setVisibility(View.INVISIBLE);
            holder.textView_selected_true.setVisibility(View.INVISIBLE);
            holder.layout_selected_false.setVisibility(View.VISIBLE);
            holder.textView_selected_false.setVisibility(View.VISIBLE);
        }
        holder.textView_selected_true.setText(category.getName());
        holder.textView_selected_false.setText(category.getName());
        holder.layout_selected_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_position = position;
                notifyDataSetChanged();
            }
        });
        holder.textView_selected_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_position = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout layout;
        public LinearLayout layout_selected_true;
        public LinearLayout layout_selected_false;
        public TextView textView_selected_true;
        public TextView textView_selected_false;

        public ViewHolder(View categoryView) {
            super(categoryView);
            layout = categoryView.findViewById(R.id.homeCategory_layout);
            layout_selected_true = categoryView.findViewById(R.id.homeCategory_linearLayout_selected_true);
            layout_selected_false = categoryView.findViewById(R.id.homeCategory_linearLayout_selected_false);
            textView_selected_true = categoryView.findViewById(R.id.categories_selected_true_textview_text);
            textView_selected_false = categoryView.findViewById(R.id.categories_selected_false_textview_text);
        }
    }
}
