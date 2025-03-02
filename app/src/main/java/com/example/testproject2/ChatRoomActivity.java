package com.example.testproject2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.ChatMessageAdapter;
import com.example.objects.Account;
import com.example.objects.Message;
import com.example.objects.Post;
import com.example.objects.Product;
import com.example.objects.Variation;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.testproject2.databinding.ActivityChatRoomBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatRoomActivity extends AppCompatActivity {

    private int productId;
    private Product product;
    private ActivityChatRoomBinding binding;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    private ArrayList<Message> messages;
    private ChatMessageAdapter chatMessageAdapter;
    private int alreadyRoomId;
    private String headerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        productId = getIntent().getIntExtra("productId", 0);
        alreadyRoomId = getIntent().getIntExtra("alreadyRoomId", -1);
        messages = new ArrayList<>();
        binding.chatRoomLayoutSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.chatRoomEditTextMessage.getText().toString().trim().isEmpty())
                    return;
                checkForChatRoom();
            }
        });
        binding.chatRoomImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getProduct();
    }

    private void getProduct() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_product.php?product_id=" + productId;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        ArrayList<Variation> singleVariation = new ArrayList<>();
                        singleVariation.add(new Variation(
                                0,
                                "not needed",
                                0,
                                0,
                                jsonObject.getString("image")
                        ));
                        product = new Product(
                                jsonObject.getInt("product_id"),
                                jsonObject.getString("product_name"),
                                "not needed",
                                new Account(
                                        jsonObject.getInt("seller_id"),
                                        jsonObject.getString("seller_image"),
                                        jsonObject.getString("first_name"),
                                        jsonObject.getString("last_name"),
                                        "not needed",
                                        "not needed",
                                        "not needed",
                                        "not needed"
                                ),
                                singleVariation
                        );
                        if (product.getAccount().getId() == CurrentAccount.getAccount().getId()) {
                            getBuyerInfo();
                        }
                        else {
                            headerName = product.getAccount().getName();
                            setValues();
                        }
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getBuyerInfo() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_buyer_info.php?room_id=" + alreadyRoomId;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        Account account = new Account(
                                0,
                                "",
                                jsonObject.getString("first_name"),
                                jsonObject.getString("last_name"),
                                "not needed",
                                "not needed",
                                "not needed",
                                "not needed"
                        );
                        headerName = account.getName();
                        setValues();

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void setValues() {
        runOnUiThread(() -> {
            messages.add(
                    new Message(
                            0,
                            0,
                            0,
                            "",
                            product.getVariations().get(0).getImage(),
                            "",
                            "",
                            "SYSTEM")
            );
            chatMessageAdapter = new ChatMessageAdapter(this,
                    product,
                    messages
            );
            binding.chatRoomRecyclerView.setAdapter(chatMessageAdapter);
            binding.chatRoomTextViewName.setText(headerName);
            if (alreadyRoomId == -1)
                getRoomId();
            else
                getMessagesCount(alreadyRoomId);
        });
    }

    private void getRoomId() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/if_chat_room_exist.php?product_id=" + product.getId() + "&buyer_id=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        int chatRoom = jsonObject.getInt("room_id");
                        if(chatRoom != 0) {
                            getMessagesCount(chatRoom);
                        }

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getMessagesCount(int roomId) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_messages_count.php?room_id=" + roomId;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        int total_messages = jsonResponse.getInt("total_messages");
                        getAllMessages(0, total_messages, roomId);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getAllMessages(int reccursion, int end, int roomId) {
        if(reccursion == end) {
            updateMessages();
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_message.php?room_id=" + roomId + "&offset=" + reccursion;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        ArrayList<Variation> singleVariation = new ArrayList<>();
                        int sender_id = jsonObject.getInt("sender_id");
                        String type = "";
                        if(sender_id == CurrentAccount.getAccount().getId()) {
                            type = "SENDER";
                        }
                        else {
                            type = "RECEIVER";
                        }
                        messages.add(new Message(
                                jsonObject.getInt("message_id"),
                                jsonObject.getInt("room_id"),
                                sender_id,
                                jsonObject.getString("name"),
                                jsonObject.getString("profile_image"),
                                jsonObject.getString("content"),
                                jsonObject.getString("datetime_sent"),
                                type
                        ));
                        getAllMessages(reccursion + 1, end, roomId);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void checkForChatRoom() {
        if (alreadyRoomId != -1) {
            enterMessage(alreadyRoomId);
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/if_chat_room_exist.php?product_id=" + product.getId() + "&buyer_id=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        int chatRoom = jsonObject.getInt("room_id");
                        if(chatRoom == 0) {
                            createChatRoom();
                        }
                        else {
                            enterMessage(chatRoom);
                        }

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(ChatRoomActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void createChatRoom() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/create_chat_room.php";

        RequestBody body = new FormBody.Builder()
                .add("product_id", Integer.toString(productId))
                .add("buyer_id", Integer.toString(CurrentAccount.getAccount().getId()))
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
                                checkForChatRoom();
                            } else {
                                Toast.makeText(ChatRoomActivity.this, "Follow Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChatRoomActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void enterMessage(int chatRoom) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/enter_message.php";

        RequestBody body = new FormBody.Builder()
                .add("room_id", Integer.toString(chatRoom))
                .add("sender_id", Integer.toString(CurrentAccount.getAccount().getId()))
                .add("content", binding.chatRoomEditTextMessage.getText().toString().trim())
                .add("datetime_sent", new SimpleDateFormat("HH:mm").format(new Date()).toString())
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
                                runOnUiThread(() -> {
                                    messages.add(new Message(
                                            0,
                                            chatRoom,
                                            CurrentAccount.getAccount().getId(),
                                            CurrentAccount.getAccount().getName(),
                                            CurrentAccount.getAccount().getImage(),
                                            binding.chatRoomEditTextMessage.getText().toString().trim(),
                                            new SimpleDateFormat("HH:mm").format(new Date()).toString(),
                                            "SENDER"
                                    ));
                                    updateMessages();
                                });
                            } else {
                                Toast.makeText(ChatRoomActivity.this, "Messaged Failed" + responseData, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChatRoomActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void updateMessages() {
        runOnUiThread(() -> {
            chatMessageAdapter.notifyDataSetChanged();
            binding.chatRoomEditTextMessage.setText("");
        });

    }
}