package com.example.testproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.HomeCategoryAdapter;
import com.example.adapters.HomePostsAdapter;
import com.example.fragments.ExploreFragment;
import com.example.fragments.HomeFragment;
import com.example.fragments.InboxFragment;
import com.example.fragments.ProfileFragment;
import com.example.fragments.UploadFragment;
import com.example.objects.Categories;
import com.example.objects.Post;
import com.example.testproject2.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private FloatingActionButton button_upload;
    private HomeFragment homeFragment;
    private ExploreFragment exploreFragment;
    private UploadFragment uploadFragment;
    private InboxFragment inboxFragment;
    private ProfileFragment profileFragment;

    private TextView profile_textView_uploads;
    private TextView profile_textView_likes;
    private GridLayout profile_gridLayout_uploads;
    private TextView profile_textView_noLikes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        homeFragment = new HomeFragment();
        exploreFragment = new ExploreFragment();
        uploadFragment = new UploadFragment();
        inboxFragment = new InboxFragment();
        profileFragment = new ProfileFragment();
        replaceFragment(homeFragment);
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home)
                    replaceFragment(homeFragment);
                else if (item.getItemId() == R.id.explore)
                    replaceFragment(exploreFragment);
                else if (item.getItemId() == R.id.inbox)
                    replaceFragment(inboxFragment);
                else if (item.getItemId() == R.id.profile)
                    replaceFragment(profileFragment);
                return true;
            }
        });
        profile_textView_uploads = profileFragment.textView_uploads;
        profile_textView_likes = profileFragment.textView_likes;
        profile_gridLayout_uploads = profileFragment.gridLayout_uploads;
        profile_textView_noLikes = profileFragment.textView_noLikes;
    }

    public void toProfile(View v) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment);
        fragmentTransaction.commit();
    }

    public void toUploads(View v) {
        profileFragment.textView_uploads.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_bold));
        profileFragment.textView_likes.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_regular));
        profileFragment.gridLayout_uploads.setVisibility(View.VISIBLE);
        profileFragment.textView_noLikes.setVisibility(View.INVISIBLE);
    }

    public void toLikes(View v) {
        profileFragment.textView_uploads.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_regular));
        profileFragment.textView_likes.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.montserrat_bold));
        profileFragment.gridLayout_uploads.setVisibility(View.INVISIBLE);
        profileFragment.textView_noLikes.setVisibility(View.VISIBLE);
    }
}