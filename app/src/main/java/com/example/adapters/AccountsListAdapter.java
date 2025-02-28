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
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;
import com.example.testproject2.R;
import com.example.testproject2.VisitProfileActivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccountsListAdapter extends RecyclerView.Adapter<AccountsListAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Account> accounts;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    public AccountsListAdapter(Context context, Activity activity, ArrayList<Account> accounts) {
        this.context = context;
        this.activity = activity;
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
        holder.imageView_profilePicture.setImageBitmap(EncodeImage.decodeFromStringBlob(account.getImage()));
        holder.textView_name.setText(account.getName());
        if(account.isFollowed()) {
            holder.button_follow.setVisibility(View.INVISIBLE);
            holder.button_unfollow.setVisibility(View.VISIBLE);
        }
        else {
            holder.button_follow.setVisibility(View.VISIBLE);
            holder.button_unfollow.setVisibility(View.INVISIBLE);
        }
        if(account.getId() == CurrentAccount.getAccount().getId()) {
            holder.button_follow.setVisibility(View.INVISIBLE);
            holder.button_unfollow.setVisibility(View.INVISIBLE);
        }
        holder.button_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setFollowed(true);
                followUser(account);
                notifyDataSetChanged();
            }
        });
        holder.button_unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setFollowed(false);
                unfollowUser(account);
                notifyDataSetChanged();
            }
        });
        holder.layout_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account.getId() != CurrentAccount.getAccount().getId()) {
                    Intent intent = new Intent(context, VisitProfileActivity.class);
                    intent.putExtra("userId", account.getId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layout;
        public LinearLayout layout_account;
        public ImageView imageView_profilePicture;
        public TextView textView_name;
        public Button button_follow;
        public Button button_unfollow;

        public ViewHolder(View accountsListView) {
            super(accountsListView);
            layout = accountsListView.findViewById(R.id.accountsList_layout);
            layout_account = accountsListView.findViewById(R.id.accountsList_layout_account);
            imageView_profilePicture = accountsListView.findViewById(R.id.accountsList_imageView_profilePicture);
            textView_name = accountsListView.findViewById(R.id.accountsList_textView_name);
            button_follow = accountsListView.findViewById(R.id.accountsList_button_follow);
            button_unfollow = accountsListView.findViewById(R.id.accountsList_button_unfollow);
        }
    }

    private void unfollowUser(Account account) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/unfollow_user.php";

        RequestBody body = new FormBody.Builder()
                .add("follower_id", Integer.toString(CurrentAccount.getAccount().getId()))
                .add("following_id", Integer.toString(account.getId()))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    final String responseData = response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful() && responseData.contains("success")) {
                                //
                            } else {
                                Toast.makeText(activity, "Unfollow Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void followUser(Account account) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/follow_user.php";

        RequestBody body = new FormBody.Builder()
                .add("follower_id", Integer.toString(CurrentAccount.getAccount().getId()))
                .add("following_id", Integer.toString(account.getId()))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    final String responseData = response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful() && responseData.contains("success")) {

                            } else {
                                Toast.makeText(activity, "Follow Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
