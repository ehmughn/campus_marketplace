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

import com.example.adapters.HomePostsAdapter;
import com.example.objects.Post;

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

        // a bunch of temporary posts until we get to know how to deal with databases
        homePosts.add(new Post(R.drawable.cookies, 25, "Cookies", "Cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! " , 50));
        homePosts.add(new Post(R.drawable.notes, 250, "Notes", "We're no strangers to love. You know the rules and so do I. A full commitment's what I'm thinkin' of. You wouldn't get this from any other guy. I just wanna tell you how I'm feeling. Gotta make you understand. Never gonna give you up. Never gonna let you down. Never gonna run around and desert you. Never gonna make you cry. Never gonna say goodbye. Never gonna tell a lie and hurt you.", 5));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10));
        homePosts.add(new Post(R.drawable.burger, 70, "Burger", "I hate jollibee, I hate mcdonalds, I hate burger king, I hate zarks, I hate popeyes, I hate angels burger, Ikaw lang ang gusto ko",30));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10));
        homePosts.add(new Post(R.drawable.uniform, 400, "Uniform", "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10));

        adapter_posts = new HomePostsAdapter(this, homePosts);
        recyclerView_posts.setAdapter(adapter_posts);
//        recyclerView_posts.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
    }

    public void toProfile(View v) {
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}