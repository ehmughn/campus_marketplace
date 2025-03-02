package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adapters.HomeCategoryAdapter;
import com.example.adapters.HomePostsAdapter;
import com.example.objects.Account;
import com.example.objects.Category;
import com.example.objects.Post;
import com.example.objects.Product;
import com.example.objects.Reviews;
import com.example.objects.Variation;
import com.example.static_classes.Categories;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;
import com.example.testproject2.NotificationActivity;
import com.example.testproject2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private ArrayList<Category> homeCategories;
    private ArrayList<Post> homePosts;
    private ArrayList<Reviews> example_reviews;
    private RecyclerView recyclerView_categories;
    private RecyclerView recyclerView_posts;
    private HomeCategoryAdapter adapter_categories;
    private HomePostsAdapter adapter_posts;
    private ImageView imageView_notification;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    private boolean isFragmentActive = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFragmentActive = false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager postsLayoutManager = new LinearLayoutManager(getContext());
        postsLayoutManager.setStackFromEnd(false);
        recyclerView_posts = view.findViewById(R.id.recyclerView_home_posts);
        recyclerView_posts.setLayoutManager(postsLayoutManager);
        homePosts = new ArrayList<>();
        adapter_posts = new HomePostsAdapter(getContext(), getActivity(), homePosts);
        recyclerView_posts.setAdapter(adapter_posts);

        recyclerView_categories = view.findViewById(R.id.recyclerView_home_categories);
        recyclerView_categories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        adapter_categories = new HomeCategoryAdapter(getContext(), Categories.getCategories(), category -> {
            filterPosts(category.getName());
        });
        recyclerView_categories.setAdapter(adapter_categories);

        imageView_notification = view.findViewById(R.id.home_imageView_notification);
        imageView_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        loadPosts();
    }

    private void filterPosts(String categoryName) {
        ArrayList<Post> filteredPosts = new ArrayList<>();
        if(categoryName.equals("Popular")) {
            filteredPosts = homePosts;
            Collections.sort(filteredPosts, Comparator.comparingInt(Post::getLikeCount).reversed());
        }
        else if(categoryName.equals("Following")) {
            for(Post post: homePosts) {
                if(post.isFollowedByCurrentUser()) {
                    filteredPosts.add(post);
                }
            }
        }
        else {
            for(Post post: homePosts) {
                if(post.getProduct().getCategory().equals(categoryName)) {
                    filteredPosts.add(post);
                }
            }
        }

        adapter_posts = new HomePostsAdapter(getContext(), getActivity(), filteredPosts);
        recyclerView_posts.setAdapter(adapter_posts);
        adapter_posts.notifyDataSetChanged();
    }

    private void loadPosts() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_post_home.php?current_user=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!isFragmentActive)
                    return;
                requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!isFragmentActive)
                    return;
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        for(int i = 0; i < responseArray.length(); i++) {
                            JSONObject jsonObject = responseArray.getJSONObject(i);
                            ArrayList<Variation> singleVariation = new ArrayList<>();
                            singleVariation.add(new Variation(
                                    0,
                                    jsonObject.getString("variant_name"),
                                    jsonObject.getDouble("variant_cost"),
                                    jsonObject.getInt("variant_stock"),
                                    EncodeImage.encodeFromDrawable(getResources(), R.drawable.loading_image)
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
                                                    EncodeImage.encodeFromDrawable(getResources(), R.drawable.loading_image),
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
                                    (jsonObject.getInt("followed_by_current_user") == 1),
                                    example_reviews
                            );

                            requireActivity().runOnUiThread(() -> {
                                if (!isFragmentActive)
                                    return;
                                homePosts.add(post);
                            });
                        }
                        requireActivity().runOnUiThread(() -> adapter_posts.notifyDataSetChanged());
                        loadImages(0, responseArray.length());

                    } catch (Exception e) {
                        if (!isFragmentActive)
                            return;
                        requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    if (!isFragmentActive)
                        return;
                    requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void loadImages(int recursion, int end) {
        if(recursion == end) {
            requireActivity().runOnUiThread(() -> adapter_posts.notifyDataSetChanged());
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_post_home_images.php?offset=" + recursion + "&current_user=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!isFragmentActive)
                    return;
                requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!isFragmentActive)
                    return;
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        for(Post post: homePosts) {
                            if(post.getId() == jsonObject.getInt("post_id")) {
                                post.getProduct().getVariations().get(0).setImage(jsonObject.getString("variant_image"));
                                post.getProduct().getAccount().setImage(jsonObject.getString("seller_image"));
                                break;
                            }
                        }
                        loadImages(recursion + 1, end);

                    } catch (Exception e) {
                        if (!isFragmentActive)
                            return;
                        requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    if (!isFragmentActive)
                        return;
                    requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}