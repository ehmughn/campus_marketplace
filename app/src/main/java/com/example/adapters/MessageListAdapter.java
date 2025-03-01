package com.example.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.objects.Account;
import com.example.objects.ChatRoom;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;
import com.example.testproject2.ChatRoomActivity;
import com.example.testproject2.R;
import com.example.testproject2.VisitProfileActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ChatRoom> rooms;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    public MessageListAdapter(Context context, ArrayList<ChatRoom> rooms) {
        this.context = context;
        this.rooms = rooms;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_message_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatRoom room = rooms.get(position);
        holder.imageView_image.setImageBitmap(EncodeImage.decodeFromStringBlob(room.getImage()));
        holder.textView_name.setText(room.getName());
        holder.textView_product.setText(room.getProductName());
        holder.textView_message.setText(room.getMessage());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatRoomActivity.class);
                intent.putExtra("productId", room.getProductId());
                intent.putExtra("alreadyRoomId", room.getRoomId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layout;
        public ImageView imageView_image;
        public TextView textView_name;
        public TextView textView_product;
        public TextView textView_message;

        public ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.messageList_layout);
            imageView_image = view.findViewById(R.id.messageList_imageView_image);
            textView_name = view.findViewById(R.id.messageList_textView_user);
            textView_product = view.findViewById(R.id.messageList_textView_product);
            textView_message = view.findViewById(R.id.messageList_textView_lastMessage);
        }
    }
}
