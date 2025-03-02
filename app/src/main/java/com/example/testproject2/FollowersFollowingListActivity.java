package com.example.testproject2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.AccountsListAdapter;
import com.example.objects.Account;
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

public class FollowersFollowingListActivity extends AppCompatActivity {

    private ImageView imageView_profilePicture;
    private TextView textView_name;
    private TextView textView_followers;
    private TextView textView_following;
    private LinearLayout layout_line_followers;
    private LinearLayout layout_line_following;
    private TextView textView_count;
    private RecyclerView recyclerView_followers;
    private RecyclerView recyclerView_following;
    private ArrayList<Account> accounts_followers;
    private ArrayList<Account> accounts_following;
    private AccountsListAdapter adapter_followers;
    private AccountsListAdapter adapter_following;
    private int userId;
    private int followersCount;
    private int followingCount;
    private boolean inFollowers;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_followers_following_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userId = getIntent().getIntExtra("userId", 0);
        imageView_profilePicture = findViewById(R.id.followersFollowing_imageView_profilePicture);
        textView_name = findViewById(R.id.followersFollowing_textView_name);
        textView_followers = findViewById(R.id.followersFollowing_textView_followers);
        textView_followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFollowers();
            }
        });
        textView_following = findViewById(R.id.followersFollowing_textView_following);
        textView_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFollowing();
            }
        });
        layout_line_followers = findViewById(R.id.followersFollowing_line_followers);
        layout_line_following =findViewById(R.id.followersFollowing_line_following);
        textView_count = findViewById(R.id.followersFollowing_textView_count);
        recyclerView_followers = findViewById(R.id.followersFollowing_recyclerView_followersAccountsList);
        recyclerView_followers.setLayoutManager(new LinearLayoutManager(this));
        accounts_followers = new ArrayList<>();
        adapter_followers = new AccountsListAdapter(this, this, accounts_followers);
        recyclerView_followers.setAdapter(adapter_followers);
        recyclerView_following = findViewById(R.id.followersFollowing_recyclerView_followingAccountsList);
        recyclerView_following.setLayoutManager(new LinearLayoutManager(this));
        accounts_following = new ArrayList<>();
        adapter_following = new AccountsListAdapter(this, this, accounts_following);
        recyclerView_following.setAdapter(adapter_following);
        Bundle extras = getIntent().getExtras();
        inFollowers = extras.getBoolean("inFollowers");
        if(inFollowers)
            toFollowers();
        else
            toFollowing();
        getNumbersCount();
    }

    private void toFollowers() {
        textView_followers.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_bold));
        textView_following.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_regular));
        layout_line_followers.setVisibility(View.VISIBLE);
        layout_line_following.setVisibility(View.INVISIBLE);
        recyclerView_followers.setVisibility(View.VISIBLE);
        recyclerView_following.setVisibility(View.INVISIBLE);
        textView_count.setText(accounts_followers.size() + " Followers");
    }

    private void toFollowing() {
        textView_followers.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_regular));
        textView_following.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_bold));
        layout_line_followers.setVisibility(View.INVISIBLE);
        layout_line_following.setVisibility(View.VISIBLE);
        recyclerView_followers.setVisibility(View.INVISIBLE);
        recyclerView_following.setVisibility(View.VISIBLE);
        textView_count.setText(accounts_following.size() + " Following");
    }

    private void getNumbersCount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_followers_following_count.php?current_user=" + userId;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(FollowersFollowingListActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        followersCount = jsonObject.getInt("follower_count");
                        followingCount = jsonObject.getInt("following_count");
                        getFollowers(0, followersCount);
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(FollowersFollowingListActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(FollowersFollowingListActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getFollowers(int recursion, int end) {
        if(recursion == end) {
            runOnUiThread(() -> {
                adapter_followers.notifyDataSetChanged();
                if (inFollowers)
                    textView_count.setText(accounts_followers.size() + " Followers");

            });
            getFollowing(0, followingCount);
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_follower.php?current_user=" + CurrentAccount.getAccount().getId() + "&user_id=" + userId + "&offset=" + recursion;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(FollowersFollowingListActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        Account account = new Account(
                                jsonObject.getInt("user_id"),
                                jsonObject.getString("profile_image"),
                                jsonObject.getString("first_name"),
                                jsonObject.getString("last_name"),
                                "not needed",
                                "not needed",
                                "not needed",
                                "not needed"
                        );
                        account.setFollowed(jsonObject.getInt("is_followed_by_current_user") != 0);
                        accounts_followers.add(account);
                        getFollowers(recursion + 1, end);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(FollowersFollowingListActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(FollowersFollowingListActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getFollowing(int recursion, int end) {
        if(recursion == end) {
            runOnUiThread(() -> {
                adapter_following.notifyDataSetChanged();
                if (!inFollowers)
                    textView_count.setText(accounts_following.size() + " Following");
            });
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_following.php?current_user=" + CurrentAccount.getAccount().getId() + "&user_id=" + userId + "&offset=" + recursion;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(FollowersFollowingListActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        Account account = new Account(
                                jsonObject.getInt("user_id"),
                                jsonObject.getString("profile_image"),
                                jsonObject.getString("first_name"),
                                jsonObject.getString("last_name"),
                                "not needed",
                                "not needed",
                                "not needed",
                                "not needed"
                        );
                        account.setFollowed(jsonObject.getInt("is_followed_by_current_user") != 0);
                        accounts_following.add(account);
                        getFollowing(recursion + 1, end);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(FollowersFollowingListActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(FollowersFollowingListActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}