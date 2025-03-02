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
import android.widget.Toast;

import com.example.adapters.ExploreAdapter;
import com.example.adapters.HomeCategoryAdapter;
import com.example.adapters.HomePostsAdapter;
import com.example.objects.Account;
import com.example.objects.Categories;
import com.example.objects.Post;
import com.example.objects.Product;
import com.example.objects.Reviews;
import com.example.objects.Variation;
import com.example.objects.VariationForExploreFragment;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
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

public class ExploreFragment extends Fragment {
    private ArrayList<VariationForExploreFragment> exploreVariations;
    private ArrayList<Reviews> example_reviews;
    private RecyclerView recyclerView_posts;
    private ExploreAdapter adapter_explore;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    private boolean isFragmentActive = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFragmentActive = false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView_posts = view.findViewById(R.id.explore_recyclerView);
        recyclerView_posts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        exploreVariations = new ArrayList<>();
        adapter_explore = new ExploreAdapter(getContext(), exploreVariations);
        recyclerView_posts.setAdapter(adapter_explore);
        getVariationCount();
    }

    private void getVariationCount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_all_variant_count.php?";

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
                        int total_variants = jsonResponse.getInt("total_variants");
                        loadPosts(0, total_variants);

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
        if(!isFragmentActive || reccursion == end) {
            //finish
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_post_explore.php?post_number=" + reccursion;

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
                        exploreVariations.add(new VariationForExploreFragment(
                                jsonObject.getInt("post_id"),
                                jsonObject.getString("image")
                        ));

                        requireActivity().runOnUiThread(() -> {
                            if (!isFragmentActive)
                                return;
                            adapter_explore.notifyDataSetChanged();
                        });
                        loadPosts(reccursion + 1, end);

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