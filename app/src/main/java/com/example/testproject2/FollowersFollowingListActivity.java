package com.example.testproject2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.AccountsListAdapter;
import com.example.objects.Account;
import com.example.static_classes.CurrentAccount;
import com.example.temporary_values.TemporaryAccountList;

import java.util.ArrayList;

public class FollowersFollowingListActivity extends AppCompatActivity {

    private ImageView imageView_profilePicture;
    private TextView textView_name;
    private TextView textView_followers;
    private TextView textView_following;
    private LinearLayout layout_line_followers;
    private LinearLayout layout_line_following;
    private TextView textView_count;
    private RecyclerView recyclerView_followers;
    private RecyclerView recyclerView_following;
    private ArrayList<Account> accounts_followers;
    private ArrayList<Account> accounts_following;
    private AccountsListAdapter adapter_followers;
    private AccountsListAdapter adapter_following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_followers_following_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageView_profilePicture = findViewById(R.id.followersFollowing_imageView_profilePicture);
        textView_name = findViewById(R.id.followersFollowing_textView_name);
        textView_followers = findViewById(R.id.followersFollowing_textView_followers);
        textView_followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFollowers();
            }
        });
        textView_following = findViewById(R.id.followersFollowing_textView_following);
        textView_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFollowing();
            }
        });
        layout_line_followers = findViewById(R.id.followersFollowing_line_followers);
        layout_line_following =findViewById(R.id.followersFollowing_line_following);
        textView_count = findViewById(R.id.followersFollowing_textView_count);
        recyclerView_followers = findViewById(R.id.followersFollowing_recyclerView_followersAccountsList);
        recyclerView_followers.setLayoutManager(new LinearLayoutManager(this));
        accounts_followers = new ArrayList<>();
        for(int i = 0; i < TemporaryAccountList.size(); i++) {
            if(i != CurrentAccount.getAccount().getId()) {
                accounts_followers.add(TemporaryAccountList.getAccount(i));
            }
        }
        adapter_followers = new AccountsListAdapter(this, accounts_followers);
        recyclerView_followers.setAdapter(adapter_followers);
        recyclerView_following = findViewById(R.id.followersFollowing_recyclerView_followingAccountsList);
        recyclerView_following.setLayoutManager(new LinearLayoutManager(this));
        accounts_following = new ArrayList<>();
//        for(int i = 0; i < TemporaryAccountList.size(); i++) {
//            if(TemporaryAccountList.getAccount(i).isFollowed() && i != CurrentAccount.getAccount().getId()) {
//                accounts_following.add(TemporaryAccountList.getAccount(i));
//            }
//        }
        adapter_following = new AccountsListAdapter(this, accounts_following);
        recyclerView_following.setAdapter(adapter_following);
        Bundle extras = getIntent().getExtras();
        boolean inFollowers = extras.getBoolean("inFollowers");
        if(inFollowers)
            toFollowers();
        else
            toFollowing();
    }

    private void toFollowers() {
        textView_followers.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_bold));
        textView_following.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_regular));
        layout_line_followers.setVisibility(View.VISIBLE);
        layout_line_following.setVisibility(View.INVISIBLE);
        recyclerView_followers.setVisibility(View.VISIBLE);
        recyclerView_following.setVisibility(View.INVISIBLE);
        textView_count.setText(accounts_followers.size() + " Followers");
    }

    private void toFollowing() {
        textView_followers.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_regular));
        textView_following.setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_bold));
        layout_line_followers.setVisibility(View.INVISIBLE);
        layout_line_following.setVisibility(View.VISIBLE);
        recyclerView_followers.setVisibility(View.INVISIBLE);
        recyclerView_following.setVisibility(View.VISIBLE);
        textView_count.setText(accounts_following.size() + " Following");
    }
}