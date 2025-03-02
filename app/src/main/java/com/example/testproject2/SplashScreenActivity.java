package com.example.testproject2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.objects.Account;
import com.example.objects.Variation;
import com.example.static_classes.Categories;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.RegisterInfoHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    private int logged_account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        logged_account = sharedPreferences.getInt("logged_account", 0);
        password = sharedPreferences.getString("password", "");
        DatabaseConnectionData.setHost(sharedPreferences.getString("host", "none"));
        if(DatabaseConnectionData.getHost().equals("none")) {
            Intent intent = new Intent(SplashScreenActivity.this, SetHostActivity.class);
            startActivity(intent);
            finish();
        }
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Categories.init();
        getDatabaseCategories();
    }

    private void getDatabaseCategories() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_all_categories.php";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Intent intent = new Intent(SplashScreenActivity.this, SetHostActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        for(int i = 0; i < responseArray.length(); i++) {
                            JSONObject jsonObject = responseArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            Categories.addCategory(name);
                        }
                        if(logged_account == 0) {
                            Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            loginWithId(logged_account, password);
                        }
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(SplashScreenActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(SplashScreenActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void loginWithId(int id, String password) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/login_with_id.php?id=" + id;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(SplashScreenActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        CurrentAccount.setAccount(new Account(
                                jsonObject.getInt("user_id"),
                                jsonObject.getString("profile_image"),
                                jsonObject.getString("first_name"),
                                jsonObject.getString("last_name"),
                                jsonObject.getString("bio"),
                                jsonObject.getString("username"),
                                jsonObject.getString("email"),
                                password
                        ));
                        login();
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(SplashScreenActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(SplashScreenActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void login() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}