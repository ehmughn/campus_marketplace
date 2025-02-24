package com.example.testproject2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.UploadProductVariationsAdapter;
import com.example.objects.Variation;
import com.example.static_classes.CategoryEncoder;
import com.example.static_classes.CurrentAccount;
import com.example.static_classes.DatabaseConnectionData;
import com.example.static_classes.EncodeImage;
import com.example.static_classes.RegisterInfoHolder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadActivity extends AppCompatActivity {
    private LayoutInflater inflaterVariation;
    private View dialogVariationView;
    private Button button_addVariation;
    private AlertDialog.Builder builderDialogVariation;
    private AlertDialog dialogVariation;
    private Button dialogVariation_button_addImage;
    private ImageView dialogVariation_imageView_variationImage;
    private Bitmap bitmap;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ArrayList<Variation> variations;
    private UploadProductVariationsAdapter uploadProductVariationsAdapter;
    private RecyclerView recyclerView_variations;
    private Button dialogVariation_button_cancel;
    private Button dialogVariation_button_confirm;
    private EditText dialogVariation_editText_variationName;
    private EditText dialogVariation_editText_variationPrice;
    private EditText dialogVariation_editText_variationStock;
    private Button button_submit;
    private EditText editText_productName;
    private TextInputLayout editText_category;
    private OkHttpClient client = new OkHttpClient();
    private LayoutInflater inflaterPleaseWait;
    private View dialogPleaseWaitView;
    private AlertDialog.Builder builderDialogPleaseWait;
    private AlertDialog dialogPleaseWait;
    private LayoutInflater inflaterFinishedUploadingProducts;
    private View dialogFinishedUploadingProductsView;
    private AlertDialog.Builder builderDialogFinishedUploadingProducts;
    private AlertDialog dialogFinishedUploadingProducts;
    private Button dialogFinishedUploadingProducts_button_goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inflaterVariation = getLayoutInflater();
        dialogVariationView = inflaterVariation.inflate(R.layout.dialog_add_variation, null);
        builderDialogVariation = new AlertDialog.Builder(this);
        builderDialogVariation.setView(dialogVariationView);
        dialogVariation = builderDialogVariation.create();
        dialogVariation.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        button_addVariation = findViewById(R.id.upload_button_addVariation);
        button_addVariation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVariation_editText_variationName.setText("");
                dialogVariation_editText_variationPrice.setText("");
                dialogVariation_editText_variationStock.setText("");
                dialogVariation_editText_variationName.clearFocus();
                dialogVariation_editText_variationPrice.clearFocus();
                dialogVariation_editText_variationStock.clearFocus();
                dialogVariation_imageView_variationImage.setImageResource(R.drawable.no_image);
                bitmap = null;
                dialogVariation.show();
            }
        });
        dialogVariation_imageView_variationImage = dialogVariationView.findViewById(R.id.dialogVariation_imageView_variationImage);
        dialogVariation_imageView_variationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageExternally();
            }
        });
        dialogVariation_button_addImage = dialogVariationView.findViewById(R.id.dialogVariation_button_addImage);
        dialogVariation_button_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageExternally();
            }
        });
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        dialogVariation_imageView_variationImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        variations = new ArrayList<>();
        uploadProductVariationsAdapter = new UploadProductVariationsAdapter(this, variations);
        recyclerView_variations = findViewById(R.id.upload_recyclerView_variations);
        recyclerView_variations.setAdapter(uploadProductVariationsAdapter);
        recyclerView_variations.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(cartItems_simpleCallBack).attachToRecyclerView(recyclerView_variations);
        dialogVariation_button_confirm = dialogVariationView.findViewById(R.id.dialogVariation_button_confirm);
        dialogVariation_button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmVariation();
            }
        });
        dialogVariation_button_cancel = dialogVariationView.findViewById(R.id.dialogVariation_button_cancel);
        dialogVariation_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVariation.dismiss();
            }
        });
        dialogVariation_editText_variationName = dialogVariationView.findViewById(R.id.dialogVariation_editText_variationName);
        dialogVariation_editText_variationPrice = dialogVariationView.findViewById(R.id.dialogVariation_editText_variationPrice);
        dialogVariation_editText_variationStock = dialogVariationView.findViewById(R.id.dialogVariation_editText_variationStock);
        button_submit = findViewById(R.id.upload_button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptToUploadProduct();
            }
        });
        editText_productName = findViewById(R.id.upload_editText_productName);
        editText_category = findViewById(R.id.upload_editText_category);

        inflaterPleaseWait = getLayoutInflater();
        dialogPleaseWaitView = inflaterPleaseWait.inflate(R.layout.dialog_please_wait, null);
        builderDialogPleaseWait = new AlertDialog.Builder(this);
        builderDialogPleaseWait.setView(dialogPleaseWaitView).setCancelable(false);
        dialogPleaseWait = builderDialogPleaseWait.create();
        dialogPleaseWait.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        inflaterFinishedUploadingProducts = getLayoutInflater();
        dialogFinishedUploadingProductsView = inflaterFinishedUploadingProducts.inflate(R.layout.dialog_finished_uploading_products, null);
        builderDialogFinishedUploadingProducts = new AlertDialog.Builder(this);
        builderDialogFinishedUploadingProducts.setView(dialogFinishedUploadingProductsView).setCancelable(false);
        dialogFinishedUploadingProducts = builderDialogFinishedUploadingProducts.create();
        dialogFinishedUploadingProducts.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFinishedUploadingProducts_button_goBack = dialogFinishedUploadingProductsView.findViewById(R.id.dialogFinishedUploadingProducts_button_goBack);
        dialogFinishedUploadingProducts_button_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFinishedUploadingProducts.dismiss();
                finish();
            }
        });
    }

    private void attemptToUploadProduct() {
        if(editText_productName.getText().toString().trim().isEmpty()) {
            Toast.makeText(UploadActivity.this, "Please fill up the product name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(editText_category.getEditText().getText().toString().trim().isEmpty()) {
            Toast.makeText(UploadActivity.this, "Please choose a category.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(variations.isEmpty()) {
            Toast.makeText(UploadActivity.this, "Please add at least 1 variation.", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadProduct();
    }

    private void uploadProduct() {
        dialogPleaseWait.show();
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/upload_product.php";

        RequestBody body = new FormBody.Builder()
                .add("product_name", editText_productName.getText().toString().trim())
                .add("seller_id", Integer.toString(CurrentAccount.getAccount().getId()))
                .add("category_id", CategoryEncoder.toCode(editText_category.getEditText().getText().toString().trim()))
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
                                Toast.makeText(UploadActivity.this, "Uploaded the product" + responseData, Toast.LENGTH_SHORT).show();
                                uploadVariants1();
                            } else {
                                Toast.makeText(UploadActivity.this, "Product Upload Failed" + responseData, Toast.LENGTH_SHORT).show();
                                dialogPleaseWait.dismiss();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UploadActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                            dialogPleaseWait.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private void uploadVariants1() {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/get_latest_product.php";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(UploadActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                dialogPleaseWait.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        JSONObject data = jsonResponse.getJSONObject("data");
                        runOnUiThread(() -> Toast.makeText(UploadActivity.this, "Obtained the product_id", Toast.LENGTH_SHORT).show());
                        uploadVariants2(data.getInt("product_id"), 0);
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(UploadActivity.this, "Unexpected Response: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        dialogPleaseWait.dismiss();
                    }
                }
                else {
                    runOnUiThread(() -> Toast.makeText(UploadActivity.this, "Network error", Toast.LENGTH_SHORT).show());
                    dialogPleaseWait.dismiss();
                }
            }
        });
    }

    private void uploadVariants2(int product_id, int reccursion) {
        String url = "http://" + DatabaseConnectionData.getHost() +"/numart_db/upload_variation.php";

        if(reccursion == variations.size()) {
            dialogPleaseWait.dismiss();
            dialogFinishedUploadingProducts.show();
            return;
        }

        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        String query = "INSERT INTO product_variants (product_id, variant_name, variant_cost, variant_stock, variant_image) VALUES ";

            query += "(" + product_id + ", '" +
                    variations.get(reccursion).getName() + "', " +
                    variations.get(reccursion).getPrice() + ", " +
                    variations.get(reccursion).getStock() + ", '" +
                    variations.get(reccursion).getImage() + "')";

        RequestBody body = new FormBody.Builder()
                .add("query", query)
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
                                Toast.makeText(UploadActivity.this, "Variation Upload Success " + reccursion, Toast.LENGTH_SHORT).show();
                                uploadVariants2(product_id, reccursion + 1);
                            } else {
                                Toast.makeText(UploadActivity.this, "Variation Upload Failed: " + responseData, Toast.LENGTH_SHORT).show();
                                dialogPleaseWait.dismiss();
                            }
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UploadActivity.this, "Network Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialogPleaseWait.dismiss();
                        }
                    });
                }
            }
        }).start();
    }

    private boolean variationIsEmpty() {
        if(dialogVariation_editText_variationName.getText().toString().trim().isEmpty())
            return true;
        if(dialogVariation_editText_variationPrice.getText().toString().trim().isEmpty())
            return true;
        if(dialogVariation_editText_variationStock.getText().toString().trim().isEmpty())
            return true;
        if(bitmap == null)
            return true;
        return false;
    }

    private void confirmVariation() {
        if(variationIsEmpty()) {
            Toast.makeText(UploadActivity.this, "Please fill up all the forms.", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = dialogVariation_editText_variationName.getText().toString().trim();
        double price = Double.parseDouble(dialogVariation_editText_variationPrice.getText().toString().trim());
        int stock = Integer.parseInt(dialogVariation_editText_variationStock.getText().toString().trim());
        String image = EncodeImage.encodeFromBitmap(bitmap);
        Variation variation = new Variation(name, price, stock, image);
        variations.add(variation);
        uploadProductVariationsAdapter.notifyDataSetChanged();
        dialogVariation.dismiss();
    }

    private void getImageExternally() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
    }


    Variation item_toRemove;
    ItemTouchHelper.SimpleCallback cartItems_simpleCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            item_toRemove = variations.get(position);
            variations.remove(item_toRemove);
            uploadProductVariationsAdapter.notifyDataSetChanged();

            Snackbar.make(recyclerView_variations, "Removed " + item_toRemove.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    variations.add(position, item_toRemove);
                    uploadProductVariationsAdapter.notifyDataSetChanged();
                }
            }).show();
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(UploadActivity.this, R.color.main_yellow))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}