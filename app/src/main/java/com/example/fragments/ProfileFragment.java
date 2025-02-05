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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapters.HomePostsAdapter;
import com.example.objects.Post;
import com.example.objects.Reviews;
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

        // a bunch of temporary posts until we get to know how to deal with databases
        profilePosts.add(new Post(R.drawable.cookies, 25, "Cookies", 0, "Cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! I want cookies! " , 50, example_reviews));
        profilePosts.add(new Post(R.drawable.notes, 250, "Notes", 1, "We're no strangers to love. You know the rules and so do I. A full commitment's what I'm thinkin' of. You wouldn't get this from any other guy. I just wanna tell you how I'm feeling. Gotta make you understand. Never gonna give you up. Never gonna let you down. Never gonna run around and desert you. Never gonna make you cry. Never gonna say goodbye. Never gonna tell a lie and hurt you.", 5, example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10, example_reviews));
        profilePosts.add(new Post(R.drawable.burger, 70, "Burger", 0, "I hate jollibee, I hate mcdonalds, I hate burger king, I hate zarks, I hate popeyes, I hate angels burger, Ikaw lang ang gusto ko",30,example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,example_reviews));
        profilePosts.add(new Post(R.drawable.uniform, 400, "Uniform", 2, "Lagi daw kasi out of stock yung bulldogs exchange so ito binebenta ko na yung sakin", 10,example_reviews));

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
    }
}