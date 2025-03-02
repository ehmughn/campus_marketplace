package com.example.testproject2;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.objects.Variation;
import com.example.static_classes.CategoryEncoder;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.Decimals;
import com.example.static_classes.EncodeImage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReviewActivity extends AppCompatActivity {

    private String productName;
    private int productId;
    private int sellerId;
    private int postId;
    private ArrayList<Variation> variations;
    private ArrayList<String> variationList;
    private AutoCompleteTextView autoComplete_variantName;
    private ArrayAdapter<String> adapterVariationList;
    private int selectedVariation;
    private TextView textView_productName;
    private EditText editText_comment;
    private ImageView imageView_star1;
    private ImageView imageView_star2;
    private ImageView imageView_star3;
    private ImageView imageView_star4;
    private ImageView imageView_star5;
    private int rating = 1;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    private LayoutInflater inflater;
    private View dialogView;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button dialog_button_goBack;
    private Button button_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        variations = new ArrayList<>();
        productName = getIntent().getStringExtra("product_name");
        productId = getIntent().getIntExtra("product_id", 0);
        sellerId = getIntent().getIntExtra("seller_id", 0);
        postId = getIntent().getIntExtra("post_id", 0);
        for(int i = 0; i < getIntent().getIntExtra("variation_count", 0); i++) {
            variations.add(new Variation(
                    getIntent().getIntExtra("variationId" + i, 0),
                    getIntent().getStringExtra("variationName" + i),
                    0,
                    0,
                    ""
            ));
        }
        selectedVariation = variations.get(0).getId();
        variationList = new ArrayList<>();
        for(Variation variation: variations) {
            variationList.add(variation.getName());
        }
        textView_productName = findViewById(R.id.review_textView_productName);
        textView_productName.setText(productName);

        editText_comment = findViewById(R.id.review_editText_comment);

        autoComplete_variantName = findViewById(R.id.review_autoComplete_variantName);
        adapterVariationList = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, variationList);
        autoComplete_variantName.setAdapter(adapterVariationList);
        autoComplete_variantName.setText(variationList.get(0), false);
        autoComplete_variantName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedVariation = variations.get(position).getId();
            }
        });
        imageView_star1 = findViewById(R.id.review_imageView_star1);
        imageView_star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDisplayRating(1);
            }
        });
        imageView_star2 = findViewById(R.id.review_imageView_star2);
        imageView_star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDisplayRating(2);
            }
        });
        imageView_star3 = findViewById(R.id.review_imageView_star3);
        imageView_star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDisplayRating(3);
            }
        });
        imageView_star4 = findViewById(R.id.review_imageView_star4);
        imageView_star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDisplayRating(4);
            }
        });
        imageView_star5 = findViewById(R.id.review_imageView_star5);
        imageView_star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDisplayRating(5);
            }
        });
        setDisplayRating(1);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_reviewed_successfully, null);
        builder = new AlertDialog.Builder(this);
        builder.setView(dialogView).setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_button_goBack = dialogView.findViewById(R.id.dialogReviewSuccessfully_button_goBack);
        dialog_button_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_submit = findViewById(R.id.review_button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProduct();
            }
        });
    }

    private void setDisplayRating(int rating) {
        this.rating = rating;
        switch(rating) {
            case 1:
                imageView_star1.setImageResource(R.drawable.star_full);
                imageView_star2.setImageResource(R.drawable.star_empty);
                imageView_star3.setImageResource(R.drawable.star_empty);
                imageView_star4.setImageResource(R.drawable.star_empty);
                imageView_star5.setImageResource(R.drawable.star_empty);
                break;
            case 2:
                imageView_star1.setImageResource(R.drawable.star_full);
                imageView_star2.setImageResource(R.drawable.star_full);
                imageView_star3.setImageResource(R.drawable.star_empty);
                imageView_star4.setImageResource(R.drawable.star_empty);
                imageView_star5.setImageResource(R.drawable.star_empty);
                break;
            case 3:
                imageView_star1.setImageResource(R.drawable.star_full);
                imageView_star2.setImageResource(R.drawable.star_full);
                imageView_star3.setImageResource(R.drawable.star_full);
                imageView_star4.setImageResource(R.drawable.star_empty);
                imageView_star5.setImageResource(R.drawable.star_empty);
                break;
            case 4:
                imageView_star1.setImageResource(R.drawable.star_full);
                imageView_star2.setImageResource(R.drawable.star_full);
                imageView_star3.setImageResource(R.drawable.star_full);
                imageView_star4.setImageResource(R.drawable.star_full);
                imageView_star5.setImageResource(R.drawable.star_empty);
                break;
            case 5:
                imageView_star1.setImageResource(R.drawable.star_full);
                imageView_star2.setImageResource(R.drawable.star_full);
                imageView_star3.setImageResource(R.drawable.star_full);
                imageView_star4.setImageResource(R.drawable.star_full);
                imageView_star5.setImageResource(R.drawable.star_full);
                break;
        }
    }

    private void uploadProduct() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/review_product.php";

        RequestBody body = new FormBody.Builder()
                .add("variant_reviewed_id", Integer.toString(selectedVariation))
                .add("reviewer_id", Integer.toString(CurrentAccount.getAccount().getId()))
                .add("comment", editText_comment.getText().toString().trim())
                .add("rating", Integer.toString(rating))
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful() && responseData.contains("success")) {
                                notifyUser();
                            } else {
                                Toast.makeText(ReviewActivity.this, "Review Failed" + responseData, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReviewActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void notifyUser() {
        String url = "http://" + DatabaseConnectionData.getHost() + "/numart_db/notify_user.php";

        RequestBody body = new FormBody.Builder()
                .add("for_user_id", Integer.toString(sellerId))
                .add("from_user_id", Integer.toString(CurrentAccount.getAccount().getId()))
                .add("message", CurrentAccount.getAccount().getName() + " reviewed your product.")
                .add("type", "REVIEW")
                .add("reference", Integer.toString(postId))
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful() && responseData.contains("success")) {
                                dialog.show();
                            } else {
                                Toast.makeText(ReviewActivity.this, "Failed to notify", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReviewActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}