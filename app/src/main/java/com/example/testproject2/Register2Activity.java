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

import com.example.static_classes.RegisterInfoHolder;

public class Register2Activity extends AppCompatActivity {

    private EditText editText_password;
    private EditText editText_corfirmPassword;
    private TextView textView_errorMessage;
    private Button button_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText_password = findViewById(R.id.register2_edittext_password);
        editText_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_errorMessage.setVisibility(View.INVISIBLE);
                }
            }
        });
        editText_corfirmPassword = findViewById(R.id.register2_edittext_confirmPassword);
        editText_corfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_errorMessage.setVisibility(View.INVISIBLE);
                }
            }
        });
        textView_errorMessage = findViewById(R.id.register2_edittext_error_message);
        button_signUp = findViewById(R.id.register2_button_signUp);
        button_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_errorMessage.setVisibility(View.VISIBLE);
                textView_errorMessage.setText("");
                editText_password.clearFocus();
                editText_corfirmPassword.clearFocus();
                String check_password = editText_password.getText().toString().trim();
                String check_confirmPassword = editText_corfirmPassword.getText().toString().trim();
                if(check_password.isEmpty() || check_confirmPassword.isEmpty()) {
                    textView_errorMessage.setText("Please fill up the fields.");
                    return;
                }
                if(!check_password.equals(check_confirmPassword)) {
                    textView_errorMessage.setText("Passwords do not match.");
                    return;
                }
                if(check_password.length() < 8) {
                    textView_errorMessage.setText("Password length must be greater than or equal to 8.");
                    return;
                }
                Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
                RegisterInfoHolder.setPassword(check_password);
                startActivity(intent);
                overridePendingTransition(R.anim.animate_slide_in_right, R.anim.animate_slide_out_left);
            }
        });
    }

    public void toSignIn(View v) {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.overridePendingTransition(R.anim.animate_slide_in_left, R.anim.animate_slide_out_right);
        finish();

    }
}