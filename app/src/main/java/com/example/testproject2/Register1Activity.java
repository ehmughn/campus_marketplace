package com.example.testproject2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class Register1Activity extends AppCompatActivity {

    private EditText editText_email;
    private TextView textView_errorMessage;
    private Button button_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText_email = findViewById(R.id.register1_edittext_email);
        editText_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_errorMessage.setVisibility(View.INVISIBLE);
                }
            }
        });
        textView_errorMessage = findViewById(R.id.register1_edittext_error_message);
        button_continue = findViewById(R.id.register1_button_continue);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check_email = editText_email.getText().toString().trim();
                textView_errorMessage.setVisibility(View.VISIBLE);
                textView_errorMessage.setText("");
                editText_email.clearFocus();
                if(check_email.isEmpty()) {
                    textView_errorMessage.setText("Please fill up the fields.");
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(check_email).matches()) {
                    textView_errorMessage.setText("Invalid email format.");
                    return;
                }
                if(check_email.equals("admin@gmail.com")){
                    textView_errorMessage.setText("Email is already been used.");
                    return;
                }
                Intent intent = new Intent(Register1Activity.this, Register2Activity.class);
                RegisterInfoHolder.setEmail(check_email);
                startActivity(intent);
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