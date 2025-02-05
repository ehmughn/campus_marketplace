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
import com.example.objects.Reviews;
import com.example.temporary_values.TemporaryPostList;
import com.example.testproject2.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Categories> homeCategories;
    private ArrayList<Post> homePosts;
    private ArrayList<Reviews> example_reviews;
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

        for(int i = 0; i < TemporaryPostList.size(); i++) {
            homePosts.add(TemporaryPostList.getPost(i));
        }
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