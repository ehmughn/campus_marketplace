package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.adapters.HomeCategoryAdapter;
import com.example.adapters.HomePostsAdapter;
import com.example.objects.Account;
import com.example.objects.Categories;
import com.example.objects.Post;
import com.example.objects.Product;
import com.example.objects.Reviews;
import com.example.objects.Variation;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.temporary_values.TemporaryPostList;
import com.example.testproject2.CartActivity;
import com.example.testproject2.PublishPostActivity;
import com.example.testproject2.R;

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

public class HomeFragment extends Fragment {

    private ArrayList<Categories> homeCategories;
    private ArrayList<Post> homePosts;
    private ArrayList<Reviews> example_reviews;
    private RecyclerView recyclerView_categories;
    private RecyclerView recyclerView_posts;
    private HomeCategoryAdapter adapter_categories;
    private HomePostsAdapter adapter_posts;
    private LinearLayout layout_cart;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

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

//        for(int i = 0; i < TemporaryPostList.size(); i++) {
//            homePosts.add(TemporaryPostList.getPost(i));
//        }
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

        layout_cart = view.findViewById(R.id.home_layout_cart);
        layout_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        getPostCount();
    }

    private void getPostCount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_post_count.php?";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        int total_post = jsonResponse.getInt("total_post");
                        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Total Post: " + total_post, Toast.LENGTH_SHORT).show());
                        loadPosts(0, total_post);

                    } catch (Exception e) {
                        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void loadPosts(int reccursion, int end) {
        if(reccursion == end) {
            // finish
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_post_home.php?post_number=" + reccursion;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
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
                                                jsonObject.getString("seller_name"),
                                                "not needed"
                                        ),
                                        singleVariation
                                ),
                                example_reviews
                        );
                        homePosts.add(post);
//                        requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Post obtained " + (reccursion + 1), Toast.LENGTH_SHORT).show());
                        requireActivity().runOnUiThread(() -> adapter_categories.notifyDataSetChanged());
//                        loadPosts(reccursion + 1, end);

                    } catch (Exception e) {
                        requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    requireActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}