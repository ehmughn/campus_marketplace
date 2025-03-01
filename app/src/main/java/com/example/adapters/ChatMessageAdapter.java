package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.objects.Message;
import com.example.objects.Product;
import com.example.objects.VariationForExploreFragment;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.EncodeImage;
import com.example.testproject2.PostActivity;
import com.example.testproject2.R;
import com.example.testproject2.databinding.TemplateChatMessageReceivedBinding;
import com.example.testproject2.databinding.TemplateChatMessageSentBinding;
import com.example.testproject2.databinding.TemplateChatMessageSystemBinding;

import java.util.ArrayList;

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Message> messages;
    private Product product;
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;
    private static final int VIEW_TYPE_SYSTEM = 3;

    public ChatMessageAdapter(Context context, Product product, ArrayList<Message> messages) {
        this.context = context;
        this.product = product;
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getType().equals("SENDER"))
            return VIEW_TYPE_SENT;
        else if (message.getType().equals("RECEIVER"))
            return VIEW_TYPE_RECEIVED;
        return VIEW_TYPE_SYSTEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT)
            return new SentMessageViewHolder(TemplateChatMessageSentBinding.inflate(LayoutInflater.from(context), parent, false));
        else if (viewType == VIEW_TYPE_RECEIVED)
            return new ReceivedMessageViewHolder(TemplateChatMessageReceivedBinding.inflate(LayoutInflater.from(context), parent, false));
        return new SystemMessageViewHolder(TemplateChatMessageSystemBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if(viewType == 1) {
            ((SentMessageViewHolder) holder).setData(messages.get(position));
        }
        else if(viewType == 2) {
            ((ReceivedMessageViewHolder) holder).setData(messages.get(position));
        }
        else {
            ((SystemMessageViewHolder) holder).setData(messages.get(position), product);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {

        private final TemplateChatMessageSentBinding binding;

        SentMessageViewHolder(TemplateChatMessageSentBinding templateChatMessageSentBinding) {
            super(templateChatMessageSentBinding.getRoot());
            binding = templateChatMessageSentBinding;
        }

        void setData(Message message) {
            binding.textMessage.setText(message.getMessage());
            binding.textDateTime.setText(message.getDatetime());
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        private final TemplateChatMessageReceivedBinding binding;

        ReceivedMessageViewHolder(TemplateChatMessageReceivedBinding templateChatMessageReceivedBinding) {
            super(templateChatMessageReceivedBinding.getRoot());
            binding = templateChatMessageReceivedBinding;
        }

        void setData(Message message) {
            binding.textMessage.setText(message.getMessage());
            binding.textDateTime.setText(message.getDatetime());
            binding.imageProfle.setImageBitmap(EncodeImage.decodeFromStringBlob(message.getImage()));
        }
    }

    static class SystemMessageViewHolder extends RecyclerView.ViewHolder {
        private final TemplateChatMessageSystemBinding binding;

        SystemMessageViewHolder(TemplateChatMessageSystemBinding templateChatMessageSystemBinding) {
            super(templateChatMessageSystemBinding.getRoot());
            binding = templateChatMessageSystemBinding;
        }

        void setData(Message message, Product product) {
            binding.textName.setText(product.getName());
            binding.image.setImageBitmap(EncodeImage.decodeFromStringBlob(message.getImage()));
        }
    }
}
