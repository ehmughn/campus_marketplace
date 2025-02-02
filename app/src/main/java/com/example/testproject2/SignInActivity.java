package com.example.testproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignInActivity extends AppCompatActivity {

    TextView textView_error_message;
    EditText editText_email;
    EditText editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView_error_message = findViewById(R.id.error_message);
        editText_email = findViewById(R.id.input_email);
        editText_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_error_message.setVisibility(View.INVISIBLE);
                }
            }
        });
        editText_password = findViewById(R.id.input_password);
        editText_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_error_message.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void toSignUp(View v) {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void finish(View v) {
        finish();
    }

    public void button_signIn(View v) {

        // temporary login information until we get to know how to deal with databases
        String temporary_email = "admin@gmail.com";
        String temporary_password = "admin";

        if(editText_email.getText().toString().equals(temporary_email) && editText_password.getText().toString().equals(temporary_password)) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            textView_error_message.setVisibility(View.VISIBLE);
        }
    }
}