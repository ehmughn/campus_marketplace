package com.example.testproject2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.objects.Account;
import com.example.objects.Variation;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;
import com.example.static_classes.RegisterInfoHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    private LayoutInflater inflaterPleaseWait;
    private View dialogPleaseWaitView;
    private AlertDialog.Builder builderDialogPleaseWait;
    private AlertDialog dialogPleaseWait;
    private TextView dialogPleaseWait_textView_progress;

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
                if(!isValidUsername(check_username, true))
                    return;
                isAlreadyUsed(check_username);
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
                intent.putExtra("toEditProfile", false);
                startActivity(intent);
                finish();
            }
        });
        dialog_button_editProfile = dialogView.findViewById(R.id.dialog_button_editProfile);
        dialog_button_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register4Activity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("toEditProfile", true);
                startActivity(intent);
                finish();
            }
        });
        builder = new AlertDialog.Builder(this);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        inflaterPleaseWait = getLayoutInflater();
        dialogPleaseWaitView = inflaterPleaseWait.inflate(R.layout.dialog_please_wait, null);
        builderDialogPleaseWait = new AlertDialog.Builder(this);
        builderDialogPleaseWait.setView(dialogPleaseWaitView).setCancelable(false);
        dialogPleaseWait = builderDialogPleaseWait.create();
        dialogPleaseWait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPleaseWait_textView_progress = dialogPleaseWaitView.findViewById(R.id.dialogPleaseWait_textView_progress);

    }

    public void randomizeUsername(View v) {
        Random random = new Random();
        String username = "";
        do {
            switch (random.nextInt(8)) {
                case 0:
                    username = RegisterInfoHolder.getFirstName().replaceAll("\\s+", "").toLowerCase() + RegisterInfoHolder.getLastName().replaceAll("\\s+", "").toLowerCase();
                    break;
                case 1:
                    username = RegisterInfoHolder.getFirstName().replaceAll("\\s+", "").toLowerCase() + RegisterInfoHolder.getLastName().replaceAll("\\s+", "").toLowerCase() + random.nextInt(1000);
                    break;
                case 2:
                    username = RegisterInfoHolder.getFirstName().replaceAll("\\s+", "").toLowerCase() + "_" + RegisterInfoHolder.getLastName().replaceAll("\\s+", "").toLowerCase();
                    break;
                case 3:
                    username = RegisterInfoHolder.getFirstName().replaceAll("\\s+", "").toLowerCase() + "_" + RegisterInfoHolder.getLastName().replaceAll("\\s+", "").toLowerCase() + random.nextInt(1000);
                    break;
                case 4:
                    username = RegisterInfoHolder.getLastName().replaceAll("\\s+", "").toLowerCase() + RegisterInfoHolder.getFirstName().replaceAll("\\s+", "").toLowerCase();
                    break;
                case 5:
                    username = RegisterInfoHolder.getLastName().replaceAll("\\s+", "").toLowerCase() + RegisterInfoHolder.getFirstName().replaceAll("\\s+", "").toLowerCase() + random.nextInt(1000);
                    break;
                case 6:
                    username = RegisterInfoHolder.getLastName().replaceAll("\\s+", "").toLowerCase() + "_" + RegisterInfoHolder.getFirstName().replaceAll("\\s+", "").toLowerCase();
                    break;
                case 7:
                    username = RegisterInfoHolder.getLastName().replaceAll("\\s+", "").toLowerCase() + "_" + RegisterInfoHolder.getFirstName().replaceAll("\\s+", "").toLowerCase() + random.nextInt(1000);
                    break;
            }
        } while(!isValidUsername(username, false));
        editText_username.setText(username);
    }

    private boolean isValidUsername(String username, boolean enableLengthMatching) {
        if(username.isEmpty()) {
            textView_errorMessage.setText("Please fill up the fields.");
            return false;
        }
        if(username.matches("^[a-z][a-z0-9_]")) {
            textView_errorMessage.setText("Username must start with a letter and only contain small letters, numbers, and \"_\".");
            return false;
        }
        if((username.length() < 6 || username.length() > 19) && !enableLengthMatching) {
            textView_errorMessage.setText("Username length must be 6 to 19");
            return false;
        }
        if(username.equals("eman_bawalan1")){
            textView_errorMessage.setText("Username already taken.");
            return false;
        }
        return true;
    }

    private void isAlreadyUsed(String username) {
        dialogPleaseWait.show();
        dialogPleaseWait_textView_progress.setText("Attempting to register account");
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/is_already_used/username.php?username=" + username;

        // Build the OkHttp request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Make asynchronous network request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialogPleaseWait.dismiss();
                runOnUiThread(() -> Toast.makeText(Register4Activity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    if (responseData.equals("username_used")) {
                        dialogPleaseWait.dismiss();
                        textView_errorMessage.setText("Username already taken.");
                    } else {
                        RegisterInfoHolder.setUsername(username);
                        attemptToInsertRegisteredAccount();
                    }
                }
                else {
                    dialogPleaseWait.dismiss();
                    textView_errorMessage.setText("Unexpected Response.");
                }
            }
        });
    }

    private void attemptToInsertRegisteredAccount() {
        button_next.setClickable(false);
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/register.php";

        RequestBody body = new FormBody.Builder()
                .add("email", RegisterInfoHolder.getEmail())
                .add("password", RegisterInfoHolder.getPassword())
                .add("first_name", RegisterInfoHolder.getFirstName())
                .add("last_name", RegisterInfoHolder.getLastName())
                .add("username", RegisterInfoHolder.getUsername())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful() && responseData.contains("success")) {
                                getRegisteredId();
                            } else {
                                dialogPleaseWait.dismiss();
                                Toast.makeText(Register4Activity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialogPleaseWait.dismiss();
                            Toast.makeText(Register4Activity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } finally {
                    button_next.setClickable(true);
                }
            }
        }).start();
    }

    private void getRegisteredId() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_registered_id.php?username=" + RegisterInfoHolder.getUsername();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialogPleaseWait.dismiss();
                runOnUiThread(() -> Toast.makeText(Register4Activity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        insertDefaultUserProfileData(jsonObject.getInt("user_id"));

                    } catch (Exception e) {
                        dialogPleaseWait.dismiss();
                        runOnUiThread(() -> Toast.makeText(Register4Activity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    dialogPleaseWait.dismiss();
                    runOnUiThread(() -> Toast.makeText(Register4Activity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void insertDefaultUserProfileData(int userId) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/modify_user_profile.php";

        RequestBody body = new FormBody.Builder()
                .add("user_id", Integer.toString(userId))
                .add("bio", "")
                .add("profile_image", EncodeImage.encodeFromDrawable(getResources(), R.drawable.no_profile_image))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful() && responseData.contains("success")) {
                                setCurrentAccount();
                            } else {
                                dialogPleaseWait.dismiss();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialogPleaseWait.dismiss();
                            Toast.makeText(Register4Activity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void setCurrentAccount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/login_after_register.php?username=" + RegisterInfoHolder.getUsername();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialogPleaseWait.dismiss();
                runOnUiThread(() -> Toast.makeText(Register4Activity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
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
                                RegisterInfoHolder.getPassword()
                        ));
                        runOnUiThread(() -> dialogPleaseWait.dismiss());
                        runOnUiThread(() -> dialog.show());
                    } catch (Exception e) {
                        dialogPleaseWait.dismiss();
                        runOnUiThread(() -> Toast.makeText(Register4Activity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    dialogPleaseWait.dismiss();
                    runOnUiThread(() -> Toast.makeText(Register4Activity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}