package com.example.navigationdrawermateriallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdrawermateriallibrary.Prevalent.PrevalentItem;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class DisplayItemsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<FoodItem> mFoodData;
    private FoodItemAdapter mAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_items_alternate);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializeNavigationDrawer();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_display_items);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_display_items);
        if (navigationView != null) {
            //navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getApplicationContext());
            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        }

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(PrevalentItem.currentOnlineUser.getName());

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //handling adaptive layout to remove swiping if rotated to landscape mode
        int gridColumnCount =
                getResources().getInteger(R.integer.grid_column_count);
        int swipeDirs;
        if(gridColumnCount > 1){
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new
                GridLayoutManager(this, gridColumnCount));

        // Initialize the ArrayList that will contain the data.
        mFoodData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        //mAdapter = new SportsAdapter(this, mSportsData);
        //mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        if (savedInstanceState!=null){
            // Clear the existing data (to avoid duplication).
            mFoodData.clear();
            //mFoodData = savedInstanceState.getParcelableArrayList("objectarraylist");
            //Log.d("MainActivity", mSportsData.get(0).getTitle());
            // Notify the adapter of the change.
            //mAdapter.notifyDataSetChanged();
        }else{
            initializeData();
        }

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new FoodItemAdapter(this, (ArrayList<FoodItem>) mFoodData); //but first attach instantiate the adapter with the data
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.DOWN | ItemTouchHelper.UP, swipeDirs) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mFoodData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mFoodData.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        helper.attachToRecyclerView(mRecyclerView);
    }

    private void initializeNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_display_items);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view_display_items);
        if (navigationView != null) {
            //navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getApplicationContext());
            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        }

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(PrevalentItem.currentOnlineUser.getName());
    }

    /**
     * Initialize the sports data from resources.
     */
    private void initializeData() {
        // Get the resources from the XML file.
        String[] foodItemsList = getResources()
                .getStringArray(R.array.food_items);
        String[] foodItemsPrice = getResources()
                .getStringArray(R.array.food_prices);
        TypedArray foodImageResources =
                getResources().obtainTypedArray(R.array.food_images);
        //int[] sportsImage = getResources()
        //        .getIntArray(R.array.sports_images);

        // Clear the existing data (to avoid duplication).
        mFoodData.clear();

        // Create the ArrayList of Sports objects with titles and
        // information about each sport.
        for(int i=0;i<foodItemsList.length;i++){

            //FoodItem myFoodItem = new FoodItem(foodItemsList[i], foodItemsPrice[i], foodImageResources.getResourceId(i,0));

            mFoodData.add(new FoodItem(foodItemsList[i], foodItemsPrice[i], foodImageResources.getResourceId(i,0)));
        }

        foodImageResources.recycle(); //Clean up the data in the typed array once you have created the Sport data ArrayList:

        // Notify the adapter of the change.
        //mAdapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        initializeData();
        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }


    /**
     * Handles a navigation drawer item click. It detects which item was
     * clicked and displays a toast message showing which item.
     * @param item  Item in the navigation drawer
     * @return      Returns true after closing the nav drawer
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_cart:
                // Handle the camera import action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_profile));
                return true;
            case R.id.nav_orders:
                // Handle the gallery action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_favourites));
                return true;
            case R.id.nav_categories:
                // Handle the slideshow action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_previous_orders));
                return true;
            case R.id.nav_settings:
                // Handle the tools action (for now display a toast).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_report_problem));
                return true;
            case R.id.nav_logout:
                Paper.book().destroy();
                Intent intent = new Intent(DisplayItemsActivity.this, WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            default:
                return false;
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
       // outState.putParcelableArrayList("objectarraylist", mFoodData);
        //save the current variables
        super.onSaveInstanceState(outState);
    }



}

