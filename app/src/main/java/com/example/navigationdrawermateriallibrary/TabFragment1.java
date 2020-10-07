package com.example.navigationdrawermateriallibrary;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

//import com.example.walimiwithdrawer.R;



public class TabFragment1 extends Fragment {

    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<FoodItem> mFoodData;
    private FoodItemAdapter mAdapter;
    private Toolbar toolbar;

    public TabFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the ArrayList that will contain the data.
        mFoodData = new ArrayList<>();

        // Get the data.
        if (savedInstanceState!=null){
            mFoodData.clear();  // Clear the existing data (to avoid duplication).
        }else{
            initializeData();
        }

        mRecyclerView = view.findViewById(R.id.recycler_view);

        mAdapter = new FoodItemAdapter(getContext(), (ArrayList<FoodItem>) mFoodData);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void initializeData() {

            // Get the resources from the XML file.
            String[] foodItemsList = getResources()
                    .getStringArray(R.array.food_items);
            String[] foodItemsPrice = getResources()
                    .getStringArray(R.array.food_prices);
            TypedArray foodImageResources =
                    getResources().obtainTypedArray(R.array.food_images);

            mFoodData.clear();

            for(int i=0;i<foodItemsList.length;i++){
                mFoodData.add(new FoodItem(foodItemsList[i], foodItemsPrice[i], foodImageResources.getResourceId(i,0)));
            }
            foodImageResources.recycle(); //Clean up the data in the typed array once you have created the Sport data ArrayList:
        }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

