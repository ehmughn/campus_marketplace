package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapters.ExploreAdapter;
import com.example.adapters.HomeCategoryAdapter;
import com.example.adapters.HomePostsAdapter;
import com.example.objects.Categories;
import com.example.objects.Post;
import com.example.temporary_values.TemporaryPostList;
import com.example.testproject2.CartActivity;
import com.example.testproject2.R;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {
    private ArrayList<Post> explorePosts;
    private RecyclerView recyclerView_posts;
    private ExploreAdapter adapter_explore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView_posts = view.findViewById(R.id.explore_recyclerView);
        recyclerView_posts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        explorePosts = new ArrayList<>();

//        for(int i = 0; i < TemporaryPostList.size(); i++) {
//            explorePosts.add(TemporaryPostList.getPost(i));
//        }
        adapter_explore = new ExploreAdapter(getContext(), explorePosts);
        recyclerView_posts.setAdapter(adapter_explore);

    }
}