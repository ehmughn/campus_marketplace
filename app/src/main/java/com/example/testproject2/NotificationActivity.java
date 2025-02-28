package com.example.testproject2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.AccountsListAdapter;
import com.example.adapters.NotificationsListAdapter;
import com.example.objects.Account;
import com.example.objects.Notification;
import com.example.objects.Post;
import com.example.objects.Product;
import com.example.objects.Variation;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView_notifications;
    private ArrayList<Notification> notifications;
    private NotificationsListAdapter adapter_notifications;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView_notifications = findViewById(R.id.notification_recyclerView);
        recyclerView_notifications.setLayoutManager(new LinearLayoutManager(this));
        notifications = new ArrayList<>();
        adapter_notifications = new NotificationsListAdapter(this, notifications);
        recyclerView_notifications.setAdapter(adapter_notifications);
        getNotificationsCount();
    }

    private void getNotificationsCount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_user_notifications_count.php?current_user=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(NotificationActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        int total_count = jsonResponse.getInt("total_count");
                        loadNotifications(0, total_count);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(NotificationActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(NotificationActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void loadNotifications(int reccursion, int end) {
        if(reccursion == end) {
            runOnUiThread(() -> {
                adapter_notifications.notifyDataSetChanged();
            });
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_notifications_by_user.php?offset=" + reccursion + "&current_user=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(NotificationActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        String type = jsonObject.getString("type");
                        String message = "";
                        if (type.equals("LIKE")) {
                            message = "liked your post.";
                        }
                        else if (type.equals("FOLLOW")) {
                            message = "followed you.";
                        }
                        else if (type.equals("REVIEW")) {
                            message = "reviewed your product.";
                        }
                        Notification notification = new Notification(
                                jsonObject.getInt("id"),
                                jsonObject.getString("image"),
                                jsonObject.getString("name"),
                                message,
                                type,
                                jsonObject.getInt("reference")
                        );
                        notifications.add(notification);
                        loadNotifications(reccursion + 1, end);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(NotificationActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(NotificationActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}