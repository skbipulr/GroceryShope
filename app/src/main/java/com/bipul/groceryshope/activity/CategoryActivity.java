package com.bipul.groceryshope.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bipul.groceryshope.Adapter.SecondCategoryAdapter;
import com.bipul.groceryshope.Adapter.ThirdCategoryAdapter;
import com.bipul.groceryshope.R;
import com.bipul.groceryshope.Utils.Common;
import com.bipul.groceryshope.Utils.ConnectivityHelper;
import com.bipul.groceryshope.Utils.CustomVisibility;
import com.bipul.groceryshope.Utils.NetworkChangeReceiver;
import com.bipul.groceryshope.interfaces.OnCartListener;
import com.bipul.groceryshope.interfaces.OnNetworkStateChangeListener;
import com.bipul.groceryshope.modelForProducts.Product;
import com.bipul.groceryshope.modelForProducts.ProductList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity implements OnNetworkStateChangeListener, OnCartListener {

    int count = 0;
    ImageView productImageIV, increaseQuantity, reduceQuantity;
    TextView productNameTV, productQuantityTV, productPriceTV, itemQuantity, addtobag;

    private Toolbar toolbar;

    private List<ProductList> cartProductLists;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //for internet--------------start----------------
    private int networkStateChangeCount = 0;
    private NetworkChangeReceiver mNetworkReceiver;
    private SwipeRefreshLayout swipeRefreshLayout;
    TextView noInternetTVED;
    //for internet--------------end------------------


    private SearchView searchView;
    TextView textCartItemCount;

    private List<ProductList> productLists;

    private RecyclerView categoryRecyclerView;
    private ThirdCategoryAdapter thirdCategoryAdapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        colorChangeStatusBar();
        init();
        initSwipeLayout();

        Intent intent = getIntent();
        // product = (Product) intent.getParcelableExtra("category");
        //int size = product.getProductList().size();
        // Toast.makeText(this, "what "+ product.s, Toast.LENGTH_LONG).show();
        String pos = getIntent().getStringExtra("category");
        TextView categoryName = findViewById(R.id.categoryName);
        categoryName.setText(pos);


        productLists = intent.getParcelableArrayListExtra("productList");

        loadSecondCategory();

    }

    private void getCartProductList() {
        cartProductLists = new ArrayList<>();
        sharedPreferences = getSharedPreferences("CartPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String cartProducts = sharedPreferences.getString("cartProductLists", "");
        if (!TextUtils.isEmpty(cartProducts)) {
            cartProductLists = new Gson().fromJson(cartProducts, new TypeToken<List<ProductList>>() {
            }.getType());
            Log.d("BBBB", ""+cartProductLists.size());
            // fab.setCount(cartProductLists.size());
            if (cartProductLists != null && cartProductLists.size() > 0) {
                setupBadge(cartProductLists.size());
            } else {
                setupBadge(0);
            }
            if (productLists!=null && productLists.size()>0){
                matchCartAddedProduct(productLists);
                thirdCategoryAdapter.notifyDataSetChanged();
            }
        } else {
            setupBadge(0);
            // fab.setCount(0);
            Log.d("BBBB", ""+cartProductLists.size());
        }
    }


    private void init() {
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noInternetTVED = findViewById(R.id.noInternetTVE);
        mNetworkReceiver = new NetworkChangeReceiver(this);
        registerNetworkBroadcast();
    }






    private void initSwipeLayout() {
        //view
        swipeRefreshLayout = findViewById(R.id.mediaCoverageSwipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.black,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Common.isConnectToInternet(getBaseContext())) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(getBaseContext(), "Please check your connection!!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
            }
        });


        //Default, load for first time
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                if (Common.isConnectToInternet(getBaseContext())) {
                   loadSecondCategory();
                    //  findViewById(R.id.NestedScrollView).setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);

                } else {
                    Toast.makeText(getBaseContext(), "Please check your connection!!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
            }

        });

    }

    //-------------for internet check------start---------
    private void registerNetworkBroadcast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    private void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ConnectivityHelper.isConnected(this) == true) {
            noInternetTVED.setVisibility(View.GONE);
        } else {
            noInternetTVED.setVisibility(View.VISIBLE);
        }

        getCartProductList();
    }

    @Override
    public void onChange(boolean isConnected) {
        networkStateChangeCount++;
        if (isConnected) {
            noInternetTVED.setBackgroundColor(getResources().getColor(R.color.green));
            noInternetTVED.setText(getResources().getString(R.string.back_online));

            if (networkStateChangeCount >= 2) {
                //  checkNextActivity();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    CustomVisibility.collapse(noInternetTVED, 500);
                }
            }, 2000);
        } else {
            noInternetTVED.setBackgroundColor(getResources().getColor(R.color.red));
            noInternetTVED.setText(getResources().getString(R.string.no_internet_connection));
            CustomVisibility.expand(noInternetTVED, 500);
        }
    }
    //-------------for internet check-------end-----------------


    private void loadSecondCategory() {
        /*thirdCategoryAdapter = new ThirdCategoryAdapter(this, productLists);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecyclerView.setAdapter(thirdCategoryAdapter);*/
        thirdCategoryAdapter = new ThirdCategoryAdapter(this, productLists,this);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categoryRecyclerView.setAdapter(thirdCategoryAdapter);
        swipeRefreshLayout.setRefreshing(false);
        thirdCategoryAdapter.notifyDataSetChanged();
    }

    private List<ProductList> matchCartAddedProduct(List<ProductList> productLists) {
        if (cartProductLists!=null && cartProductLists.size()>0){
            for (ProductList product: productLists){
                for (ProductList cartProduct: cartProductLists){
                    if (product.getProductId()==cartProduct.getProductId()){
                        product.setCountForCart(cartProduct.getCountForCart());
                        break;
                    }else {
                        product.setCountForCart(0);
                    }
                }
            }

            return productLists;
        }else {
            return productLists;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.cart_menu);

        View actionView = menuItem.getActionView();
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        textCartItemCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //textCartItemCount.setText(String.valueOf(Common.getCount+1));
        // Toast.makeText(this, ""+Common.getCount, Toast.LENGTH_SHORT).show();

        if (cartProductLists != null && cartProductLists.size() > 0) {
            setupBadge(cartProductLists.size());
        } else {
            setupBadge(0);
        }
        return true;
    }

    private void setupBadge(int count) {
        if (textCartItemCount != null) {
            if (count == 0) {
                textCartItemCount.setVisibility(View.GONE);
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(count, 99)));
                textCartItemCount.setVisibility(View.VISIBLE);
            }
        }
    }

    public void colorChangeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }

    public void goToCart(View view) {
        Intent intent = new Intent(this, AddToCartActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

    public void backBtn(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
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
        if (cartProductLists.size() > 0) {
            setupBadge(cartProductLists.size());
           // fab.setCount(cartProductLists.size());
        } else {
            setupBadge(0);
            //fab.setCount(0);
        }
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
        if (cartProductLists.size() > 0) {
            setupBadge(cartProductLists.size());
           // fab.setCount(cartProductLists.size());
        } else {
            setupBadge(0);
            //fab.setCount(0);
        }
        editor.putString("cartProductLists", new Gson().toJson(cartProductLists));
        editor.apply();
    }

    @Override
    public void onDeleteFromCart(ProductList productList) {

    }



}
