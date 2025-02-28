package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapters.HomePostsAdapter;
import com.example.objects.Account;
import com.example.objects.Post;
import com.example.objects.Product;
import com.example.objects.Reviews;
import com.example.objects.Variation;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;
import com.example.temporary_values.TemporaryAccountList;
import com.example.temporary_values.TemporaryPostList;
import com.example.testproject2.EditProfileActivity;
import com.example.testproject2.FollowersFollowingListActivity;
import com.example.testproject2.MainActivity;
import com.example.testproject2.PublishPostActivity;
import com.example.testproject2.R;
import com.example.testproject2.UploadProductActivity;
import com.example.testproject2.VisitProfileActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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

public class ProfileFragment extends Fragment {

    public TextView textView_uploads;
    public TextView textView_likes;
    public Button profile_button_uploadItems;
    public Button profile_button_editProfile;
    public ArrayList<Post> profilePosts;
    public ArrayList<Post> likedPosts;
    public ArrayList<Reviews> example_reviews;
    public RecyclerView recyclerView_uploads;
    public RecyclerView recyclerView_likes;
    public HomePostsAdapter adapter_posts;
    public HomePostsAdapter adapter_likes;
    public LinearLayout layout_followers;
    public LinearLayout layout_following;
    public ImageView imageView_profilePicture;
    public TextView textView_name;
    public TextView textView_bio;
    public TextView textView_followersCount;
    public TextView textView_followingCount;
    public TextView textView_likesCount;
    private LinearLayout layout_uploadProduct;
    private LinearLayout layout_publishPost;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    private boolean isFragmentActive = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout_post for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFragmentActive = false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView_uploads = view.findViewById(R.id.profile_recyclerView_uploads);
        recyclerView_likes = view.findViewById(R.id.profile_recyclerView_likes);
        recyclerView_uploads.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_likes.setLayoutManager(new LinearLayoutManager(getContext()));
        profilePosts = new ArrayList<>();
        likedPosts = new ArrayList<>();
        adapter_posts = new HomePostsAdapter(getContext(), getActivity(), profilePosts);
        adapter_likes = new HomePostsAdapter(getContext(), getActivity(), likedPosts);
        recyclerView_uploads.setAdapter(adapter_posts);
        recyclerView_likes.setAdapter(adapter_likes);

        textView_uploads = view.findViewById(R.id.text_uploads);
        textView_uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_uploads.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.montserrat_bold));
                textView_likes.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.montserrat_regular));
                recyclerView_uploads.setVisibility(View.VISIBLE);
                recyclerView_likes.setVisibility(View.INVISIBLE);
            }
        });
        textView_likes = view.findViewById(R.id.text_likes);
        textView_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_uploads.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.montserrat_regular));
                textView_likes.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.montserrat_bold));
                recyclerView_uploads.setVisibility(View.INVISIBLE);
                recyclerView_likes.setVisibility(View.VISIBLE);
            }
        });
        profile_button_uploadItems = view.findViewById(R.id.profile_button_uploadItems);
        profile_button_uploadItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openUploadOptions();
                } catch(Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        profile_button_editProfile = view.findViewById(R.id.profile_button_editProfile);
        profile_button_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
        layout_followers = view.findViewById(R.id.profile_layout_followers);
        layout_followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FollowersFollowingListActivity.class);
                intent.putExtra("inFollowers", true);
                startActivity(intent);
            }
        });
        layout_following = view.findViewById(R.id.profile_layout_following);
        layout_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FollowersFollowingListActivity.class);
                intent.putExtra("inFollowers", false);
                startActivity(intent);
            }
        });
        imageView_profilePicture = view.findViewById(R.id.profile_imageView_profilePicture);
        imageView_profilePicture.setImageBitmap(EncodeImage.decodeFromStringBlob(CurrentAccount.getAccount().getImage()));
        textView_name = view.findViewById(R.id.profile_textView_name);
        textView_name.setText(CurrentAccount.getAccount().getName());
        textView_bio = view.findViewById(R.id.profile_textView_bio);
        textView_bio.setText(CurrentAccount.getAccount().getBio());
        textView_followersCount = view.findViewById(R.id.profile_textView_followers);
        textView_followersCount.setText(Integer.toString(10));
        textView_followingCount = view.findViewById(R.id.profile_textView_following);
        textView_followingCount.setText(Integer.toString(20));
        textView_likesCount = view.findViewById(R.id.profile_textView_likes);
        getNumbers();
    }

    public void openUploadOptions() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View view_uploadProductOrPublishPost = LayoutInflater.from(getActivity()).inflate(R.layout.bottomsheet_upload_product_or_publish_post, null);
        bottomSheetDialog.setContentView(view_uploadProductOrPublishPost);
        bottomSheetDialog.show();
        layout_uploadProduct = view_uploadProductOrPublishPost.findViewById(R.id.uploadproductorpublishpost_layout_uploadproduct);
        layout_uploadProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadProductActivity.class);
                startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });
        layout_publishPost = view_uploadProductOrPublishPost.findViewById(R.id.uploadproductorpublishpost_layout_publishpost);
        layout_publishPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PublishPostActivity.class);
                startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void getNumbers() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_account_by_id.php?user_id=" + CurrentAccount.getAccount().getId() + "&current_user=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        requireActivity().runOnUiThread(() -> {
                            try{
                                textView_followingCount.setText(Integer.toString(jsonObject.getInt("following_count")));
                                textView_followersCount.setText(Integer.toString(jsonObject.getInt("follower_count")));
                                textView_likesCount.setText(Integer.toString(jsonObject.getInt("like_count")));
                            }
                            catch (Exception e) {
                            }
                        });
                        getPostCount();
                    } catch (Exception e) {
                        requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getPostCount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_post_count_by_user.php?current_user=" + CurrentAccount.getAccount().getId();

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
        if(!isFragmentActive || reccursion == end) {
            getPostLikedCount();
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_post_profile.php?post_number=" + reccursion + "&current_user=" + CurrentAccount.getAccount().getId();

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

                        requireActivity().runOnUiThread(() -> {
                            if (!isFragmentActive)
                                return;
                            profilePosts.add(post);
                            adapter_posts.notifyDataSetChanged();
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

    private void getPostLikedCount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_post_liked_count_by_user.php?current_user=" + CurrentAccount.getAccount().getId();

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
                        int total_post_liked = jsonResponse.getInt("total_post_liked");
                        loadLikedPosts(0, total_post_liked);

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

    private void loadLikedPosts(int reccursion, int end) {
        if(!isFragmentActive || reccursion == end) {
            //finish
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_post_liked_profile.php?post_number=" + reccursion + "&current_user=" + CurrentAccount.getAccount().getId();

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

                        requireActivity().runOnUiThread(() -> {
                            if (!isFragmentActive)
                                return;
                            likedPosts.add(post);
                            adapter_likes.notifyDataSetChanged();
                        });
                        loadLikedPosts(reccursion + 1, end);

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