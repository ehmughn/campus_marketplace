package com.example.testproject2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.PostReviewAdapter;
import com.example.objects.Reviews;
import com.example.static_classes.ShowCurrentPost;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    private ImageView imageView_image;
    private TextView textView_title;
    private TextView textView_price;
    private TextView textView_description;
    private Button button_details;
    private Button button_reviews;
    private LinearLayout layout_details;
    private LinearLayout layout_reviews;
    private RecyclerView recyclerView_reviews;
    private PostReviewAdapter adapter_postReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageView_image = findViewById(R.id.post_imageView_image);
        textView_title = findViewById(R.id.post_textView_title);
        textView_price = findViewById(R.id.post_textView_price);
        textView_description = findViewById(R.id.post_textView_description);
        button_details = findViewById(R.id.post_button_details);
        button_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDetails();
            }
        });
        button_reviews = findViewById(R.id.post_button_reviews);
        button_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toReviews();
            }
        });
        layout_details = findViewById(R.id.post_layout_details);
        layout_reviews = findViewById(R.id.post_layout_reviews);
        recyclerView_reviews = findViewById(R.id.post_recyclerView_reviews);
        setValues();
    }

    private void setValues() {
        imageView_image.setImageResource(ShowCurrentPost.getImage());
        textView_title.setText(ShowCurrentPost.getTitle());
        textView_price.setText("₱" + ShowCurrentPost.getPrice());
        textView_description.setText(ShowCurrentPost.getDescription());
        adapter_postReview = new PostReviewAdapter(this, ShowCurrentPost.getReviews());
        recyclerView_reviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_reviews.setAdapter(adapter_postReview);
    }

    private void toDetails() {
        layout_details.setVisibility(View.VISIBLE);
        layout_reviews.setVisibility(View.INVISIBLE);
        button_details.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_corner));
//        button_details.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.main_yellow));
        button_details.setTextColor(ContextCompat.getColorStateList(this, R.color.main_blue));
        button_reviews.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_corner_hollow));
//        button_reviews.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.transparent));
        button_reviews.setTextColor(ContextCompat.getColorStateList(this, R.color.main_yellow));
    }

    private void toReviews() {
        layout_details.setVisibility(View.INVISIBLE);
        layout_reviews.setVisibility(View.VISIBLE);
        button_details.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_corner_hollow));
//        button_details.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.main_blue));
        button_details.setTextColor(ContextCompat.getColorStateList(this, R.color.main_yellow));
        button_reviews.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_corner));
//        button_reviews.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.main_yellow));
        button_reviews.setTextColor(ContextCompat.getColorStateList(this, R.color.main_blue));
    }
}