package com.bipul.groceryshope.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bipul.groceryshope.Adapter.CategoryAdapter;
import com.bipul.groceryshope.Adapter.CustomExpandableListAdapter;
import com.bipul.groceryshope.Adapter.GroceriesAdapter;
import com.bipul.groceryshope.Adapter.SecondCategoryAdapter;
import com.bipul.groceryshope.Adapter.SliderAdapterExample;
import com.bipul.groceryshope.R;
import com.bipul.groceryshope.datasource.ExpandableListDataSource;
import com.bipul.groceryshope.model.Category;
import com.bipul.groceryshope.model.Groceries;
import com.bipul.groceryshope.model.SecondCategory;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CustomExpandableListAdapter.OnExpandableListener{


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

    private RecyclerView categoryRecyclerView;
    private ArrayList<Category> categories = new ArrayList<>();
    private CategoryAdapter categoryAdapter;

    private RecyclerView secondCategoryRecyclerView;
    private ArrayList<SecondCategory> secondCategories = new ArrayList<>();
    private SecondCategoryAdapter secondCategoryAdapter;

    private RecyclerView groceriesRecyclerView;
    private ArrayList<Groceries> groceriesList = new ArrayList<>();
    private GroceriesAdapter groceriesAdapter;


   SearchView searchView;


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
        getAllCategory();

        loadSecondCategory();
        getAllSecondCategory();

        loadGroceries();
        getAllGroceries();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        return true;
    }

    public void CheckOut(View view) {
        Intent intent = new Intent(this,OrderListActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(intent);
    }

    private void getAllGroceries() {
        groceriesList.add(new Groceries(R.drawable.meat,"Meat"));
        groceriesList.add(new Groceries(R.drawable.oil,"Oil & Ghee"));
        groceriesList.add(new Groceries(R.drawable.pulese,"Pulses"));
        groceriesList.add(new Groceries(R.drawable.sugar,"Sugar"));
        groceriesList.add(new Groceries(R.drawable.meat,"Meat"));
        groceriesList.add(new Groceries(R.drawable.oil,"Oil & Ghee"));
        groceriesList.add(new Groceries(R.drawable.pulese,"Pulses"));
        groceriesList.add(new Groceries(R.drawable.sugar,"Sugar"));

    }

    private void loadGroceries() {
        groceriesRecyclerView = findViewById(R.id.groceriesRecyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        groceriesAdapter = new GroceriesAdapter(this, groceriesList);
        groceriesRecyclerView.setLayoutManager(layoutManager);
        groceriesRecyclerView.setAdapter(groceriesAdapter);
    }

    private void getAllSecondCategory() {
        secondCategories.add(new SecondCategory(R.drawable.boiler_murgi,"Broiler Murgi",
                "1 KG","260"));
        secondCategories.add(new SecondCategory(R.drawable.writing_drawing,"Writing & Drawing",
                "1 pisces","2050"));
        secondCategories.add(new SecondCategory(R.drawable.lights_electrical,"Lights & Electrical",
                "1 pisces","2050"));
        secondCategories.add(new SecondCategory(R.drawable.fruits_vegetables,"Fruits & Vegetables",
                "1 pisces","2050"));
        secondCategories.add(new SecondCategory(R.drawable.frozen_canned,"Frozen Canned",
                "1 pisces","2050"));
        secondCategories.add(new SecondCategory(R.drawable.cleaning_supplies,"Cleaning & Supplies",
                "1 pisces","2050"));
        secondCategories.add(new SecondCategory(R.drawable.bread_bakery,"Cleaning & Supplies",
                "1 pisces","2050"));
    }

    private void loadSecondCategory() {
        secondCategoryRecyclerView = findViewById(R.id.secondCategoryRecyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        secondCategoryAdapter = new SecondCategoryAdapter(this, secondCategories);
        secondCategoryRecyclerView.setLayoutManager(layoutManager);
        secondCategoryRecyclerView.setAdapter(secondCategoryAdapter);
    }

    public void initImageSlider() {
        sliderView = findViewById(R.id.imageSlider);
        sliderAdapterExample = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(sliderAdapterExample);

        /*-------------initBanner---start----------*/
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(R.color.main_color);
        sliderView.setIndicatorUnselectedColor(R.color.main_color);
        sliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
        sliderView.startAutoCycle();
        /*-------------initBanner---end----------*/
    }

    private void init() {
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



    }

    private void initItems() {
        items = getResources().getStringArray(R.array.all_title);
        mujibItem = getResources().getStringArray(R.array.mujib);
    }

    private void loadCategory() {
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryAdapter = new CategoryAdapter(this, categories);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void getAllCategory(){
        categories.add(new Category(R.drawable.general_grocery,"General Grocery"));
        categories.add(new Category(R.drawable.fresh_product,"Fresh Product"));
        categories.add(new Category(R.drawable.meat_fish,"Meat & Fish"));
        categories.add(new Category(R.drawable.beverages,"Beverages"));
        categories.add(new Category(R.drawable.furniture,"Personal Care"));
        categories.add(new Category(R.drawable.pet_food,"Pet Food"));
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
                    Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();  break;
                case 2:
                    Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();    break;
                case 3:
                    Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();   break;
                case 4:
                    Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();  break;
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

}
