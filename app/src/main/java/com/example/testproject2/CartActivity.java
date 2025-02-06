package com.example.testproject2;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.CartItemsAdapter;
import com.example.objects.Post;
import com.example.temporary_values.TemporaryPostList;
import com.example.testproject2.databinding.ActivityCartBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private ArrayList<Post> items;
    private CartItemsAdapter adapter_cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        EdgeToEdge.enable(this);
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        items = new ArrayList<>();
        items.add(TemporaryPostList.getPost(0));
        items.add(TemporaryPostList.getPost(1));
        items.add(TemporaryPostList.getPost(2));
        items.add(TemporaryPostList.getPost(3));
        items.add(TemporaryPostList.getPost(4));
        items.add(TemporaryPostList.getPost(5));
        items.add(TemporaryPostList.getPost(6));
        items.add(TemporaryPostList.getPost(7));
        binding.cartRecyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        adapter_cartItems = new CartItemsAdapter(this, items);
        binding.cartRecyclerViewItems.setAdapter(adapter_cartItems);
        new ItemTouchHelper(cartItems_simpleCallBack).attachToRecyclerView(binding.cartRecyclerViewItems);
    }
    Post item_toRemove;
    ItemTouchHelper.SimpleCallback cartItems_simpleCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            item_toRemove = items.get(position);
            items.remove(item_toRemove);
            adapter_cartItems.notifyDataSetChanged();

            Snackbar.make(binding.cartRecyclerViewItems, "Removed " + item_toRemove.getTitle(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items.add(position, item_toRemove);
                    adapter_cartItems.notifyDataSetChanged();
                }
            }).show();
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(CartActivity.this, R.color.main_yellow))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}