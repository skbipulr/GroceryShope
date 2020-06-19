package com.bipul.groceryshope.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bipul.groceryshope.Adapter.CartAdapter;
import com.bipul.groceryshope.R;
import com.bipul.groceryshope.datebase.DatabaseOpenHelper;
import com.bipul.groceryshope.model.Order;

import java.util.ArrayList;
import java.util.List;

public class AddToCartActivity extends AppCompatActivity {

    Toolbar toolbar;

    SearchView searchView;
    public static TextView txtTotalPrice;
    public static TextView txtSubTotal;

    public static List<Order> carts = new ArrayList<>();
    CartAdapter adapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        colorChangeStatusBar();

        init();

        showData();

        //loadListFood();
        calculatePrice();


    }

    static public void calculatePrice(){
        //Calculate total price
        int total = 0;
        for (Order order : carts)
            total += (Integer.parseInt(order.getProductPrice())*order.getProductQuantity());
        txtSubTotal.setText(String.valueOf(total));
        txtTotalPrice.setText(String.valueOf(total));
        //Toast.makeText(AddToCartActivity.this, ""+carts.get(0).getProductQuantity(), Toast.LENGTH_SHORT).show();
    }

    private void init() {
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
        adapter = new CartAdapter(this, carts);
        recyclerView.setAdapter(adapter);


    }

    private void showData() {
        Cursor cursor = helper.getData();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
            int productId = cursor.getInt(cursor.getColumnIndex(helper.COL_PRODUCT_ID));
            String productName = cursor.getString(cursor.getColumnIndex(helper.COL_PRODUCT_NAME));
            String productImage = cursor.getString(cursor.getColumnIndex(helper.COL_PRODUCT_IMAGE));
            String productPrice = cursor.getString(cursor.getColumnIndex(helper.COL_PRODUCT_PRICE));
            String productUnit = cursor.getString(cursor.getColumnIndex(helper.COL_PRODUCT_UNIT));
            int productQuantity = cursor.getInt(cursor.getColumnIndex(helper.COL_PRODUCT_QUANTITY));

            carts.add(new Order(id, productId, productName, productImage, productUnit, productPrice, productQuantity));
            adapter.notifyDataSetChanged();
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
}
