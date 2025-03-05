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

import com.example.adapters.ChatMessageAdapter;
import com.example.adapters.MessageListAdapter;
import com.example.objects.Account;
import com.example.objects.ChatRoom;
import com.example.objects.Message;
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

public class MessageSentListActivity extends AppCompatActivity {

    private ArrayList<ChatRoom> rooms;
    private MessageListAdapter messageListAdapter;
    private RecyclerView recyclerView;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message_sent_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rooms = new ArrayList<>();
        messageListAdapter = new MessageListAdapter(this, rooms);
        recyclerView = findViewById(R.id.messageSent_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageListAdapter);
        countMessages();
    }

    private void countMessages() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_message_sent_count.php?current_user=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MessageSentListActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        int total_rooms = jsonResponse.getInt("total_rooms");
                        loadMessages(0, total_rooms);

                    } catch (Exception e) {
                        //
                    }
                }
                else {
                    //
                }
            }
        });
    }

    private void loadMessages(int reccursion, int end) {
        if(reccursion == end) {
            setData();
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_message_sent.php?offset=" + reccursion + "&current_user=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        rooms.add(new ChatRoom(
                                jsonObject.getInt("product_id"),
                                jsonObject.getInt("room_id"),
                                jsonObject.getString("image"),
                                jsonObject.getString("seller_name"),
                                jsonObject.getString("product_name"),
                                jsonObject.getString("latest_message"),
                                jsonObject.getInt("is_current_user_sender")

                        ));
                        loadMessages(reccursion + 1, end);

                    } catch (Exception e) {
                        //
                    }
                }
                else {
                    //
                }
            }
        });
    }

    private void setData() {
        runOnUiThread(() -> {
            messageListAdapter.notifyDataSetChanged();
        });
    }
}