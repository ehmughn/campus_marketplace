package com.example.testproject2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.objects.Account;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;
import com.example.static_classes.RegisterInfoHolder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private Bitmap bitmapProfilePicture;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private String password;
    private EditText dialogChangePassword_editText_oldPassword;
    private EditText dialogChangePassword_editText_newPassword;
    private EditText dialogChangePassword_editText_confirmNewPassword;
    private Button dialogChangePassword_button_cancel;
    private Button dialogChangePassword_button_confirm;

    private LayoutInflater inflaterChangePassword;
    private View dialogChangePasswordView;
    private AlertDialog.Builder builderDialogChangePassword;
    private AlertDialog dialogChangePassword;

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

    private LayoutInflater inflaterFinishedUpdatingProfile;
    private View dialogFinishedUpdatingProfileView;
    private AlertDialog.Builder builderDialogFinishedUpdatingProfile;
    private AlertDialog dialogFinishedUpdatingProfile;
    private Button dialogFinishedUpdatingProfile_button_goBack;
    private TextView textView_logOut;

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
        textView_logOut = findViewById(R.id.editProfile_textView_logOut);
        textView_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("logged_account", 0);
                editor.putString("password", "");
                editor.apply();
                Intent intent = new Intent(EditProfileActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
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

        bitmapProfilePicture = EncodeImage.decodeFromStringBlob(CurrentAccount.getAccount().getImage());
        imageView_profilePicture.setImageBitmap(EncodeImage.decodeFromStringBlob(CurrentAccount.getAccount().getImage()));
        editText_firstName.setText(CurrentAccount.getAccount().getFirstName());
        editText_lastName.setText(CurrentAccount.getAccount().getLastName());
        editText_bio.setText(CurrentAccount.getAccount().getBio());
        editText_username.setText(CurrentAccount.getAccount().getUsername());
        editText_email.setText(CurrentAccount.getAccount().getEmail());
        password = CurrentAccount.getAccount().getPassword();

        inflaterChangePassword = getLayoutInflater();
        dialogChangePasswordView = inflaterChangePassword.inflate(R.layout.dialog_change_password, null);
        builderDialogChangePassword = new AlertDialog.Builder(this);
        builderDialogChangePassword.setView(dialogChangePasswordView);
        dialogChangePassword = builderDialogChangePassword.create();
        dialogChangePassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogChangePassword_editText_oldPassword = dialogChangePasswordView.findViewById(R.id.dialogChangePassword_editText_oldPassword);
        dialogChangePassword_editText_newPassword = dialogChangePasswordView.findViewById(R.id.dialogChangePassword_editText_newPassword);
        dialogChangePassword_editText_confirmNewPassword = dialogChangePasswordView.findViewById(R.id.dialogChangePassword_editText_confirmNewPassword);
        dialogChangePassword_button_cancel = dialogChangePasswordView.findViewById(R.id.dialogChangePassword_button_cancel);
        dialogChangePassword_button_confirm = dialogChangePasswordView.findViewById(R.id.dialogChangePassword_button_confirm);

        dialogChangePassword_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangePassword.dismiss();
            }
        });

        dialogChangePassword_button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_oldPassword = dialogChangePassword_editText_oldPassword.getText().toString();
                String input_newPassword = dialogChangePassword_editText_newPassword.getText().toString();
                String input_confirmNewPassword = dialogChangePassword_editText_confirmNewPassword.getText().toString();

                if(input_oldPassword.isEmpty() || input_newPassword.isEmpty() || input_confirmNewPassword.isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
                }
                else if(!input_oldPassword.equals(CurrentAccount.getAccount().getPassword())) {
                    Toast.makeText(EditProfileActivity.this, "Old password does not match", Toast.LENGTH_SHORT).show();
                }
                else if (!input_newPassword.equals(input_confirmNewPassword)) {
                    Toast.makeText(EditProfileActivity.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else if(input_newPassword.length() < 8) {
                    Toast.makeText(EditProfileActivity.this, "Password length must be greater than or equal to 8", Toast.LENGTH_SHORT).show();
                }
                else {
                    password = input_newPassword;
                    dialogChangePassword.dismiss();
                }
            }
        });


        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForAllFields();
            }
        });
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmapProfilePicture = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageView_profilePicture.setImageBitmap(bitmapProfilePicture);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        textView_changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageExternally();
            }
        });
        textView_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangePassword.show();
            }
        });

        inflaterPleaseWait = getLayoutInflater();
        dialogPleaseWaitView = inflaterPleaseWait.inflate(R.layout.dialog_please_wait, null);
        builderDialogPleaseWait = new AlertDialog.Builder(this);
        builderDialogPleaseWait.setView(dialogPleaseWaitView).setCancelable(false);
        dialogPleaseWait = builderDialogPleaseWait.create();
        dialogPleaseWait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPleaseWait_textView_progress = dialogPleaseWaitView.findViewById(R.id.dialogPleaseWait_textView_progress);


        inflaterFinishedUpdatingProfile = getLayoutInflater();
        dialogFinishedUpdatingProfileView = inflaterFinishedUpdatingProfile.inflate(R.layout.dialog_finished_updating_profile, null);
        builderDialogFinishedUpdatingProfile = new AlertDialog.Builder(this);
        builderDialogFinishedUpdatingProfile.setView(dialogFinishedUpdatingProfileView).setCancelable(false);
        dialogFinishedUpdatingProfile = builderDialogFinishedUpdatingProfile.create();
        dialogFinishedUpdatingProfile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFinishedUpdatingProfile_button_goBack = dialogFinishedUpdatingProfileView.findViewById(R.id.dialogFinishedUpdatingProfile_button_goBack);
        dialogFinishedUpdatingProfile_button_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFinishedUpdatingProfile.dismiss();
                finish();
            }
        });
    }

    private void checkForAllFields() {
        String firstName = editText_firstName.getText().toString().trim();
        String lastName = editText_lastName.getText().toString().trim();
        String bio = editText_bio.getText().toString().trim();
        String username = editText_username.getText().toString().trim();
        String email = editText_email.getText().toString().trim();

        if (firstName.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "First name is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(lastName.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Last name is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(username.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Username is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(email.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        dialogPleaseWait.show();
        dialogPleaseWait_textView_progress.setText("Checking for duplicate username and email.");

        String url = "http://" + DatabaseConnectionData.getHost() + "/numart_db/is_already_used/email_for_change.php?email=" + email + "&user_id=" + CurrentAccount.getAccount().getId();

        // Build the OkHttp request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Make asynchronous network request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialogPleaseWait.dismiss();
                runOnUiThread(() -> Toast.makeText(EditProfileActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    if (responseData.equals("email_used")) {
                        dialogPleaseWait.dismiss();
                        runOnUiThread(() -> Toast.makeText(EditProfileActivity.this, "Email is already being used by someone else", Toast.LENGTH_SHORT).show());
                    } else {
                        checkUsername(username);
                    }
                } else {
                    dialogPleaseWait.dismiss();
                    runOnUiThread(() -> Toast.makeText(EditProfileActivity.this, "Unexpected Response", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void checkUsername(String username) {

        String url = "http://" + DatabaseConnectionData.getHost() + "/numart_db/is_already_used/username_for_change.php?username=" + username + "&user_id=" + CurrentAccount.getAccount().getId();

        // Build the OkHttp request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Make asynchronous network request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialogPleaseWait.dismiss();
                runOnUiThread(() -> Toast.makeText(EditProfileActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    if (responseData.equals("username_used")) {
                        dialogPleaseWait.dismiss();
                        runOnUiThread(() -> Toast.makeText(EditProfileActivity.this, "Username is already being used by someone else", Toast.LENGTH_SHORT).show());
                    } else {
                        updateAccountInfo();
                    }
                } else {
                    dialogPleaseWait.dismiss();
                    runOnUiThread(() -> Toast.makeText(EditProfileActivity.this, "Unexpected Response", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void updateAccountInfo() {
        runOnUiThread(() -> dialogPleaseWait_textView_progress.setText("Updating Account Information."));
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/update_account.php";

        RequestBody body = new FormBody.Builder()
                .add("user_id", Integer.toString(CurrentAccount.getAccount().getId()))
                .add("email", editText_email.getText().toString())
                .add("password", password)
                .add("first_name", editText_firstName.getText().toString())
                .add("last_name", editText_lastName.getText().toString())
                .add("username", editText_username.getText().toString())
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
                                Toast.makeText(EditProfileActivity.this, "Account Update Successful", Toast.LENGTH_SHORT).show();
                                insertUserProfileData();
                            } else {
                                dialogPleaseWait.dismiss();
                                Toast.makeText(EditProfileActivity.this, "Account Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditProfileActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void insertUserProfileData() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/modify_user_profile_for_change.php";

        RequestBody body = new FormBody.Builder()
                .add("user_id", Integer.toString(CurrentAccount.getAccount().getId()))
                .add("bio", editText_bio.getText().toString())
                .add("profile_image", EncodeImage.encodeFromBitmap(bitmapProfilePicture))
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
                            Toast.makeText(EditProfileActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void setCurrentAccount() {
        CurrentAccount.setAccount(new Account(
                CurrentAccount.getAccount().getId(),
                EncodeImage.encodeFromBitmap(bitmapProfilePicture),
                editText_firstName.getText().toString(),
                editText_lastName.getText().toString(),
                editText_bio.getText().toString(),
                editText_username.getText().toString(),
                editText_email.getText().toString(),
                password
        ));
        dialogPleaseWait.dismiss();
        dialogFinishedUpdatingProfile.show();
    }

    private void getImageExternally() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
    }

}