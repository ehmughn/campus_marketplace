package com.example.testproject2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.ChooseProductsProductAdapter;
import com.example.objects.Account;
import com.example.objects.Product;
import com.example.objects.Variation;
import com.example.static_classes.CategoryEncoder;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishPostActivity extends AppCompatActivity {

    private Button button_attachProduct;
    private LayoutInflater inflaterChooseProduct;
    private View dialogChooseProductView;
    private AlertDialog.Builder builderDialogChooseProduct;
    private AlertDialog dialogChooseProduct;
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    private ChooseProductsProductAdapter chooseProductsAdapter;
    private RecyclerView recyclerView_chooseProduct;
    private Button button_cancel;
    private Button button_post;
    private EditText editText_title;
    private EditText editText_description;
    private Product selectedProduct = null;
    private LinearLayout layout_selectedProduct;
    private TextView textView_productName;
    private ImageView imageView_productImage;

    private boolean hasRetrievedProducts = false;

    private int lastProductId = 0;
    private String lastProductName = "";
    private String lastCategory = "";
    private ArrayList<Variation> variations = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();

    private LayoutInflater inflaterPleaseWait;
    private View dialogPleaseWaitView;
    private AlertDialog.Builder builderDialogPleaseWait;
    private AlertDialog dialogPleaseWait;
    private TextView dialogPleaseWait_textView_progress;

    private LayoutInflater inflaterFinishedPublishingPost;
    private View dialogFinishedPublishingPostView;
    private AlertDialog.Builder builderDialogFinishedPublishingPost;
    private AlertDialog dialogFinishedPublishingPost;
    private Button dialogFinishedPublishingPost_button_goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_publish_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inflaterChooseProduct = getLayoutInflater();
        dialogChooseProductView = inflaterChooseProduct.inflate(R.layout.dialog_choose_product, null);
        builderDialogChooseProduct = new AlertDialog.Builder(this);
        builderDialogChooseProduct.setView(dialogChooseProductView);
        dialogChooseProduct = builderDialogChooseProduct.create();
        dialogChooseProduct.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        button_attachProduct = findViewById(R.id.publish_button_attachProduct);
        button_attachProduct.setClickable(false);
        button_attachProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasRetrievedProducts) {
                    dialogChooseProduct.show();
                }
                else {
                    dialogPleaseWait.show();
                }
            }
        });
        button_cancel = findViewById(R.id.publish_button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishPostActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        button_post = findViewById(R.id.publish_button_post);
        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForEmptyFields();
            }
        });
        layout_selectedProduct = findViewById(R.id.publish_layout_selectedProduct);
        textView_productName = findViewById(R.id.publish_textView_productName);
        imageView_productImage = findViewById(R.id.publish_imageView_productImage);

        inflaterPleaseWait = getLayoutInflater();
        dialogPleaseWaitView = inflaterPleaseWait.inflate(R.layout.dialog_please_wait, null);
        builderDialogPleaseWait = new AlertDialog.Builder(this);
        builderDialogPleaseWait.setView(dialogPleaseWaitView);
        dialogPleaseWait = builderDialogPleaseWait.create();
        dialogPleaseWait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPleaseWait_textView_progress = dialogPleaseWaitView.findViewById(R.id.dialogPleaseWait_textView_progress);

        editText_title = findViewById(R.id.publish_editText_title);
        editText_description = findViewById(R.id.publish_editText_description);

        inflaterFinishedPublishingPost = getLayoutInflater();
        dialogFinishedPublishingPostView = inflaterFinishedPublishingPost.inflate(R.layout.dialog_finished_publishing_post, null);
        builderDialogFinishedPublishingPost = new AlertDialog.Builder(this);
        builderDialogFinishedPublishingPost.setView(dialogFinishedPublishingPostView).setCancelable(false);
        dialogFinishedPublishingPost = builderDialogFinishedPublishingPost.create();
        dialogFinishedPublishingPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFinishedPublishingPost_button_goBack = dialogFinishedPublishingPostView.findViewById(R.id.dialogFinishedPublishingPost_button_goBack);
        dialogFinishedPublishingPost_button_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFinishedPublishingPost.dismiss();
                Intent intent = new Intent(PublishPostActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        getVariationCount();
    }

    private void getVariationCount() {
        runOnUiThread(() -> dialogPleaseWait_textView_progress.setText("Preparing to get products and variations."));
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_variation_count_by_user.php?user_id=" + CurrentAccount.getAccount().getId();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(PublishPostActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        int rows = jsonResponse.getInt("total_rows");
                        loadProducts(0, rows);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(PublishPostActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(PublishPostActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void loadProducts(int reccursion, int end) {
        runOnUiThread(() -> dialogPleaseWait_textView_progress.setText("Getting variant " + (reccursion + 1) + " / " + end + "."));
        if(reccursion == end) {
            inputRecyclerViewData(products);
            return;
        }
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/select_all_products_by_user.php?user_id=" + CurrentAccount.getAccount().getId() + "&variation_number=" + reccursion;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(PublishPostActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray responseArray = new JSONArray(responseData);
                        JSONObject jsonObject = responseArray.getJSONObject(0);
                        int currentProductId = jsonObject.getInt("product_id");
                        if(lastProductId != currentProductId) {
                            if(lastProductId != 0) {
                                products.add(new Product(
                                        lastProductId,
                                        lastProductName,
                                        lastCategory,
                                        CurrentAccount.getAccount(),
                                        variations));
                                variations = new ArrayList<>();
                            }
                            lastProductId = currentProductId;
                            lastProductName = jsonObject.getString("product_name");
                            lastCategory = jsonObject.getString("category");
                        }
                        variations.add(new Variation(
                                0,
                                jsonObject.getString("variant_name"),
                                jsonObject.getDouble("cost"),
                                jsonObject.getInt("stock"),
                                jsonObject.getString("image")
                        ));
                        if(reccursion == end - 1) {
                            products.add(new Product(
                                    lastProductId,
                                    lastProductName,
                                    lastCategory,
                                    CurrentAccount.getAccount(),
                                    variations
                            ));
                        }
                        loadProducts(reccursion + 1, end);

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(PublishPostActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(PublishPostActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void inputRecyclerViewData(ArrayList<Product> products) {
        runOnUiThread(() -> dialogPleaseWait_textView_progress.setText("Done"));
        chooseProductsAdapter = new ChooseProductsProductAdapter(dialogChooseProductView.getContext(), products, product -> {
            displaySelectedProduct(product);
            dialogChooseProduct.dismiss();
        });
        hasRetrievedProducts = true;
        recyclerView_chooseProduct = dialogChooseProductView.findViewById(R.id.recyclerView_chooseProduct);
        recyclerView_chooseProduct.setAdapter(chooseProductsAdapter);
        recyclerView_chooseProduct.setLayoutManager(new LinearLayoutManager(dialogChooseProductView.getContext()));
        if(dialogPleaseWait.isShowing()) {
            runOnUiThread(() -> dialogPleaseWait.dismiss());
            runOnUiThread(() -> dialogChooseProduct.show());
        }
    }

    private void displaySelectedProduct(Product product) {
        selectedProduct = product;
        textView_productName.setText(product.getName());
        imageView_productImage.setImageBitmap(EncodeImage.decodeFromStringBlob(product.getVariations().get(0).getImage()));
        layout_selectedProduct.setVisibility(View.VISIBLE);
    }

    private void checkForEmptyFields() {
        if(editText_title.getText().toString().trim().isEmpty() || editText_description.getText().toString().trim().isEmpty() || selectedProduct == null) {
            Toast.makeText(PublishPostActivity.this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(PublishPostActivity.this, "Publishing post", Toast.LENGTH_SHORT).show();
        attemptPublishPost();
    }

    private void attemptPublishPost() {
        dialogPleaseWait.setCancelable(false);
        dialogPleaseWait.show();
        dialogPleaseWait_textView_progress.setText("Publishing post...");
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/publish_post.php";

        RequestBody body = new FormBody.Builder()
                .add("product_id", Integer.toString(selectedProduct.getId()))
                .add("post_title", editText_title.getText().toString().trim())
                .add("post_description", editText_description.getText().toString().trim())
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
                                successPublishPost();
                            } else {
                                Toast.makeText(PublishPostActivity.this, "Publish Post Failed" + responseData, Toast.LENGTH_SHORT).show();
                                dialogPleaseWait.dismiss();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PublishPostActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                            dialogPleaseWait.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private void successPublishPost() {
        dialogPleaseWait.dismiss();
        dialogFinishedPublishingPost.show();
    }
}