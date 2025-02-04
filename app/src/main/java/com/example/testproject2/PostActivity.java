package com.example.testproject2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.static_classes.ShowCurrentPost;

public class PostActivity extends AppCompatActivity {

    ImageView imageView_image;
    TextView textView_title;
    TextView textView_price;
    TextView textView_description;

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
        setValues();
    }

    private void setValues() {
        imageView_image.setImageResource(ShowCurrentPost.getImage());
        textView_title.setText(ShowCurrentPost.getTitle());
        textView_price.setText("₱" + ShowCurrentPost.getPrice());
        textView_description.setText(ShowCurrentPost.getDescription());
    }
}