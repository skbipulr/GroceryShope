package com.bipul.groceryshope.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.bipul.groceryshope.Adapter.CategoryAdapter;
import com.bipul.groceryshope.Adapter.CustomExpandableListAdapter;
import com.bipul.groceryshope.Adapter.FeatureProductAdapter;
import com.bipul.groceryshope.Adapter.LatestCategoryAdapter;
import com.bipul.groceryshope.Adapter.SecondCategoryAdapter;
import com.bipul.groceryshope.Adapter.SliderAdapterExample;
import com.bipul.groceryshope.R;
import com.bipul.groceryshope.Utils.Common;
import com.bipul.groceryshope.Utils.ConnectivityHelper;
import com.bipul.groceryshope.Utils.CustomVisibility;
import com.bipul.groceryshope.Utils.NetworkChangeReceiver;
import com.bipul.groceryshope.interfaces.OnCartListener;
import com.bipul.groceryshope.interfaces.OnNetworkStateChangeListener;
import com.bipul.groceryshope.datasource.ExpandableListDataSource;
import com.bipul.groceryshope.interfaces.ApiInterface;

import com.bipul.groceryshope.model.SecondCategory;
import com.bipul.groceryshope.modelFodSlider.Slider;
import com.bipul.groceryshope.modelFodSlider.SliderResponse;
import com.bipul.groceryshope.modelForFeatureProduct.Category;
import com.bipul.groceryshope.modelForFeatureProduct.FeatureProductResponse;
import com.bipul.groceryshope.modelForLatestProduct.Datum;
import com.bipul.groceryshope.modelForLatestProduct.LatestProductResponse;
import com.bipul.groceryshope.modelForProducts.Product;
import com.bipul.groceryshope.modelForProducts.ProductList;
import com.bipul.groceryshope.modelForProducts.ProductsResponse;
import com.bipul.groceryshope.webApi.RetrofitClient;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements CustomExpandableListAdapter.OnExpandableListener,
        NavigationView.OnNavigationItemSelectedListener, OnNetworkStateChangeListener, OnCartListener {

    CounterFab fab;

    FrameLayout rlCart;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private RelativeLayout playViewRL;
    private String[] items;
    private String[] mujibItem;

    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;

    private List<String> mExpandableListTitle;
    private List<String> mExpandableTitle;
    private Map<String, List<String>> mExpandableListData;

    // for image Slider
    private SliderView sliderView;
    private SliderAdapterExample sliderAdapterExample;


    //Category
    private RecyclerView categoryRecyclerView;

    private List<ProductList> productLists0;
    private List<ProductList> productLists1;
    private List<ProductList> productLists2;
    private List<ProductList> productLists3;
    private List<ProductList> productLists4;
    private List<ProductList> productLists5;
    private List<ProductList> productLists6;
    private List<ProductList> productLists7;
    private List<ProductList> productLists8;

    private List<Datum> latestProductLists;
    private List<Datum> latestProductLists1;
    private List<Datum> cartLatestProductLists;

    private CategoryAdapter categoryAdapter;


    private ArrayList<SecondCategory> secondCategories = new ArrayList<>();
    private SecondCategoryAdapter secondCategoryAdapter;
    private LatestCategoryAdapter latestProductAdapter;


    private RecyclerView groceriesRecyclerView;
    private List<Category> featureProducts = new ArrayList<>();
    private FeatureProductAdapter featureProductAdapter;

    private int categoryId;
    private int productCategoryId;

    //for internet--------------start----------------
    private int networkStateChangeCount = 0;
    private NetworkChangeReceiver mNetworkReceiver;
    private SwipeRefreshLayout swipeRefreshLayout;
    TextView noInternetTVED;
    //for internet--------------end------------------

    SearchView searchView;
    TextView textCartItemCount;

    private ApiInterface apiInterface;
    private ApiInterface apiInterfaceWithHome;

    LinearLayout singInLinearLyout;
    LinearLayout infoShowLinearLayout;

    private List<ProductList> cartProductLists;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSwipeLayout();
        init();
        initImageSlider();
        initItems();


        addDrawerItems();
        setupDrawer();

        colorChangeStatusBar();

        loadCategory0();
        loadCategory1();
        loadCategory2();
        loadCategory3();
        loadCategory4();
       // loadCategory5();
        //loadCategory6();
        /*loadCategory7();
        loadCategory8();*/

        loadLatestProduct();

        loadGroceries();
        getAllSlider();



    }


    private void loadLatestProduct() {
        apiInterfaceWithHome.getLatestProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<LatestProductResponse>() {
            @Override
            public void onResponse(Call<LatestProductResponse> call, Response<LatestProductResponse> response) {

                LatestProductResponse productsResponse = response.body();
                if (response.code() == 200) {

                    RecyclerView latestProductRecyclerView = findViewById(R.id.latestProductRecyclerView);
                    // sisuKhadoRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    LinearLayoutManager secondLayoutManager
                            = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    latestProductRecyclerView.setLayoutManager(secondLayoutManager);


                    latestProductLists  = productsResponse.getData();
                    latestProductAdapter = new LatestCategoryAdapter(MainActivity.this, matchCartAddedProductForLatest(latestProductLists), MainActivity.this);
                    latestProductRecyclerView.setAdapter(latestProductAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    latestProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<LatestProductResponse> call, Throwable t) {

            }
        });
    }

    private void getCartProductList() {
        cartProductLists = new ArrayList<>();
        sharedPreferences = getSharedPreferences("CartPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String cartProducts = sharedPreferences.getString("cartProductLists", "");
        if (!TextUtils.isEmpty(cartProducts)) {
            cartProductLists = new Gson().fromJson(cartProducts, new TypeToken<List<ProductList>>() {
            }.getType());
            Log.d("BBBB", "" + cartProductLists.size());
            fab.setCount(cartProductLists.size());
            if (cartProductLists != null && cartProductLists.size() > 0) {
                setupBadge(cartProductLists.size());
            } else {
                setupBadge(0);
            }
            if (productLists1 != null && productLists1.size() > 0) {
                matchCartAddedProduct(productLists1);
                secondCategoryAdapter.notifyDataSetChanged();
            }
        } else {
            setupBadge(0);
            fab.setCount(0);
            Log.d("BBBB", "" + cartProductLists.size());
        }
    }

    private void getCartProductListForLatestProduct() {
        cartLatestProductLists = new ArrayList<>();
        sharedPreferences = getSharedPreferences("CartPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String cartProducts = sharedPreferences.getString("cartProductLists", "");
        if (!TextUtils.isEmpty(cartProducts)) {
            cartLatestProductLists = new Gson().fromJson(cartProducts, new TypeToken<List<Datum>>() {
            }.getType());
            Log.d("BBBB", "" + cartLatestProductLists.size());
            fab.setCount(cartLatestProductLists.size());
            if (cartLatestProductLists != null && cartLatestProductLists.size() > 0) {
                setupBadge(cartLatestProductLists.size());
            } else {
                setupBadge(0);
            }
            if (latestProductLists1 != null && latestProductLists1.size() > 0) {
                matchCartAddedProductForLatest(latestProductLists1);
                latestProductAdapter.notifyDataSetChanged();
            }
        } else {
            setupBadge(0);
            fab.setCount(0);
            Log.d("BBBB", "" + cartLatestProductLists.size());
        }
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
                    loadCategory1();
                    loadGroceries();
                    //  findViewById(R.id.NestedScrollView).setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(true);

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
        getCartProductListForLatestProduct();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.cart_menu);

        View actionView = menuItem.getActionView();
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        rlCart = actionView.findViewById(R.id.rlCart);
        rlCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(MainActivity.this, AddToCartActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(cartIntent);
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

    public void CheckOut(View view) {
        Intent intent = new Intent(this, OrderListActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

    private void loadGroceries() {
        groceriesRecyclerView = findViewById(R.id.groceriesRecyclerView);
        apiInterface.getFeatureProduct("A1b1C2d32564kjhkjadu").enqueue(new Callback<FeatureProductResponse>() {
            @Override
            public void onResponse(Call<FeatureProductResponse> call, Response<FeatureProductResponse> response) {
                if (response.code() == 200) {
                    FeatureProductResponse featureProductResponse = response.body();
                    featureProducts = featureProductResponse.getData().getCategories();
                    featureProductAdapter = new FeatureProductAdapter(MainActivity.this, featureProducts);

                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    groceriesRecyclerView.setLayoutManager(layoutManager);
                    groceriesRecyclerView.setAdapter(featureProductAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    featureProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FeatureProductResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initImageSlider() {
        sliderView = findViewById(R.id.imageSlider);
        /*sliderAdapterExample = new SliderAdapterExample(MainActivity.this,sliderProducts);
        sliderView.setSliderAdapter(sliderAdapterExample);
*/
        /*-------------initBanner---start----------*/
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

        sliderView.startAutoCycle();

      /*  sliderView.setIndicatorSelectedColor(R.color.main_color);
        sliderView.setIndicatorUnselectedColor(R.color.main_color);*/
        sliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        /*-------------initBanner---end----------*/
    }

    private void init() {
        productLists1 = new ArrayList<>();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawers();
        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noInternetTVED = findViewById(R.id.noInternetTVE);
        mNetworkReceiver = new NetworkChangeReceiver(this);
        registerNetworkBroadcast();


        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        mExpandableListView.addHeaderView(listHeaderView);
        singInLinearLyout = listHeaderView.findViewById(R.id.signInLinearLayout);
        infoShowLinearLayout = listHeaderView.findViewById(R.id.infoShowLinearLayout);

        if (Common.assess_token != null) {
            singInLinearLyout.setVisibility(View.GONE);
            infoShowLinearLayout.setVisibility(View.VISIBLE);
            TextView nameTV = listHeaderView.findViewById(R.id.nameTV);
            TextView mobileNoTV = listHeaderView.findViewById(R.id.mobileTV);
            nameTV.setText(Common.name);
            mobileNoTV.setText(Common.mobile);

            infoShowLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                }
            });
            // Toast.makeText(this, "" + Common.name, Toast.LENGTH_SHORT).show();
        }


        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        mExpandableTitle = new ArrayList(mExpandableListData.keySet());

        apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
        apiInterfaceWithHome = RetrofitClient.getRetrofitWithoutHome().create(ApiInterface.class);

        fab = (CounterFab) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(MainActivity.this, AddToCartActivity.class);
                startActivity(cartIntent);
            }
        });

        //fab.setCount(new DatabaseOpenHelper(this).getCountCart());
    }


    private void getAllSlider() {

        apiInterface.getSliderResponse("A1b1C2d32564kjhkjadu").enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {
                SliderResponse sliderResponse = response.body();
                List<Slider> sliderProducts = new ArrayList<>();
                assert sliderResponse != null;
                sliderProducts = sliderResponse.getData().getSlider();
                // Toast.makeText(MainActivity.this, "" + sliderProducts.size(), Toast.LENGTH_SHORT).show();
                SliderAdapterExample adapter = new SliderAdapterExample(MainActivity.this, sliderProducts);
                sliderView.setSliderAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {

            }
        });
    }

    private void initItems() {
        items = getResources().getStringArray(R.array.all_title);
        mujibItem = getResources().getStringArray(R.array.mujib);
    }

    /*category wise product---------- start------------*/
    private void loadCategory0() {
        apiInterface.getProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();

                if (response.code() == 200) {
                    RecyclerView secondCategoryRecyclerView0 = findViewById(R.id.secondCategoryRecyclerView0);
                    // sisuKhadoRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    LinearLayoutManager secondLayoutManager
                            = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    secondCategoryRecyclerView0.setLayoutManager(secondLayoutManager);
                    productLists0 = productsResponse.getData().getProducts().get(0).getProductList();
                    secondCategoryAdapter = new SecondCategoryAdapter(MainActivity.this, matchCartAddedProduct(productLists0), MainActivity.this);
                    secondCategoryRecyclerView0.setAdapter(secondCategoryAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    secondCategoryAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadCategory1() {
        apiInterface.getProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();
                List<Product> categories = new ArrayList<>();
                categoryRecyclerView = findViewById(R.id.categoryRecyclerView);

                categories = productsResponse.getData().getProducts();
                // Toast.makeText(MainActivity.this, "" + categories.size(), Toast.LENGTH_SHORT).show();
                RecyclerView secondCategoryRecyclerView1;
                productLists1 = productsResponse.getData().getProducts().get(1).getProductList();
                //for second Category
                secondCategoryRecyclerView1 = findViewById(R.id.secondCategoryRecyclerView1);
                // secondCategoryRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                LinearLayoutManager secondLayoutManager
                        = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                secondCategoryRecyclerView1.setLayoutManager(secondLayoutManager);
                secondCategoryAdapter = new SecondCategoryAdapter(MainActivity.this, matchCartAddedProduct(productLists1), MainActivity.this);
                secondCategoryRecyclerView1.setAdapter(secondCategoryAdapter);
                swipeRefreshLayout.setRefreshing(false);
                secondCategoryAdapter.notifyDataSetChanged();



                //Data data = new Data(productsResponse.getData().getProducts());
                //categories = data.getProducts();

                //for Category
                categoryAdapter = new CategoryAdapter(MainActivity.this, categories);
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                categoryRecyclerView.setLayoutManager(layoutManager);
                categoryRecyclerView.setAdapter(categoryAdapter);
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

            }
        });


    }

    private void loadCategory2() {
        apiInterface.getProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();

                if (response.code() == 200) {
                    RecyclerView secondCategoryRecyclerView2 = findViewById(R.id.secondCategoryRecyclerView2);
                    // sisuKhadoRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    LinearLayoutManager secondLayoutManager
                            = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    secondCategoryRecyclerView2.setLayoutManager(secondLayoutManager);
                    productLists2 = productsResponse.getData().getProducts().get(2).getProductList();
                    secondCategoryAdapter = new SecondCategoryAdapter(MainActivity.this, matchCartAddedProduct(productLists2), MainActivity.this);
                    secondCategoryRecyclerView2.setAdapter(secondCategoryAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    secondCategoryAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadCategory3() {
        apiInterface.getProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();

                if (response.code() == 200) {
                    RecyclerView secondCategoryRecyclerView3 = findViewById(R.id.secondCategoryRecyclerView3);
                    LinearLayoutManager secondLayoutManager
                            = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    secondCategoryRecyclerView3.setLayoutManager(secondLayoutManager);
                    productLists3 = productsResponse.getData().getProducts().get(3).getProductList();
                    secondCategoryAdapter = new SecondCategoryAdapter(MainActivity.this, matchCartAddedProduct(productLists3), MainActivity.this);
                    secondCategoryRecyclerView3.setAdapter(secondCategoryAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    secondCategoryAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadCategory4() {
        apiInterface.getProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();

                if (response.code() == 200) {
                    RecyclerView secondCategoryRecyclerView4 = findViewById(R.id.secondCategoryRecyclerView4);
                    // sisuKhadoRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    LinearLayoutManager secondLayoutManager
                            = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    secondCategoryRecyclerView4.setLayoutManager(secondLayoutManager);
                    productLists4 = productsResponse.getData().getProducts().get(4).getProductList();
                    secondCategoryAdapter = new SecondCategoryAdapter(MainActivity.this, matchCartAddedProduct(productLists4), MainActivity.this);
                    secondCategoryRecyclerView4.setAdapter(secondCategoryAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    secondCategoryAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadCategory5() {
        apiInterface.getProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();

                if (response.code() == 200) {
                    RecyclerView secondCategoryRecyclerView5 = findViewById(R.id.secondCategoryRecyclerView5);
                    // sisuKhadoRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    LinearLayoutManager secondLayoutManager
                            = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    secondCategoryRecyclerView5.setLayoutManager(secondLayoutManager);
                    productLists5 = productsResponse.getData().getProducts().get(5).getProductList();
                    secondCategoryAdapter = new SecondCategoryAdapter(MainActivity.this, matchCartAddedProduct(productLists5), MainActivity.this);
                    secondCategoryRecyclerView5.setAdapter(secondCategoryAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    secondCategoryAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadCategory6() {
        apiInterface.getProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();

                if (response.code() == 200) {
                    RecyclerView secondCategoryRecyclerView5 = findViewById(R.id.secondCategoryRecyclerView6);
                    // sisuKhadoRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    LinearLayoutManager secondLayoutManager
                            = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    secondCategoryRecyclerView5.setLayoutManager(secondLayoutManager);
                    productLists6 = productsResponse.getData().getProducts().get(6).getProductList();
                    secondCategoryAdapter = new SecondCategoryAdapter(MainActivity.this, matchCartAddedProduct(productLists6), MainActivity.this);
                    secondCategoryRecyclerView5.setAdapter(secondCategoryAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    secondCategoryAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadCategory7() {
        apiInterface.getProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();

                if (response.code() == 200) {
                    RecyclerView secondCategoryRecyclerView7 = findViewById(R.id.secondCategoryRecyclerView7);
                    // sisuKhadoRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    LinearLayoutManager secondLayoutManager
                            = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    secondCategoryRecyclerView7.setLayoutManager(secondLayoutManager);
                    productLists7 = productsResponse.getData().getProducts().get(7).getProductList();
                    secondCategoryAdapter = new SecondCategoryAdapter(MainActivity.this, matchCartAddedProduct(productLists7), MainActivity.this);
                    secondCategoryRecyclerView7.setAdapter(secondCategoryAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    secondCategoryAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void loadCategory8() {
        apiInterface.getProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();

                if (response.code() == 200) {
                    RecyclerView secondCategoryRecyclerView5 = findViewById(R.id.secondCategoryRecyclerView8);
                    // sisuKhadoRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    LinearLayoutManager secondLayoutManager
                            = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    secondCategoryRecyclerView5.setLayoutManager(secondLayoutManager);
                    productLists8 = productsResponse.getData().getProducts().get(8).getProductList();
                    secondCategoryAdapter = new SecondCategoryAdapter(MainActivity.this, matchCartAddedProduct(productLists8), MainActivity.this);
                    secondCategoryRecyclerView5.setAdapter(secondCategoryAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    secondCategoryAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    /*category wise product---------- end------------*/


    private List<ProductList> matchCartAddedProduct(List<ProductList> productLists) {
        if (cartProductLists != null && cartProductLists.size() > 0) {
            for (ProductList product : productLists) {
                for (ProductList cartProduct : cartProductLists) {
                    if (product.getProductId() == cartProduct.getProductId()) {
                        product.setCountForCart(cartProduct.getCountForCart());
                        break;
                    } else {
                        product.setCountForCart(0);
                    }
                }
            }

            return productLists;
        } else {
            return productLists;
        }
    }

    private List<Datum> matchCartAddedProductForLatest(List<Datum> productLists) {
        if (cartLatestProductLists != null && cartLatestProductLists.size() > 0) {
            for (Datum product : productLists) {
                for (Datum cartProduct : cartLatestProductLists) {
                    if (product.getId() == cartProduct.getId()) {
                        product.setCountForCart(cartProduct.getCountForCart());
                        break;
                    } else {
                        product.setCountForCart(0);
                    }
                }
            }

            return productLists;
        } else {
            return productLists;
        }
    }

    private void addDrawerItems() {
        mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData, this);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition == 5) {

                } else if (groupPosition == 6) {
                } else if (groupPosition == 7) {
                    if (Common.assess_token != null) {
                        infoShowLinearLayout.setVisibility(View.GONE);
                        singInLinearLyout.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                    }

                } else if (groupPosition == 8) {

                }
                return false;
            }
        });
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {


                //   getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                // getSupportActionBar().setTitle(R.string.film_genres);
                // Toast.makeText(MainActivity.this, groupPosition+"", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                /*    getSupportActionBar().setTitle(R.string.film_genres);*/
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGroupItemListener(int position) {

    }

    @Override
    public boolean onChildItemListener(int groupPosition, int childPosition) {
        // Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
        if (groupPosition == 0) {


        } else if (groupPosition == 1) {


        } else if (groupPosition == 2) {


        } else if (groupPosition == 3) {


        } else if (groupPosition == 4) {


        } else if (groupPosition == 5) {


        } else if (groupPosition == 6) {


        }
       /* else  if (groupPosition ==6){
            startActivity(new Intent(MainActivity.this, CommitteeActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }*/
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void colorChangeStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }

    public void goSignIn(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
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
            fab.setCount(cartProductLists.size());
        } else {
            setupBadge(0);
            fab.setCount(0);
        }
        Log.d("BBBB", "OnCartAdded: " + cartProductLists.size());
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
        Log.d("BBBB", "OnCartRemoved: " + cartProductLists.size());
        if (cartProductLists.size() > 0) {
            setupBadge(cartProductLists.size());
            fab.setCount(cartProductLists.size());
        } else {
            setupBadge(0);
            fab.setCount(0);
        }
        editor.putString("cartProductLists", new Gson().toJson(cartProductLists));
        editor.apply();
    }

    @Override
    public void onDeleteFromCart(ProductList productList) {

    }

    @Override
    public void OnCartAddedForLatest(Datum productList) {
        if (cartLatestProductLists.size() > 0) {
            boolean isMatched = false;
            for (Datum cartProduct : cartLatestProductLists) {
                if (cartProduct.getId() == productList.getId()) {
                    cartProduct.setCountForCart(cartProduct.getCountForCart() + 1);
                    isMatched = true;
                    break;
                }
            }
            if (!isMatched) {
                cartLatestProductLists.add(productList);
            }
        } else {
            cartLatestProductLists.add(productList);
        }
        if (cartLatestProductLists.size() > 0) {
            setupBadge(cartLatestProductLists.size());
            fab.setCount(cartLatestProductLists.size());
        } else {
            setupBadge(0);
            fab.setCount(0);
        }
        Log.d("BBBB", "OnCartAdded: " + cartLatestProductLists.size());
        editor.putString("cartProductLists", new Gson().toJson(cartLatestProductLists));
        editor.apply();
    }

    @Override
    public void onCartRemovedForLatest(Datum productList) {
        if (cartLatestProductLists.size() > 0) {
            for (Datum cartProduct : cartLatestProductLists) {
                if (cartProduct.getId() == productList.getId()) {
                    if (cartProduct.getCountForCart() > 1) {
                        cartProduct.setCountForCart(cartProduct.getCountForCart() - 1);
                    } else {
                        cartLatestProductLists.remove(cartProduct);
                    }
                    break;
                }
            }
        }
        Log.d("BBBB", "OnCartRemoved: " + cartLatestProductLists.size());
        if (cartLatestProductLists.size() > 0) {
            setupBadge(cartLatestProductLists.size());
            fab.setCount(cartLatestProductLists.size());
        } else {
            setupBadge(0);
            fab.setCount(0);
        }
        editor.putString("cartProductLists", new Gson().toJson(cartLatestProductLists));
        editor.apply();
    }
}