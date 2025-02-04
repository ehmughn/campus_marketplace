package com.example.testproject2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
    private Button button_detailsPressed;
    private Button button_detailsUnpressed;
    private Button button_reviewsPressed;
    private Button button_reviewsUnpressed;
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
        button_detailsUnpressed = findViewById(R.id.post_button_detailsUnpressed);
        button_detailsPressed = findViewById(R.id.post_button_detailsPressed);
        button_detailsUnpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDetails();
            }
        });
        button_reviewsUnpressed = findViewById(R.id.post_button_reviewsUnpressed);
        button_reviewsPressed = findViewById(R.id.post_button_reviewsPressed);
        button_reviewsUnpressed.setOnClickListener(new View.OnClickListener() {
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
        button_detailsPressed.setVisibility(View.VISIBLE);
        button_detailsUnpressed.setVisibility(View.INVISIBLE);
        button_reviewsPressed.setVisibility(View.INVISIBLE);
        button_reviewsUnpressed.setVisibility(View.VISIBLE);
    }

    private void toReviews() {
        layout_details.setVisibility(View.INVISIBLE);
        layout_reviews.setVisibility(View.VISIBLE);
        button_detailsPressed.setVisibility(View.INVISIBLE);
        button_detailsUnpressed.setVisibility(View.VISIBLE);
        button_reviewsPressed.setVisibility(View.VISIBLE);
        button_reviewsUnpressed.setVisibility(View.INVISIBLE);
    }
}