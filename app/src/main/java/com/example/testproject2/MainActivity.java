package com.example.testproject2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fragments.ExploreFragment;
import com.example.fragments.HomeFragment;
import com.example.fragments.InboxFragment;
import com.example.fragments.ProfileFragment;
import com.example.fragments.UploadFragment;
import com.example.static_classes.CurrentAccount;
import com.example.testproject2.databinding.ActivityMainBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private FloatingActionButton button_upload;
    private LinearLayout layout_uploadProduct;
    private LinearLayout layout_publishPost;


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
        boolean toEditProfile = getIntent().getBooleanExtra("toEditProfile", false);
        if(toEditProfile) {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            startActivity(intent);
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("logged_account", CurrentAccount.getAccount().getId());
        editor.putString("password", CurrentAccount.getAccount().getPassword());
        editor.apply();
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home)
                    replaceFragment(new HomeFragment());
                else if (item.getItemId() == R.id.explore)
                    replaceFragment(new ExploreFragment());
                else if (item.getItemId() == R.id.inbox)
                    replaceFragment(new InboxFragment());
                else if (item.getItemId() == R.id.profile)
                    replaceFragment(new ProfileFragment());
                return true;
            }
        });
        binding.mainButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUploadOptions();
            }
        });
    }

    public void openUploadOptions() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        View view_uploadProductOrPublishPost = LayoutInflater.from(MainActivity.this).inflate(R.layout.bottomsheet_upload_product_or_publish_post, null);
        bottomSheetDialog.setContentView(view_uploadProductOrPublishPost);
        bottomSheetDialog.show();
        layout_uploadProduct = view_uploadProductOrPublishPost.findViewById(R.id.uploadproductorpublishpost_layout_uploadproduct);
        layout_uploadProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploadProductActivity.class);
                startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });
        layout_publishPost = view_uploadProductOrPublishPost.findViewById(R.id.uploadproductorpublishpost_layout_publishpost);
        layout_publishPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PublishPostActivity.class);
                startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment);
        fragmentTransaction.commit();
    }
}