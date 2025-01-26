package com.example.testproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Post> homePosts;
    private RecyclerView recyclerView_posts;
    private HomePostsAdapter adapter_posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView_posts = findViewById(R.id.recyclerView_posts);
        recyclerView_posts.setLayoutManager(new LinearLayoutManager(this));
        homePosts = new ArrayList<>();
        homePosts.add(new Post(R.drawable.cookies, 25, "Cookies", 50));
        homePosts.add(new Post(R.drawable.notes, 250, "Notes", 5));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 10));
        homePosts.add(new Post(R.drawable.burger, 70, "Burger", 30));
        adapter_posts = new HomePostsAdapter(homePosts);
        recyclerView_posts.setAdapter(adapter_posts);
    }

    public void toProfile(View v) {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}