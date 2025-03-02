package com.example.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adapters.ExploreAdapter;
import com.example.objects.Reviews;
import com.example.objects.VariationForExploreFragment;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;
import com.example.testproject2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
        loadPosts();
    }

    private void loadPosts() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_post_explore.php";

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
                            exploreVariations.add(new VariationForExploreFragment(
                                    jsonObject.getInt("post_id"),
                                    jsonObject.getInt("variant_id"),
                                    EncodeImage.encodeFromDrawable(getResources(), R.drawable.loading_image)
                            ));
                        }
                        requireActivity().runOnUiThread(() -> {
                            if (!isFragmentActive)
                                return;
                            Collections.shuffle(exploreVariations);
                            adapter_explore.notifyDataSetChanged();
                        });
                        loadPostsImages(0, responseArray.length());

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
    private void loadPostsImages(int reccursion, int end) {
        if(!isFragmentActive || reccursion == end) {

            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_post_explore_images.php?post_number=" + reccursion;

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
                        for(VariationForExploreFragment variation: exploreVariations) {
                            if(variation.getVariant_id() == jsonObject.getInt("variant_id")) {
                                variation.setImage(jsonObject.getString("image"));
                                break;
                            }
                        }
                        requireActivity().runOnUiThread(() -> {
                            if (!isFragmentActive)
                                return;
                            adapter_explore.notifyDataSetChanged();
                        });
                        loadPostsImages(reccursion + 1, end);

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