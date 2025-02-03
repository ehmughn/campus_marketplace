package com.example.testproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button login_button_signIn;

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
        textView_error_message = findViewById(R.id.login_textview_errormessage);
        editText_email = findViewById(R.id.login_edittext_email);
        editText_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_error_message.setVisibility(View.INVISIBLE);
                }
            }
        });
        editText_password = findViewById(R.id.login_edittext_password);
        editText_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_error_message.setVisibility(View.INVISIBLE);
                }
            }
        });
        login_button_signIn = findViewById(R.id.login_button_signIn);
        login_button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_signIn();
            }
        });
    }

    public void toSignUp(View v) {
        Intent intent = new Intent(SignInActivity.this, Register1Activity.class);
        startActivity(intent);
//        this.overridePendingTransition(R.anim.animate_spin_enter, R.anim.animate_spin_exit);
    }

    public void finish(View v) {
        finish();
    }

    public void button_signIn() {
        editText_email.clearFocus();
        editText_password.clearFocus();

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