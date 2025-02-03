package com.example.testproject2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.static_classes.RegisterInfoHolder;

import java.util.Random;

public class Register4Activity extends AppCompatActivity {

    private EditText editText_username;
    private TextView textView_errorMessage;
    private Button button_next;
    private LayoutInflater inflater;
    private View dialogView;
    private Button dialog_button_goToHome;
    private Button dialog_button_editProfile;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText_username = findViewById(R.id.register4_edittext_username);
        editText_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_errorMessage.setVisibility(View.INVISIBLE);
                }
            }
        });
        textView_errorMessage = findViewById(R.id.register4_edittext_error_message);
        button_next = findViewById(R.id.register4_button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_errorMessage.setVisibility(View.VISIBLE);
                textView_errorMessage.setText("");
                editText_username.clearFocus();
                String check_username = editText_username.getText().toString().trim();
                if(check_username.isEmpty()) {
                    textView_errorMessage.setText("Please fill up the fields.");
                    return;
                }
                if(check_username.matches("^[a-z][a-z0-9_]")) {
                    textView_errorMessage.setText("Username must start with a letter and only contain small letters, numbers, and \"_\".");
                    return;
                }
                if(check_username.length() < 6 || check_username.length() > 19) {
                    textView_errorMessage.setText("Username length must be 6 to 19");
                    return;
                }
                if(check_username.equals("eman_bawalan1")){
                    textView_errorMessage.setText("Username already taken.");
                    return;
                }
                dialog.show();
//                Intent intent = new Intent(Register4Activity.this, Register5Activity.class);
//                RegisterInfoHolder.setUsername(check_username);
//                startActivity(intent);
//                overridePendingTransition(R.anim.animate_slide_in_right, R.anim.animate_slide_out_left);
            }
        });
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_welcome, null);
        dialog_button_goToHome = dialogView.findViewById(R.id.dialog_button_goToHome);
        dialog_button_goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register4Activity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        dialog_button_editProfile = dialogView.findViewById(R.id.dialog_button_editProfile);
        builder = new AlertDialog.Builder(this);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void randomizeUsername(View v) {
        Random random = new Random();
        switch(random.nextInt(8)) {
            case 0:
                editText_username.setText(RegisterInfoHolder.getFirstName().toLowerCase() + RegisterInfoHolder.getLastName().toLowerCase());
                break;
            case 1:
                editText_username.setText(RegisterInfoHolder.getFirstName().toLowerCase() + RegisterInfoHolder.getLastName().toLowerCase() + random.nextInt(1000));
                break;
            case 2:
                editText_username.setText(RegisterInfoHolder.getFirstName().toLowerCase() + "_" + RegisterInfoHolder.getLastName().toLowerCase());
                break;
            case 3:
                editText_username.setText(RegisterInfoHolder.getFirstName().toLowerCase() + "_" + RegisterInfoHolder.getLastName().toLowerCase() + random.nextInt(1000));
                break;
            case 4:
                editText_username.setText(RegisterInfoHolder.getLastName().toLowerCase() + RegisterInfoHolder.getFirstName().toLowerCase());
                break;
            case 5:
                editText_username.setText(RegisterInfoHolder.getLastName().toLowerCase() + RegisterInfoHolder.getFirstName().toLowerCase() + random.nextInt(1000));
                break;
            case 6:
                editText_username.setText(RegisterInfoHolder.getLastName().toLowerCase() + "_" + RegisterInfoHolder.getFirstName().toLowerCase());
                break;
            case 7:
                editText_username.setText(RegisterInfoHolder.getLastName().toLowerCase() + "_" + RegisterInfoHolder.getFirstName().toLowerCase() + random.nextInt(1000));
                break;
        }
    }
}