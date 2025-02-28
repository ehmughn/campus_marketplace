package com.example.testproject2;

import android.content.Intent;
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

import com.example.adapters.HomePostsAdapter;
import com.example.objects.Account;
import com.example.objects.Post;
import com.example.objects.Product;
import com.example.objects.Reviews;
import com.example.objects.Variation;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;
import com.example.static_classes.RegisterInfoHolder;

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

public class VisitProfileActivity extends AppCompatActivity {

    private Account account;
    private TextView textView_uploads;
    private TextView textView_likes;
    private Button profile_button_uploadItems;
    private ArrayList<Post> profilePosts;
    private ArrayList<Post> likedPosts;
    private ArrayList<Reviews> example_reviews;
    private RecyclerView recyclerView_uploads;
    private RecyclerView recyclerView_likes;
    private HomePostsAdapter adapter_posts;
    private HomePostsAdapter adapter_likes;
    private LinearLayout layout_followers;
    private LinearLayout layout_following;
    private ImageView imageView_profilePicture;
    private TextView textView_name;
    private TextView textView_bio;
    private TextView textView_followersCount;
    private TextView textView_followingCount;
    private TextView textView_likesCount;
    private LinearLayout layout_uploadProduct;
    private LinearLayout layout_publishPost;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int userId = getIntent().getIntExtra("userId", 0);

        recyclerView_uploads = findViewById(R.id.visitProfile_recyclerView_uploads);
        recyclerView_likes = findViewById(R.id.visitProfile_recyclerView_likes);
        recyclerView_uploads.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_likes.setLayoutManager(new LinearLayoutManager(this));
        profilePosts = new ArrayList<>();
        likedPosts = new ArrayList<>();
        adapter_posts = new HomePostsAdapter(this, this, profilePosts);
        adapter_likes = new HomePostsAdapter(this, this, likedPosts);
        recyclerView_uploads.setAdapter(adapter_posts);
        recyclerView_likes.setAdapter(adapter_likes);
        textView_uploads = findViewById(R.id.visitProfile_textView_uploads);
        textView_uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_uploads.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_bold));
                textView_likes.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_regular));
                recyclerView_uploads.setVisibility(View.VISIBLE);
                recyclerView_likes.setVisibility(View.INVISIBLE);
            }
        });
        textView_likes = findViewById(R.id.visitProfile_textView_likes);
        textView_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_uploads.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_regular));
                textView_likes.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_bold));
                recyclerView_uploads.setVisibility(View.INVISIBLE);
                recyclerView_likes.setVisibility(View.VISIBLE);
            }
        });
        layout_followers = findViewById(R.id.visitProfile_layout_followers);
        layout_followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisitProfileActivity.this, FollowersFollowingListActivity.class);
                intent.putExtra("inFollowers", true);
                startActivity(intent);
            }
        });
        layout_following = findViewById(R.id.visitProfile_layout_following);
        layout_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisitProfileActivity.this, FollowersFollowingListActivity.class);
                intent.putExtra("inFollowers", false);
                startActivity(intent);
            }
        });
        imageView_profilePicture = findViewById(R.id.visitProfile_imageView_profilePicture);
        imageView_profilePicture.setImageBitmap(EncodeImage.decodeFromStringBlob(CurrentAccount.getAccount().getImage()));
        textView_name = findViewById(R.id.visitProfile_textView_name);
        textView_name.setText(CurrentAccount.getAccount().getName());
        textView_bio = findViewById(R.id.visitProfile_textView_bio);
        textView_bio.setText(CurrentAccount.getAccount().getBio());
        textView_followersCount = findViewById(R.id.visitProfile_textView_followersCount);
        textView_followersCount.setText(Integer.toString(10));
        textView_followingCount = findViewById(R.id.visitProfile_textView_followingCount);
        textView_followingCount.setText(Integer.toString(20));
        textView_likesCount = findViewById(R.id.visitProfile_textView_likesCount);
        getAccount(userId);
    }

    private void getAccount(int userId) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_account_by_id.php?user_id=" + userId;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        account = new Account(
                                jsonObject.getInt("user_id"),
                                jsonObject.getString("profile_image"),
                                jsonObject.getString("first_name"),
                                jsonObject.getString("last_name"),
                                jsonObject.getString("bio"),
                                "not needed",
                                "not needed",
                                "not needed"
                        );
                        setUserValues();
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void setUserValues() {
        runOnUiThread(() -> {
            imageView_profilePicture.setImageBitmap(EncodeImage.decodeFromStringBlob(account.getImage()));
            textView_name.setText(account.getName());
            textView_bio.setText(account.getBio());
            textView_followersCount.setText(Integer.toString(10));
            textView_followingCount.setText(Integer.toString(20));
            getPostCount();
        });
    }

    private void getPostCount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_post_count_by_user.php?current_user=" + account.getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        int total_post = jsonResponse.getInt("total_post");
                        loadPosts(0, total_post);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void loadPosts(int reccursion, int end) {
        if(reccursion == end) {
            getPostLikedCount();
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_post_visit_profile.php?post_number=" + reccursion + "&current_user=" + account.getId() + "&visiting_user=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
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
                                jsonObject.getString("variant_name"),
                                jsonObject.getDouble("variant_cost"),
                                jsonObject.getInt("variant_stock"),
                                jsonObject.getString("variant_image")
                        ));
                        Post post = new Post(
                                jsonObject.getInt("post_id"),
                                jsonObject.getString("title"),
                                jsonObject.getString("description"),
                                new Product(
                                        jsonObject.getInt("product_id"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("category"),
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
                                ),
                                jsonObject.getInt("like_count"),
                                (jsonObject.getInt("liked_by_current_user") == 1),
                                example_reviews
                        );

                        runOnUiThread(() -> {
                            profilePosts.add(post);
                            adapter_posts.notifyDataSetChanged();
                        });
                        loadPosts(reccursion + 1, end);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getPostLikedCount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_post_liked_count_by_user.php?current_user=" + account.getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        int total_post_liked = jsonResponse.getInt("total_post_liked");
                        loadLikedPosts(0, total_post_liked);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void loadLikedPosts(int reccursion, int end) {
        if(reccursion == end) {
            //finish
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_post_liked_visit_profile.php?post_number=" + reccursion + "&current_user=" + account.getId() + "&visiting_user=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
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
                                jsonObject.getString("variant_name"),
                                jsonObject.getDouble("variant_cost"),
                                jsonObject.getInt("variant_stock"),
                                jsonObject.getString("variant_image")
                        ));
                        Post post = new Post(
                                jsonObject.getInt("post_id"),
                                jsonObject.getString("title"),
                                jsonObject.getString("description"),
                                new Product(
                                        jsonObject.getInt("product_id"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("category"),
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
                                ),
                                jsonObject.getInt("like_count"),
                                (jsonObject.getInt("liked_by_current_user") == 1),
                                example_reviews
                        );

                        runOnUiThread(() -> {
                            likedPosts.add(post);
                            adapter_likes.notifyDataSetChanged();
                        });
                        loadLikedPosts(reccursion + 1, end);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(VisitProfileActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}