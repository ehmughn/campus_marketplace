package com.example.testproject2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.static_classes.EncodeImage;

public class VisitProfileActivity extends AppCompatActivity {

    private ImageView imageView_profilePicture;
    private TextView textView_name;
    private TextView textView_bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageView_profilePicture = findViewById(R.id.visitProfile_imageView_profilePicture);
        textView_name = findViewById(R.id.visitProfile_textView_name);
        textView_bio = findViewById(R.id.visitProfile_textView_bio);

//        imageView_profilePicture.setImageBitmap(EncodeImage.decodeFromStringBlob(ShowCurrentProfile.getAccount().getImage()));
//        textView_name.setText(ShowCurrentProfile.getAccount().getName());
//        textView_bio.setText(ShowCurrentProfile.getAccount().getBio());

    }
}