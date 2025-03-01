package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testproject2.ChatRoomActivity;
import com.example.testproject2.MessageReceivedListActivity;
import com.example.testproject2.MessageSentListActivity;
import com.example.testproject2.NotificationActivity;
import com.example.testproject2.R;

public class InboxFragment extends Fragment {

    private LinearLayout layout_notifications;
    private LinearLayout layout_messageReceived;
    private LinearLayout layout_messageSent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout_post for this fragment
        return inflater.inflate(R.layout.fragment_inbox, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        layout_notifications = view.findViewById(R.id.inbox_layout_notification);
        layout_messageReceived = view.findViewById(R.id.inbox_layout_received);
        layout_messageSent = view.findViewById(R.id.inbox_layout_sent);

        layout_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        layout_messageReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageReceivedListActivity.class);
                startActivity(intent);
            }
        });

        layout_messageSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageSentListActivity.class);
                startActivity(intent);
            }
        });

    }
}