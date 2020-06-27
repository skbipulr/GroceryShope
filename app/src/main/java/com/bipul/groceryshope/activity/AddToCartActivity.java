package com.bipul.groceryshope.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bipul.groceryshope.Adapter.CartAdapter;
import com.bipul.groceryshope.R;
import com.bipul.groceryshope.datebase.DatabaseOpenHelper;
import com.bipul.groceryshope.interfaces.OnCartListener;
import com.bipul.groceryshope.model.Order;
import com.bipul.groceryshope.modelForProductDetails.Product;
import com.bipul.groceryshope.modelForProducts.ProductList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AddToCartActivity extends AppCompatActivity implements OnCartListener {

    Toolbar toolbar;

    SearchView searchView;
    public static TextView txtTotalPrice;
    public static TextView txtSubTotal;

    CartAdapter adapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseOpenHelper helper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private List<ProductList> cartProductLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        colorChangeStatusBar();
        init();
        showData();
    }

    public void calculatePrice() {
        if (cartProductLists!=null && cartProductLists.size()>0){
            int total = 0;
            for (ProductList productList : cartProductLists)
                total += (productList.getRate()) * productList.getCountForCart();
            txtSubTotal.setText(String.valueOf(total));
            txtTotalPrice.setText(String.valueOf(total));
        }else {
            txtSubTotal.setText("0");
            txtTotalPrice.setText("0");
        }
    }

    private void init() {
        cartProductLists = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTotalPrice = findViewById(R.id.totalTV);
        txtSubTotal = findViewById(R.id.SubtotalTV);

        //Init
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        helper = new DatabaseOpenHelper(this);
        adapter = new CartAdapter(this, cartProductLists,this);
        recyclerView.setAdapter(adapter);

    }

    private void showData() {
        sharedPreferences = getSharedPreferences("CartPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String cartProducts = sharedPreferences.getString("cartProductLists", "");

        Log.d("BBBB", cartProducts);
        if (!TextUtils.isEmpty(cartProducts)) {
            List<ProductList> productLists = new Gson().fromJson(cartProducts, new TypeToken<List<ProductList>>() {
            }.getType());
            cartProductLists.addAll(productLists);

            if (cartProductLists!=null && cartProductLists.size()>0){
                adapter.notifyDataSetChanged();
                calculatePrice();
            }
        }
    }

 /*   private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(this, cart);
        recyclerView.setAdapter(adapter);
    }*/

     /*//Calculate total price
        int total = 0;
        for (Order order : cart)
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuanlity()));
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_at_to_cart, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        return true;
    }

    public void colorChangeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }

    public void backBtn(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    public void CheckOut(View view) {
        Intent intent = new Intent(this, PhoneLoginActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

    @Override
    public void OnCartAdded(ProductList productList) {
        if (cartProductLists.size() > 0) {
            boolean isMatched = false;
            for (ProductList cartProduct : cartProductLists) {
                if (cartProduct.getProductId() == productList.getProductId()) {
                    cartProduct.setCountForCart(cartProduct.getCountForCart() + 1);
                    isMatched = true;
                    break;
                }
            }
            if (!isMatched) {
                cartProductLists.add(productList);
            }
        } else {
            cartProductLists.add(productList);
        }
        calculatePrice();
        adapter.notifyDataSetChanged();
        Log.d("BBBB", "OnCartAdded: "+cartProductLists.size());
        editor.putString("cartProductLists", new Gson().toJson(cartProductLists));
        editor.apply();
    }

    @Override
    public void onCartRemoved(ProductList productList) {
        if (cartProductLists.size() > 0) {
            for (ProductList cartProduct : cartProductLists) {
                if (cartProduct.getProductId() == productList.getProductId()) {
                    if (cartProduct.getCountForCart() > 1) {
                        cartProduct.setCountForCart(cartProduct.getCountForCart() - 1);
                    } else {
                        cartProductLists.remove(cartProduct);
                    }
                    break;
                }
            }
        }
        Log.d("BBBB", "OnCartRemoved: "+cartProductLists.size());

        calculatePrice();
        adapter.notifyDataSetChanged();
        editor.putString("cartProductLists", new Gson().toJson(cartProductLists));
        editor.apply();
    }

    @Override
    public void onDeleteFromCart(ProductList productList) {
        if (cartProductLists.size() > 0) {
            for (ProductList cartProduct : cartProductLists) {
                if (cartProduct.getProductId() == productList.getProductId()) {
                    cartProductLists.remove(cartProduct);
                    break;
                }
            }
        }
        calculatePrice();
        adapter.notifyDataSetChanged();
        editor.putString("cartProductLists", new Gson().toJson(cartProductLists));
        editor.apply();
    }




}
