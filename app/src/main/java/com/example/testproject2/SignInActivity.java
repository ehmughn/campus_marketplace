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

import com.example.objects.Account;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;
import com.example.static_classes.RegisterInfoHolder;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {

    TextView textView_errorMessage;
    private EditText editText_email;
    private EditText editText_password;
    private Button login_button_signIn;
    private OkHttpClient client = new OkHttpClient();

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
        textView_errorMessage = findViewById(R.id.login_textview_errormessage);
        editText_email = findViewById(R.id.login_edittext_email);
        editText_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_errorMessage.setText("");
                }
            }
        });
        editText_password = findViewById(R.id.login_edittext_password);
        editText_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    textView_errorMessage.setText("");
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
        this.overridePendingTransition(R.anim.animate_slide_in_right, R.anim.animate_slide_out_left);
    }

    public void finish(View v) {
        finish();
    }

    public void button_signIn() {
        editText_email.clearFocus();
        editText_password.clearFocus();
        findAccount(editText_email.getText().toString(), editText_password.getText().toString());
    }

    private void findAccount(String email, String password) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/login.php?email=" + email + "&password=" + password;

        // Build the OkHttp request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Make asynchronous network request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(SignInActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        String status = jsonResponse.getString("status");
                        if (status.equals("invalid_credentials")) {
                            errorMessage("Invalid Credentials.");
                        } else {
                            loginUser(responseData);
                        }
                    } catch (Exception e) {
                        errorMessage("Unexpected Response.");
                        runOnUiThread(() -> Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    errorMessage("Network Error.");
                }
            }
        });
    }

    private void errorMessage(String errorMessage) {
        textView_errorMessage.setText(errorMessage);
    }

    private void loginUser(String responseData) {
        try {
            JSONObject jsonResponse = new JSONObject(responseData);
            JSONObject data = jsonResponse.getJSONObject("data");
            CurrentAccount.setAccount(new Account(
                    data.getInt("user_id"),
                    data.getString("profile_image"),
                    data.getString("first_name") + " " + data.getString("last_name"),
                    data.getString("bio")));
            CurrentAccount.getAccount().setBlobImage(data.getString("profile_image"));
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        catch(Exception e) {

        }
    }
}