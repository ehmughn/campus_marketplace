package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.objects.Account;
import com.example.testproject2.R;

import java.util.ArrayList;

public class AccountsListAdapter extends RecyclerView.Adapter<AccountsListAdapter.ViewHolder> {

    private Context context;

    private ArrayList<Account> accounts;

    public AccountsListAdapter(Context context, ArrayList<Account> accounts) {
        this.context = context;
        this.accounts = accounts;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_accounts_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.imageView_profilePicture.setImageResource(account.getImage());
        holder.textView_name.setText(account.getName());
        if(account.isFollowed()) {
            holder.button_follow.setVisibility(View.INVISIBLE);
            holder.button_unfollow.setVisibility(View.VISIBLE);
        }
        else {
            holder.button_follow.setVisibility(View.VISIBLE);
            holder.button_unfollow.setVisibility(View.INVISIBLE);
        }
        holder.button_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setFollowed(true);
                notifyDataSetChanged();
            }
        });
        holder.button_unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setFollowed(false);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layout;
        public ImageView imageView_profilePicture;
        public TextView textView_name;
        public Button button_follow;
        public Button button_unfollow;

        public ViewHolder(View accountsListView) {
            super(accountsListView);
            layout = accountsListView.findViewById(R.id.accountsList_layout);
            imageView_profilePicture = accountsListView.findViewById(R.id.accountsList_imageView_profilePicture);
            textView_name = accountsListView.findViewById(R.id.accountsList_textView_name);
            button_follow = accountsListView.findViewById(R.id.accountsList_button_follow);
            button_unfollow = accountsListView.findViewById(R.id.accountsList_button_unfollow);
        }
    }
}
