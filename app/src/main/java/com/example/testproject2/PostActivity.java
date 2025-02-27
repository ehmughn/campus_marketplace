package com.example.testproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.PostReviewAdapter;
import com.example.objects.Account;
import com.example.objects.Post;
import com.example.objects.Product;
import com.example.objects.Reviews;
import com.example.objects.Variation;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.Decimals;
import com.example.static_classes.EncodeImage;
import com.example.static_classes.ShowCurrentPost;
import com.example.static_classes.ShowCurrentProfile;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostActivity extends AppCompatActivity {

    private ImageView imageView_image;
    private TextView textView_title;
    private TextView textView_price;
    private TextView textView_description;
    private Button button_detailsPressed;
    private Button button_detailsUnpressed;
    private Button button_reviewsPressed;
    private Button button_reviewsUnpressed;
    private LinearLayout layout_details;
    private LinearLayout layout_reviews;
    private RecyclerView recyclerView_reviews;
    private PostReviewAdapter adapter_postReview;
    private ImageView imageView_profilePicture;
    private TextView textView_sellerName;
    private LinearLayout layout_seller;
    private Button button_addToCart;
    private TextView bottomSheet_textView_productName;
    private ImageView bottomSheet_imageView_productImage;
    private TextView bottomSheet_textView_productPrice;
    private TextView bottomSheet_textView_productStockAvailable;
    private CardView bottomSheet_cardView_stockIncrement;
    private TextView bottomSheet_textView_productStockSelected;
    private CardView bottomSheet_cardView_stockDecrement;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    private ArrayList<Reviews> example_reviews;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageView_image = findViewById(R.id.post_imageView_image);
        textView_title = findViewById(R.id.post_textView_title);
        textView_price = findViewById(R.id.post_textView_price);
        textView_description = findViewById(R.id.post_textView_description);
        button_detailsUnpressed = findViewById(R.id.post_button_detailsUnpressed);
        button_detailsPressed = findViewById(R.id.post_button_detailsPressed);
        button_detailsUnpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDetails();
            }
        });
        button_reviewsUnpressed = findViewById(R.id.post_button_reviewsUnpressed);
        button_reviewsPressed = findViewById(R.id.post_button_reviewsPressed);
        button_reviewsUnpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toReviews();
            }
        });
        layout_details = findViewById(R.id.post_layout_details);
        layout_reviews = findViewById(R.id.post_layout_reviews);
        recyclerView_reviews = findViewById(R.id.post_recyclerView_reviews);
        imageView_profilePicture = findViewById(R.id.post_imageView_profile_picture);
        textView_sellerName = findViewById(R.id.post_textView_sellerName);
        layout_seller = findViewById(R.id.post_layout_seller);
        layout_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCurrentProfile.setAccount(ShowCurrentPost.getSeller_id());
                Intent intent = new Intent(PostActivity.this, VisitProfileActivity.class);
                startActivity(intent);
            }
        });
        button_addToCart = findViewById(R.id.post_button_addToCart);
        button_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PostActivity.this);
                View view_addToCartBuy = LayoutInflater.from(PostActivity.this).inflate(R.layout.bottomsheet_addtocartbuy, null);
                bottomSheetDialog.setContentView(view_addToCartBuy);
                bottomSheetDialog.show();
                bottomSheet_textView_productName = view_addToCartBuy.findViewById(R.id.addtocartbuy_textView_productName);
                bottomSheet_imageView_productImage = view_addToCartBuy.findViewById(R.id.addtocartbuy_imageView_productImage);
                bottomSheet_textView_productPrice = view_addToCartBuy.findViewById(R.id.addtocartbuy_textView_productPrice);
                bottomSheet_textView_productStockAvailable = view_addToCartBuy.findViewById(R.id.addtocartbuy_textView_productStocksAvailable);
                bottomSheet_cardView_stockDecrement = view_addToCartBuy.findViewById(R.id.addtocartbuy_cardView_stockDecrement);
                bottomSheet_textView_productStockSelected = view_addToCartBuy.findViewById(R.id.addtocartbuy_textView_productStockSelected);
                bottomSheet_cardView_stockIncrement = view_addToCartBuy.findViewById(R.id.addtocartbuy_cardView_stockIncrement);
                bottomSheet_textView_productName.setText(post.getProduct().getName());
                bottomSheet_imageView_productImage.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getVariations().get(0).getImage()));
                bottomSheet_textView_productPrice.setText("₱" + Decimals.FORMAT_PRICE.format(post.getProduct().getVariations().get(0).getPrice()));
                bottomSheet_textView_productStockAvailable.setText("Stocks: " + post.getProduct().getVariations().get(0).getStock());
                bottomSheet_cardView_stockDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Integer.parseInt(bottomSheet_textView_productStockSelected.getText().toString()) > 1)
                            bottomSheet_textView_productStockSelected.setText(Integer.toString(Integer.parseInt(bottomSheet_textView_productStockSelected.getText().toString()) - 1));
                    }
                });
                bottomSheet_textView_productStockSelected.setText("1");
                bottomSheet_cardView_stockIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Integer.parseInt(bottomSheet_textView_productStockSelected.getText().toString()) < ShowCurrentPost.getStockCount())
                            bottomSheet_textView_productStockSelected.setText(Integer.toString(Integer.parseInt(bottomSheet_textView_productStockSelected.getText().toString()) + 1));
                    }
                });
            }
        });
        example_reviews = new ArrayList<>();
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        example_reviews.add(new Reviews());
        int postId = getIntent().getIntExtra("postId", 0);
        getProductData(postId);
    }

    private void getProductData(int postId) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/post_select_product.php?post_id=" + postId + "&current_user=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(PostActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        ArrayList<Variation> variations = new ArrayList<>();
                        post = new Post(
                                jsonObject.getInt("post_id"),
                                jsonObject.getString("title"),
                                jsonObject.getString("description"),
                                new Product(
                                        jsonObject.getInt("product_id"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("category"),
                                        new Account(
                                                jsonObject.getInt("seller_id"),
                                                jsonObject.getString("seller_image"),
                                                jsonObject.getString("seller_name"),
                                                "not needed"
                                        ),
                                        variations
                                ),
                                jsonObject.getInt("like_count"),
                                (jsonObject.getInt("liked_by_current_user") == 1),
                                example_reviews
                        );
                        // get the variations
                        getVariationsCount();
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(PostActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(PostActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getVariationsCount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_variant_count.php?product_id=" + post.getProduct().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(PostActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        int totalVariants = jsonResponse.getInt("total_variants");
                        getVariations(0, totalVariants);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(PostActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(PostActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void getVariations(int recursion, int end) {
        if(recursion == end) {
            setValues();
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/post_select_variant.php?product_id=" + post.getProduct().getId() + "&variant_number=" + recursion;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(PostActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        post.getProduct().getVariations().add(new Variation(
                                jsonObject.getString("variant_name"),
                                jsonObject.getDouble("variant_cost"),
                                jsonObject.getInt("variant_stock"),
                                jsonObject.getString("variant_image")
                        ));
                        getVariations(recursion + 1, end);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(PostActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(PostActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void setValues() {
        runOnUiThread(() -> {
            Toast.makeText(PostActivity.this, "Retrieved all the data", Toast.LENGTH_SHORT).show();
            imageView_image.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getVariations().get(0).getImage()));
            textView_title.setText(post.getProduct().getVariations().get(0).getName());
            textView_price.setText("₱" + Decimals.FORMAT_PRICE.format(post.getProduct().getVariations().get(0).getPrice()));
            textView_description.setText(post.getDescription());
            imageView_profilePicture.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getAccount().getImage()));
            textView_sellerName.setText(post.getProduct().getAccount().getName());
            adapter_postReview = new PostReviewAdapter(this, post, example_reviews);
            recyclerView_reviews.setLayoutManager(new LinearLayoutManager(this));
            recyclerView_reviews.setAdapter(adapter_postReview);
        });
//        imageView_image.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getVariations().get(0).getImage()));
//        textView_title.setText(post.getProduct().getVariations().get(0).getName());
    }

    private void toDetails() {
        layout_details.setVisibility(View.VISIBLE);
        layout_reviews.setVisibility(View.INVISIBLE);
        button_detailsPressed.setVisibility(View.VISIBLE);
        button_detailsUnpressed.setVisibility(View.INVISIBLE);
        button_reviewsPressed.setVisibility(View.INVISIBLE);
        button_reviewsUnpressed.setVisibility(View.VISIBLE);
    }

    private void toReviews() {
        layout_details.setVisibility(View.INVISIBLE);
        layout_reviews.setVisibility(View.VISIBLE);
        button_detailsPressed.setVisibility(View.INVISIBLE);
        button_detailsUnpressed.setVisibility(View.VISIBLE);
        button_reviewsPressed.setVisibility(View.VISIBLE);
        button_reviewsUnpressed.setVisibility(View.INVISIBLE);
    }
}