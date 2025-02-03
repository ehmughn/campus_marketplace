package com.example.testproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.static_classes.RegisterInfoHolder;

public class Register3Activity extends AppCompatActivity {

    private EditText editText_firstName;
    private EditText editText_lastName;
    private TextView textView_errorMessage;
    private Button button_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText_firstName = findViewById(R.id.register3_edittext_firstName);
        editText_firstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_errorMessage.setVisibility(View.INVISIBLE);
                }
            }
        });
        editText_lastName = findViewById(R.id.register3_edittext_lastName);
        editText_lastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_errorMessage.setVisibility(View.INVISIBLE);
                }
            }
        });
        textView_errorMessage = findViewById(R.id.register3_edittext_error_message);
        button_next = findViewById(R.id.register3_button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_errorMessage.setVisibility(View.VISIBLE);
                textView_errorMessage.setText("");
                editText_firstName.clearFocus();
                editText_lastName.clearFocus();
                String check_firstName = editText_firstName.getText().toString().trim();
                String check_lastName = editText_lastName.getText().toString().trim();
                if(check_firstName.isEmpty() || check_lastName.isEmpty()) {
                    textView_errorMessage.setText("Please fill up the fields.");
                    return;
                }
                Intent intent = new Intent(Register3Activity.this, Register4Activity.class);
                RegisterInfoHolder.setFirstName(check_firstName);
                RegisterInfoHolder.setLastName(check_lastName);
                startActivity(intent);
                overridePendingTransition(R.anim.animate_slide_in_right, R.anim.animate_slide_out_left);
            }
        });
    }
}