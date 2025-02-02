package com.example.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapters.HomeCategoryAdapter;
import com.example.adapters.HomePostsAdapter;
import com.example.objects.Categories;
import com.example.objects.Post;
import com.example.testproject2.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Categories> homeCategories;
    private ArrayList<Post> homePosts;
    private RecyclerView recyclerView_categories;
    private RecyclerView recyclerView_posts;
    private HomeCategoryAdapter adapter_categories;
    private HomePostsAdapter adapter_posts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView_posts = view.findViewById(R.id.recyclerView_home_posts);
        recyclerView_posts.setLayoutManager(new LinearLayoutManager(getContext()));
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

        adapter_posts = new HomePostsAdapter(getContext(), homePosts);
        recyclerView_posts.setAdapter(adapter_posts);

        recyclerView_categories = view.findViewById(R.id.recyclerView_home_categories);
        recyclerView_categories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        homeCategories = new ArrayList<>();

        homeCategories.add(new Categories("Popular"));
        homeCategories.add(new Categories("Uniform"));
        homeCategories.add(new Categories("Crafts"));
        homeCategories.add(new Categories("Foods"));
        homeCategories.add(new Categories("Droga"));
        homeCategories.add(new Categories("Alak"));
        homeCategories.add(new Categories("Pagmamahal"));
        homeCategories.add(new Categories("DNFJSDIHFSDI"));

        adapter_categories = new HomeCategoryAdapter(getContext(), homeCategories);
        recyclerView_categories.setAdapter(adapter_categories);
    }
}