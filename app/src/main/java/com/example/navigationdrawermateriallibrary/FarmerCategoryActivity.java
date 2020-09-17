package com.example.navigationdrawermateriallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.PipedOutputStream;

public class FarmerCategoryActivity extends AppCompatActivity {

    private ImageButton FruitsButton;
    private ImageButton OrganicFoodsButton;
    private ImageButton FreshDairyButton;
    private ImageButton OtherFoodsButton;
    private ImageButton MeatButton;
    private ImageButton PoultryButton;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_category);

        Intent intent = getIntent();
        userName = intent.getStringExtra("Name");

        GetHandlesToViews();
        initializeClickListeners();
    }

    private void GetHandlesToViews() {
        FruitsButton = findViewById(R.id.Category_Fruits);
        OrganicFoodsButton = findViewById(R.id.Category_Organic_Food);
        FreshDairyButton = findViewById(R.id.Category_Fresh_Dairy);
        OtherFoodsButton = findViewById(R.id.Category_Other_Foods);
        MeatButton = findViewById(R.id.Category_Meats);
        PoultryButton = findViewById(R.id.Category_Poultry_Products);
    }

    private void initializeClickListeners() {

        FruitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmerCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("Category", "Fruits");
                intent.putExtra("Name", userName);
                startActivity(intent);
            }
        });

        OrganicFoodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FreshDairyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        MeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        PoultryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        OtherFoodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
