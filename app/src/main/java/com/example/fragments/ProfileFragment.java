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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapters.HomePostsAdapter;
import com.example.objects.Post;
import com.example.objects.Reviews;
import com.example.static_classes.CurrentAccount;
import com.example.temporary_values.TemporaryAccountList;
import com.example.temporary_values.TemporaryPostList;
import com.example.testproject2.FollowersFollowingListActivity;
import com.example.testproject2.R;
import com.example.testproject2.UploadActivity;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    public TextView textView_uploads;
    public TextView textView_likes;
    public TextView textView_noLikes;
    public Button profile_button_uploadItems;
    public ArrayList<Post> profilePosts;
    public ArrayList<Reviews> example_reviews;
    public RecyclerView recyclerView_uploads;
    public HomePostsAdapter adapter_posts;
    public LinearLayout layout_followers;
    public LinearLayout layout_following;
    public ImageView imageView_profilePicture;
    public TextView textView_name;
    public TextView textView_bio;
    public TextView textView_followersCount;
    public TextView textView_followingCount;
    public TextView textView_likesCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        example_reviews = new ArrayList<>();
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());

        recyclerView_uploads = view.findViewById(R.id.profile_recyclerView_uploads);
        recyclerView_uploads.setLayoutManager(new LinearLayoutManager(getContext()));
        profilePosts = new ArrayList<>();

        for(int i = 0; i < TemporaryPostList.size(); i++) {
            if(TemporaryPostList.getPost(i).getPoster_id() == CurrentAccount.getAccount().getId())
                profilePosts.add(TemporaryPostList.getPost(i));
        }

        adapter_posts = new HomePostsAdapter(getContext(), profilePosts);
        recyclerView_uploads.setAdapter(adapter_posts);


        textView_uploads = view.findViewById(R.id.text_uploads);
        textView_uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_uploads.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.montserrat_bold));
                textView_likes.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.montserrat_regular));
                recyclerView_uploads.setVisibility(View.VISIBLE);
                textView_noLikes.setVisibility(View.INVISIBLE);
            }
        });
        textView_likes = view.findViewById(R.id.text_likes);
        textView_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_uploads.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.montserrat_regular));
                textView_likes.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.montserrat_bold));
                recyclerView_uploads.setVisibility(View.INVISIBLE);
                textView_noLikes.setVisibility(View.VISIBLE);
            }
        });
        textView_noLikes = view.findViewById(R.id.profle_likes);
        profile_button_uploadItems = view.findViewById(R.id.profile_button_uploadItems);
        profile_button_uploadItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), UploadActivity.class);
                    startActivity(intent);
                } catch(Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
        imageView_profilePicture.setImageResource(CurrentAccount.getAccount().getImage());
        textView_name = view.findViewById(R.id.profile_textView_name);
        textView_name.setText(CurrentAccount.getAccount().getName());
        textView_bio = view.findViewById(R.id.profile_textView_bio);
        textView_bio.setText(CurrentAccount.getAccount().getBio());
        textView_followersCount = view.findViewById(R.id.profile_textView_followers);
        textView_followersCount.setText(Integer.toString(TemporaryAccountList.size() - 1));
        textView_followingCount = view.findViewById(R.id.profile_textView_following);
        int followingCount = 0;
        for(int i = 0; i < TemporaryAccountList.size(); i++) {
            if(TemporaryAccountList.getAccount(i).isFollowed() && i != CurrentAccount.getAccount().getId()) {
                followingCount++;
            }
        }
        textView_followingCount.setText(Integer.toString(followingCount));
        textView_likesCount = view.findViewById(R.id.profile_textView_likes);
    }
}