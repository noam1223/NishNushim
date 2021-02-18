package com.example.nishnushim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishnushim.helpclasses.Classification;
import com.example.nishnushim.helpclasses.Dish;
import com.example.nishnushim.helpclasses.Menu;
import com.example.nishnushim.helpclasses.Restaurant;
import com.example.nishnushim.helpclasses.Order;
import com.example.nishnushim.helpclasses.helpInterfaces.OrderListener;
import com.example.nishnushim.nishnushFragments.cartFragments.CartDeliveryDetailsFragment;
import com.example.nishnushim.nishnushFragments.cartFragments.CartDetailsFragment;

import java.util.List;

public class CartActivity extends AppCompatActivity implements OrderListener {

    ScrollView fragmentScrollView;
    EditText searchEditText;
    ImageView searchImageView;

    TextView moveToCartDetailsTextView, moveToDeliveryOrderDetailsTextView;

    Fragment orderDetailFragmentLayout;

    Order order;
    Restaurant restaurant;
    String key;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       orderDetailFragmentLayout.onActivityResult(requestCode, resultCode, data);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        order = new Order();
        order.setOrderStatus(Order.OrderStatus.NONE);
        Intent intent = getIntent();

        if (intent != null){

            if (intent.getSerializableExtra("cart") != null){
                order.setOrder((Menu) intent.getSerializableExtra("cart"));
            }

            if (intent.getSerializableExtra(getString(R.string.restaurant_detail)) != null){
                restaurant = (Restaurant) intent.getSerializableExtra(getString(R.string.restaurant_detail));
            }

            if (intent.getStringExtra("key") != null){
                key = intent.getStringExtra("key");
            }

        }



        fragmentScrollView = findViewById(R.id.cart_fragment_layout_scroll_view_cart_activity);
        searchEditText = findViewById(R.id.edit_text_search_tool_bar_cart_activity);
        searchImageView = findViewById(R.id.search_image_view_tool_bar_cart_activity);

        moveToCartDetailsTextView = findViewById(R.id.my_order_details_text_view_cart_activity);
        moveToDeliveryOrderDetailsTextView = findViewById(R.id.my_delivery_details_text_view_cart_activity);



        orderDetailFragmentLayout = new CartDetailsFragment(order, restaurant, key, this);

        getSupportFragmentManager().beginTransaction().replace(R.id.cart_frame_layout_cart_activity, orderDetailFragmentLayout).commit();


        moveToCartDetailsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(orderDetailFragmentLayout instanceof CartDetailsFragment)){

                    moveToCartDetailsTextView.setBackground(ContextCompat.getDrawable(CartActivity.this, R.drawable.sub_title_menu_background_clicked));
                    moveToDeliveryOrderDetailsTextView.setBackground(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.cart_frame_layout_cart_activity, orderDetailFragmentLayout).commit();

                }

            }
        });



        moveToDeliveryOrderDetailsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(orderDetailFragmentLayout instanceof CartDeliveryDetailsFragment)){

                    moveToDeliveryOrderDetailsTextView.setBackground(ContextCompat.getDrawable(CartActivity.this, R.drawable.sub_title_menu_background_clicked));
                    moveToCartDetailsTextView.setBackground(null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.cart_frame_layout_cart_activity, new CartDeliveryDetailsFragment(order, restaurant, key)).commit();

                }


            }
        });

    }


    @Override
    public void onContinueWithOrder(Order order) {

        this.order = order;
        getSupportFragmentManager().beginTransaction().replace(R.id.cart_frame_layout_cart_activity, new CartDeliveryDetailsFragment(this.order, restaurant, key)).commit();
        fragmentScrollView.scrollTo(0,0);
        moveToDeliveryOrderDetailsTextView.setBackground(ContextCompat.getDrawable(CartActivity.this, R.drawable.sub_title_menu_background_clicked));
        moveToCartDetailsTextView.setBackground(null);

    }


}