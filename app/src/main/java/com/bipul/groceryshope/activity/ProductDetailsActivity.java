package com.bipul.groceryshope.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bipul.groceryshope.Adapter.RelatedProductAdapter;
import com.bipul.groceryshope.Adapter.SecondCategoryAdapter;
import com.bipul.groceryshope.R;
import com.bipul.groceryshope.Utils.Common;
import com.bipul.groceryshope.Utils.ConnectivityHelper;
import com.bipul.groceryshope.Utils.CustomVisibility;
import com.bipul.groceryshope.Utils.NetworkChangeReceiver;
import com.bipul.groceryshope.interfaces.ApiInterface;
import com.bipul.groceryshope.interfaces.OnNetworkStateChangeListener;
import com.bipul.groceryshope.model.RelatedProduct;
import com.bipul.groceryshope.model.SecondCategory;
import com.bipul.groceryshope.modelForProductDetails.Product;
import com.bipul.groceryshope.modelForProductDetails.ProductDetailsResponse;
import com.bipul.groceryshope.webApi.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity implements OnNetworkStateChangeListener {

    private RecyclerView secondCategoryRecyclerView;
    private ArrayList<RelatedProduct> relatedProducts = new ArrayList<>();
    private RelatedProductAdapter relatedProductAdapter;

    ImageView productImageIV, increaseQuantity, reduceQuantity;
    TextView productNameTV, productQuantityTV, productPriceTV,
            itemQuantity, addtobag,unitName,upozilaNameTV,unionNameTV,
            storeNameTV,descriptionTV,descriptionText;

    int count = 0;

    Toolbar toolbar;
    SearchView searchView;
    String productId;

    private ApiInterface apiInterface;

    //for internet--------------start----------------
    private int networkStateChangeCount = 0;
    private NetworkChangeReceiver mNetworkReceiver;
    private SwipeRefreshLayout swipeRefreshLayout;
    TextView noInternetTVED;
    //for internet--------------end------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        colorChangeStatusBar();

        initSwipeLayout();

        toolbar = findViewById(R.id.toolbar);

        init();

        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");

        //fatchProcuctDetails(4);

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
                    fatchProcuctDetails(Integer.parseInt(productId));
                    swipeRefreshLayout.setRefreshing(true);
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
                    fatchProcuctDetails(Integer.parseInt(productId));
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


    private void fatchProcuctDetails(int id) {
        apiInterface.getProductDetails("A1b1C2d32564kjhkjadu", id).enqueue(new Callback<ProductDetailsResponse>() {
            @Override
            public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {

                ProductDetailsResponse responses = response.body();
                Product product = responses.getData().getProduct();
                Picasso.get().load("http://gobazaar.com.bd/public/upload/product/" + product.getPicture())
                        .into(productImageIV);

                productPriceTV.setText(String.valueOf(product.getRate()));
                productNameTV.setText(product.getProductName());
                unitName.setText(product.getUnitName());
                unionNameTV.setText(product.getUnionName());
                upozilaNameTV.setText(product.getUpazilaName());
                storeNameTV.setText(product.getShopName());

                if (product.getDescription() == null){
                    descriptionText.setVisibility(View.GONE);
                    descriptionTV.setVisibility(View.GONE);
                }else {
                    descriptionTV.setText(product.getDescription());
                }

                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_at_to_cart, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        return true;
    }

    private void init() {
        productImageIV = findViewById(R.id.productImageIV);
        productNameTV = findViewById(R.id.productNameTV);
        productPriceTV = findViewById(R.id.productPriceTV);
        productQuantityTV = findViewById(R.id.productQuantityTV);
        increaseQuantity = findViewById(R.id.increaseQuantity);
        itemQuantity = findViewById(R.id.itemQuantity);
        reduceQuantity = findViewById(R.id.reduceQuantity);
        addtobag = findViewById(R.id.addtobag);
        unitName = findViewById(R.id.unitName);
        upozilaNameTV = findViewById(R.id.upazilaNameTV);
        unionNameTV = findViewById(R.id.unionNameTV);
        storeNameTV = findViewById(R.id.storeNameTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        descriptionText = findViewById(R.id.descriptionText);

        noInternetTVED = findViewById(R.id.noInternetTVE);
        mNetworkReceiver = new NetworkChangeReceiver(this);
        registerNetworkBroadcast();



        //init retrofit
        apiInterface = RetrofitClient.getRetrofitWithoutHome().create(ApiInterface.class);


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
                if (count == 0) {
                    addtobag.setVisibility(View.VISIBLE);
                    itemQuantity.setVisibility(View.GONE);
                    reduceQuantity.setVisibility(View.GONE);
                }


            }
        });
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

    public void goToCart(View view) {
        Intent intent = new Intent(this, AddToCartActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

/*

    private void getAllSecondCategory() {
        relatedProducts.add(new RelatedProduct(R.drawable.boiler_murgi, "Broiler Murgi",
                "1 KG", "260"));
        relatedProducts.add(new RelatedProduct(R.drawable.murgi1, "Fresh Murgi",
                "1 pisces", "2050"));
        relatedProducts.add(new RelatedProduct(R.drawable.murgi3, "Lights & Electrical",
                "1 pisces", "2050"));
        relatedProducts.add(new RelatedProduct(R.drawable.boiler_murgi, "Fruits & Vegetables",
                "1 pisces", "2050"));
        relatedProducts.add(new RelatedProduct(R.drawable.frozen_canned, "Frozen Canned",
                "1 pisces", "2050"));
        relatedProducts.add(new RelatedProduct(R.drawable.cleaning_supplies, "Cleaning & Supplies",
                "1 pisces", "2050"));
        relatedProducts.add(new RelatedProduct(R.drawable.bread_bakery, "Cleaning & Supplies",
                "1 pisces", "2050"));
    }

    private void loadSecondCategory() {
        secondCategoryRecyclerView = findViewById(R.id.relatedProductRecyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        relatedProductAdapter = new RelatedProductAdapter(this, relatedProducts);
        secondCategoryRecyclerView.setLayoutManager(layoutManager);
        secondCategoryRecyclerView.setAdapter(relatedProductAdapter);
    }
*/


}
