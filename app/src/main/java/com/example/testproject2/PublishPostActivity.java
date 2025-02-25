package com.example.testproject2;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.example.objects.Product;
import com.example.objects.Variation;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PublishPostActivity extends AppCompatActivity {

    private Button button_publishPost;
    private LayoutInflater inflaterChooseProduct;
    private View dialogChooseProductView;
    private AlertDialog.Builder builderDialogChooseProduct;
    private AlertDialog dialogChooseProduct;
    private OkHttpClient client = new OkHttpClient();
    private ChooseProductsProductAdapter chooseProductsAdapter;
    private RecyclerView recyclerView_chooseProduct;

    private int lastProductId = 0;
    private String lastProductName = "";
    private String lastCategory = "";
    private ArrayList<Variation> variations = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();

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
        button_publishPost = findViewById(R.id.publish_button_attachProduct);
        button_publishPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChooseProduct.show();
            }
        });
        getVariationCount();
    }

    private void getVariationCount() {
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
                                        CurrentAccount.getAccount().getId(),
                                        variations));
                                variations = new ArrayList<>();
                            }
                            lastProductId = currentProductId;
                            lastProductName = jsonObject.getString("product_name");
                            lastCategory = jsonObject.getString("category");
                        }
                        variations.add(new Variation(
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
                                    CurrentAccount.getAccount().getId(),
                                    variations
                            ));
                        }
                        runOnUiThread(() -> Toast.makeText(PublishPostActivity.this, "Obtained the product and variation " + (reccursion + 1), Toast.LENGTH_SHORT).show());
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
        chooseProductsAdapter = new ChooseProductsProductAdapter(dialogChooseProductView.getContext(), products);
        recyclerView_chooseProduct = dialogChooseProductView.findViewById(R.id.recyclerView_chooseProduct);
        recyclerView_chooseProduct.setAdapter(chooseProductsAdapter);
        recyclerView_chooseProduct.setLayoutManager(new LinearLayoutManager(dialogChooseProductView.getContext()));
    }
}