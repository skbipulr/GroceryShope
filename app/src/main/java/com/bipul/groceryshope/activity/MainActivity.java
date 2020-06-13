package com.bipul.groceryshope.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bipul.groceryshope.Adapter.CategoryAdapter;
import com.bipul.groceryshope.Adapter.CustomExpandableListAdapter;
import com.bipul.groceryshope.Adapter.FeatureProductAdapter;
import com.bipul.groceryshope.Adapter.SecondCategoryAdapter;
import com.bipul.groceryshope.Adapter.SliderAdapterExample;
import com.bipul.groceryshope.R;
import com.bipul.groceryshope.Utils.Common;
import com.bipul.groceryshope.modelFodSlider.SliderResponse;
import com.bipul.groceryshope.datasource.ExpandableListDataSource;
import com.bipul.groceryshope.interfaces.ApiInterface;

import com.bipul.groceryshope.model.SecondCategory;
import com.bipul.groceryshope.modelFodSlider.SliderProduct;
import com.bipul.groceryshope.modelForFeatureProduct.Category;
import com.bipul.groceryshope.modelForFeatureProduct.FeatureProductResponse;
import com.bipul.groceryshope.modelForProducts.Data;
import com.bipul.groceryshope.modelForProducts.Product;
import com.bipul.groceryshope.modelForProducts.ProductList;
import com.bipul.groceryshope.modelForProducts.ProductsResponse;
import com.bipul.groceryshope.webApi.RetrofitClient;
import com.google.android.material.navigation.NavigationView;
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
        NavigationView.OnNavigationItemSelectedListener {


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
    private List<SliderProduct> sliderProducts = new ArrayList<>();

    //Category
    private RecyclerView categoryRecyclerView;
    private List<Product> categories = new ArrayList<>();
    private List<ProductList> productLists = new ArrayList<>();
    private CategoryAdapter categoryAdapter;

    private RecyclerView secondCategoryRecyclerView;
    private ArrayList<SecondCategory> secondCategories = new ArrayList<>();
    private SecondCategoryAdapter secondCategoryAdapter;

    private RecyclerView groceriesRecyclerView;
    private List<Category> featureProducts = new ArrayList<>();
    private FeatureProductAdapter featureProductAdapter;

    private int categoryId;
    private int productCategoryId;

    SearchView searchView;

    private ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initImageSlider();
        init();
        initItems();

        addDrawerItems();
        setupDrawer();

        colorChangeStatusBar();
        loadCategory();
        loadGroceries();
        getAllFeatureProducts();
        getAllSlider();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        return true;
    }

    public void CheckOut(View view) {
        Intent intent = new Intent(this, OrderListActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

    private void getAllFeatureProducts() {

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
                    featureProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FeatureProductResponse> call, Throwable t) {

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
      /*  sliderView.setIndicatorSelectedColor(R.color.main_color);
        sliderView.setIndicatorUnselectedColor(R.color.main_color);*/
        sliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        /*-------------initBanner---end----------*/
    }

    private void init() {
       /* ExpandableListView navigationView = (ExpandableListView) findViewById(R.id.navList);
        //navigationView.setSelectionAfterHeaderView();
        navigationView.getHeaderViewsCount();

        View headerView=navigationView.getRootView();
        TextView nameTV = headerView.findViewById(R.id.nameTV);
        nameTV.setText(Common.currentUser);
*/

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawers();
        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        mExpandableListView.addHeaderView(listHeaderView);

        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        mExpandableTitle = new ArrayList(mExpandableListData.keySet());

        apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
    }


    private void getAllSlider() {

        apiInterface.getSliderResponse("A1b1C2d32564kjhkjadu").enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {
                SliderResponse sliderResponse = response.body();
                sliderProducts = sliderResponse.getData().getSliderProducts();
                //Toast.makeText(MainActivity.this, "" + sliderProducts.size(), Toast.LENGTH_SHORT).show();
                SliderAdapterExample adapter = new SliderAdapterExample(MainActivity.this, sliderProducts);
                sliderView.setSliderAdapter(adapter);

            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initItems() {
        items = getResources().getStringArray(R.array.all_title);
        mujibItem = getResources().getStringArray(R.array.mujib);
    }

    private void loadCategory() {
        apiInterface.getProducts("A1b1C2d32564kjhkjadu").enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();

                categoryRecyclerView = findViewById(R.id.categoryRecyclerView);

                categories = productsResponse.getData().getProducts();
                List<ProductList> productLists;
                for (int i = 0; i <=3; i++) {
                    productLists = productsResponse.getData().getProducts().get(i).getProductList();
//for second Category
                    secondCategoryRecyclerView = findViewById(R.id.secondCategoryRecyclerView);
                    secondCategoryRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    secondCategoryAdapter = new SecondCategoryAdapter(MainActivity.this, productLists);
                    secondCategoryRecyclerView.setAdapter(secondCategoryAdapter);
                }
                //Data data = new Data(productsResponse.getData().getProducts());
                //categories = data.getProducts();

                categoryAdapter = new CategoryAdapter(MainActivity.this, categories);

                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                categoryRecyclerView.setLayoutManager(layoutManager);
                categoryRecyclerView.setAdapter(categoryAdapter);


            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {

            }
        });


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
            switch (childPosition) {
                case 0:
                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    return false;
            }

        } else if (groupPosition == 1) {

            switch (childPosition) {
                case 0:
                    Toast.makeText(this, "c1", Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(MainActivity.this, Media.class));
                    break;
                case 1:
                    Toast.makeText(this, "c2", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    break;

                default:
                    return false;
            }

        } else if (groupPosition == 2) {
            switch (childPosition) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;

            }
        } else if (groupPosition == 3) {
            switch (childPosition) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;

            }
        } else if (groupPosition == 4) {
            switch (childPosition) {
                case 0:
                    break;
                case 1:
                    break;
            }


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
}
