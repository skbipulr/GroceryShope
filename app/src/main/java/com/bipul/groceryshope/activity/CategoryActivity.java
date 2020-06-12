package com.bipul.groceryshope.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bipul.groceryshope.Adapter.SecondCategoryAdapter;
import com.bipul.groceryshope.Adapter.ThirdCategoryAdapter;
import com.bipul.groceryshope.R;
import com.bipul.groceryshope.modelForProducts.Product;
import com.bipul.groceryshope.modelForProducts.ProductList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {

    int count=0;
    ImageView productImageIV,increaseQuantity,reduceQuantity;
    TextView productNameTV, productQuantityTV,productPriceTV,itemQuantity,addtobag;

    Toolbar toolbar;

    SearchView searchView;

    private List<ProductList> productLists;

    private RecyclerView categoryRecyclerView;
    private ThirdCategoryAdapter thirdCategoryAdapter;

    String productName;

    Product  product;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        colorChangeStatusBar();
        init();

        Intent intent = getIntent();
       // product = (Product) intent.getParcelableExtra("category");
        //int size = product.getProductList().size();
       // Toast.makeText(this, "what "+ product.s, Toast.LENGTH_LONG).show();

       productLists =  intent.getParcelableArrayListExtra("productList");
        thirdCategoryAdapter = new ThirdCategoryAdapter(this,productLists);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categoryRecyclerView.setAdapter(thirdCategoryAdapter);

    }

    private void init() {
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_at_to_cart, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        return true;
    }
    private void loadSecondCategory() {
        thirdCategoryAdapter = new ThirdCategoryAdapter(this,productLists);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecyclerView.setAdapter(thirdCategoryAdapter);
    }


    public void colorChangeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }



    public void goToCart(View view){
        Intent intent = new Intent(this,AddToCartActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }
    public void backBtn(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    /*private void init() {
        productImageIV  = findViewById(R.id.imageProduct);
        productNameTV = findViewById(R.id.productNameTV);
        productPriceTV = findViewById(R.id.productPriceTV);
        productQuantityTV = findViewById(R.id.productQuantityTV);
        increaseQuantity = findViewById(R.id.increaseQuantity);
        itemQuantity = findViewById(R.id.itemQuantity);
        reduceQuantity = findViewById(R.id.reduceQuantity);
        addtobag = findViewById(R.id.addtobag);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                itemQuantity.setVisibility(View.VISIBLE);
                reduceQuantity.setVisibility(View.VISIBLE);
                addtobag.setVisibility(View.GONE);
                itemQuantity.setText(String.valueOf(count));

            }
        });

        reduceQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                itemQuantity.setText(String.valueOf(count));
                if (count==0){
                    addtobag.setVisibility(View.VISIBLE);
                    itemQuantity.setVisibility(View.GONE);
                    reduceQuantity.setVisibility(View.GONE);
                }


            }
        });
    }*/
}
