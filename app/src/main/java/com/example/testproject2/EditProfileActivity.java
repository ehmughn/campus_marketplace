package com.example.testproject2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.static_classes.CurrentAccount;
import com.example.static_classes.EncodeImage;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView imageView_profilePicture;
    private TextView textView_changePhoto;
    private EditText editText_firstName;
    private EditText editText_lastName;
    private EditText editText_bio;
    private EditText editText_username;
    private EditText editText_email;
    private TextView textView_changePassword;
    private Button button_cancel;
    private Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageView_profilePicture = findViewById(R.id.editProfile_imageView_profilePicture);
        textView_changePhoto = findViewById(R.id.editProfile_textView_changePhoto);
        editText_firstName = findViewById(R.id.editProfile_editText_firstName);
        editText_lastName = findViewById(R.id.editProfile_editText_lastName);
        editText_bio = findViewById(R.id.editProfile_editText_bio);
        editText_username = findViewById(R.id.editProfile_editText_username);
        editText_email = findViewById(R.id.editProfile_editText_email);
        textView_changePassword = findViewById(R.id.editProfile_textView_changePassword);
        button_cancel = findViewById(R.id.editProfile_button_cancel);
        button_save = findViewById(R.id.editProfile_button_save);

        imageView_profilePicture.setImageBitmap(EncodeImage.decodeFromStringBlob(CurrentAccount.getAccount().getImage()));
        editText_firstName.setText(CurrentAccount.getAccount().getFirstName());
        editText_lastName.setText(CurrentAccount.getAccount().getLastName());
        editText_bio.setText(CurrentAccount.getAccount().getBio());
        editText_username.setText(CurrentAccount.getAccount().getUsername());
        editText_email.setText(CurrentAccount.getAccount().getEmail());
    }
}