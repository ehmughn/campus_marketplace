package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.objects.Notification;
import com.example.static_classes.EncodeImage;
import com.example.testproject2.PostActivity;
import com.example.testproject2.R;
import com.example.testproject2.VisitProfileActivity;

import java.util.ArrayList;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Notification> notifications;

    public NotificationsListAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_notifications_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.imageView_image.setImageBitmap(EncodeImage.decodeFromStringBlob(notification.getImage()));
        holder.textView_name.setText(notification.getName());
        if(notification.getType().equals("LIKE"))
            holder.textView_message.setText(" liked your post.");
        else if(notification.getType().equals("FOLLOW"))
            holder.textView_message.setText(" followed you.");
        else if(notification.getType().equals("REVIEW"))
            holder.textView_message.setText(" reviewed your post.");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notification.getType().equals("LIKE")) {
                    Intent intent = new Intent(context, PostActivity.class);
                    intent.putExtra("postId", notification.getReference());
                    context.startActivity(intent);
                }
                else if(notification.getType().equals("FOLLOW")) {
                    Intent intent = new Intent(context, VisitProfileActivity.class);
                    intent.putExtra("userId", notification.getReference());
                    context.startActivity(intent);
                }
                else if(notification.getType().equals("REVIEW")) {
                    Intent intent = new Intent(context, PostActivity.class);
                    intent.putExtra("postId", notification.getReference());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layout;
        public ImageView imageView_image;
        public TextView textView_name;
        public TextView textView_message;
        public ViewHolder(View notificationView) {
            super(notificationView);
            layout = notificationView.findViewById(R.id.notificationList_layout_notification);
            imageView_image = notificationView.findViewById(R.id.notificationList_imageView_image);
            textView_name = notificationView.findViewById(R.id.notificationList_textView_user);
            textView_message = notificationView.findViewById(R.id.notificationList_textView_message);
        }
    }
}
