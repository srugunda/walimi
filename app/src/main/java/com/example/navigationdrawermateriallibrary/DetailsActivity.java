package com.example.navigationdrawermateriallibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class DetailsActivity extends AppCompatActivity {

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<FoodItem> mFoodData;
    private FoodItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
       // outState.putParcelableArrayList("objectarraylist", mFoodData);
        //save the current variables
        super.onSaveInstanceState(outState);
    }
}

