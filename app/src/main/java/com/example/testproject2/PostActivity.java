package com.example.testproject2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.material.textfield.TextInputLayout;

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

    private ImageView imageView_variantImage;
    private TextView textView_variantName;
    private TextView textView_variantPrice;
    private TextView textView_variantStock;
    private TextView textView_title;
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
    private Button button_message;
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
    private ArrayList<Reviews> reviews;
    private Post post;
    private TextInputLayout textField_variantName;
    private AutoCompleteTextView autoComplete_variantName;
    private ArrayList<String> variationList;
    private ArrayAdapter<String> adapterVariationList;

    private LayoutInflater inflaterPleaseWait;
    private View dialogPleaseWaitView;
    private AlertDialog.Builder builderDialogPleaseWait;
    private AlertDialog dialogPleaseWait;
    private TextView dialogPleaseWait_textView_progress;
    private int productId;

    private ImageView imageView_review;
    private TextView textView_seller;

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
        imageView_variantImage = findViewById(R.id.post_imageView_variantImage);
        textView_variantName = findViewById(R.id.post_textView_variantName);
        textView_variantPrice = findViewById(R.id.post_textView_variantPrice);
        textView_variantStock = findViewById(R.id.post_textView_variantStock);
        textView_title = findViewById(R.id.post_textView_title);
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
        imageView_profilePicture = findViewById(R.id.post_imageView_profilePicture);
        textView_sellerName = findViewById(R.id.post_textView_sellerName);
        button_message = findViewById(R.id.post_button_message);
        button_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, ChatRoomActivity.class);
                intent.putExtra("productId", productId);
                startActivity(intent);
            }
        });

        inflaterPleaseWait = getLayoutInflater();
        dialogPleaseWaitView = inflaterPleaseWait.inflate(R.layout.dialog_please_wait, null);
        builderDialogPleaseWait = new AlertDialog.Builder(this);
        builderDialogPleaseWait.setView(dialogPleaseWaitView).setCancelable(false);
        dialogPleaseWait = builderDialogPleaseWait.create();
        dialogPleaseWait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPleaseWait_textView_progress = dialogPleaseWaitView.findViewById(R.id.dialogPleaseWait_textView_progress);

        reviews = new ArrayList<>();
        adapter_postReview = new PostReviewAdapter(this, reviews);
        recyclerView_reviews = findViewById(R.id.post_recyclerView_reviews);
        recyclerView_reviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_reviews.setAdapter(adapter_postReview);
        int postId = getIntent().getIntExtra("postId", 0);
        getProductData(postId);
        textField_variantName = findViewById(R.id.post_textField_variantName);
        autoComplete_variantName = findViewById(R.id.post_autoComplete_variantName);

        imageView_review = findViewById(R.id.post_imageView_review);
        imageView_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(post.getProduct().getAccount().getId() == CurrentAccount.getAccount().getId())
                    return;
                Intent intent = new Intent(PostActivity.this, ReviewActivity.class);
                intent.putExtra("product_name", post.getProduct().getName());
                intent.putExtra("product_id", post.getProduct().getId());
                intent.putExtra("seller_id", post.getProduct().getAccount().getId());
                intent.putExtra("post_id", post.getId());
                int variationCount = post.getProduct().getVariations().size();
                intent.putExtra("variation_count", variationCount);
                for(int i = 0; i < variationCount; i++) {
                    intent.putExtra("variationId" + i, post.getProduct().getVariations().get(i).getId());
                    intent.putExtra("variationName" + i, post.getProduct().getVariations().get(i).getName());
                }
                startActivity(intent);
            }
        });
        textView_seller = findViewById(R.id.post_textView_seller);
    }

    private void getProductData(int postId) {
        dialogPleaseWait.show();
        dialogPleaseWait_textView_progress.setText("Please wait while we set up all the details for you.");
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
                                                jsonObject.getString("first_name"),
                                                jsonObject.getString("last_name"),
                                                "not needed",
                                                "not needed",
                                                "not needed",
                                                "not needed"
                                        ),
                                        variations
                                ),
                                jsonObject.getInt("like_count"),
                                (jsonObject.getInt("liked_by_current_user") == 1),
                                false,
                                reviews
                        );
                        productId = jsonObject.getInt("product_id");
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
                                jsonObject.getInt("variant_id"),
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
            imageView_variantImage.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getVariations().get(0).getImage()));
            textView_variantName.setText(post.getProduct().getVariations().get(0).getName());
            textView_variantPrice.setText("₱" + Decimals.FORMAT_PRICE.format(post.getProduct().getVariations().get(0).getPrice()));
            textView_variantStock.setText("Stock: " + post.getProduct().getVariations().get(0).getStock());
            textView_title.setText(post.getTitle());
            textView_description.setText(post.getDescription());
            imageView_profilePicture.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getAccount().getImage()));
            textView_sellerName.setText(post.getProduct().getAccount().getName());
            variationList = new ArrayList<>();
            if(post.getProduct().getAccount().getId() == CurrentAccount.getAccount().getId()) {
                button_message.setClickable(false);
            }
            for(Variation variation: post.getProduct().getVariations()) {
                variationList.add(variation.getName());
            }
            adapterVariationList = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, variationList);
            autoComplete_variantName.setAdapter(adapterVariationList);
            autoComplete_variantName.setText(variationList.get(0), false);
            autoComplete_variantName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    imageView_variantImage.setImageBitmap(EncodeImage.decodeFromStringBlob(post.getProduct().getVariations().get(position).getImage()));
                    textView_variantName.setText(post.getProduct().getVariations().get(position).getName());
                    textView_variantPrice.setText("₱" + Decimals.FORMAT_PRICE.format(post.getProduct().getVariations().get(position).getPrice()));
                    textView_variantStock.setText("Stock: " + post.getProduct().getVariations().get(position).getStock());
                }
            });
            dialogPleaseWait.dismiss();
            if(CurrentAccount.getAccount().getId() == post.getProduct().getAccount().getId())
                textView_seller.setText("Seller (You)");
        });
    }

    private void getReviewsCount() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_reviews_count.php?product_id=" + post.getProduct().getId();

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
                        int totalReviews = jsonResponse.getInt("total_reviews");
                        reviews = new ArrayList<>();
                        getReviews(0, totalReviews);

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

    private void getReviews(int recursion, int end) {
        if(recursion == end) {
            setReviews();
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/post_select_reviews.php?product_id=" + post.getProduct().getId() + "&offset=" + recursion;

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
                        reviews.add(new Reviews(
                                jsonObject.getString("reviewer_name"),
                                jsonObject.getString("reviewer_image"),
                                jsonObject.getInt("rating"),
                                jsonObject.getString("variant_name"),
                                jsonObject.getString("comment")
                        ));
                        getReviews(recursion + 1, end);

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

    private void setReviews() {
        runOnUiThread(() -> {
            adapter_postReview = new PostReviewAdapter(this, reviews);
            recyclerView_reviews = findViewById(R.id.post_recyclerView_reviews);
            recyclerView_reviews.setLayoutManager(new LinearLayoutManager(this));
            recyclerView_reviews.setAdapter(adapter_postReview);
            adapter_postReview.notifyDataSetChanged();
        });
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
        getReviewsCount();
    }
}