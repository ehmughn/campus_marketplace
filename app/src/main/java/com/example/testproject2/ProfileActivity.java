package com.example.testproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    TextView textView_uploads;
    TextView textView_likes;
    GridLayout gridLayout_uploads;
    TextView textView_noLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView_uploads = findViewById(R.id.text_uploads);
        textView_likes = findViewById(R.id.text_likes);
        gridLayout_uploads = findViewById(R.id.profle_uploads);
        textView_noLikes = findViewById(R.id.profle_likes);
    }

    public void toUpload(View v) {
        Intent intent = new Intent(ProfileActivity.this, UploadActivity.class);
        startActivity(intent);
    }

    public void toUploads(View v) {
        textView_uploads.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_bold));
        textView_likes.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_regular));
        gridLayout_uploads.setVisibility(View.VISIBLE);
        textView_noLikes.setVisibility(View.INVISIBLE);
    }

    public void toLikes(View v) {
        textView_uploads.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_regular));
        textView_likes.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_bold));
        gridLayout_uploads.setVisibility(View.INVISIBLE);
        textView_noLikes.setVisibility(View.VISIBLE);
    }
}