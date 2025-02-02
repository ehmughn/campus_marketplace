package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testproject2.ProfileActivity;
import com.example.testproject2.R;
import com.example.testproject2.UploadActivity;

public class ProfileFragment extends Fragment {

    public TextView textView_uploads;
    public TextView textView_likes;
    public GridLayout gridLayout_uploads;
    public TextView textView_noLikes;
    public Button profile_button_uploadItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView_uploads = view.findViewById(R.id.text_uploads);
        textView_likes = view.findViewById(R.id.text_likes);
        gridLayout_uploads = view.findViewById(R.id.profle_uploads);
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
    }
}