package com.example.navigationdrawermateriallibrary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navigationdrawermateriallibrary.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

//import com.example.walimiwithdrawer.R;

public class TabFragment2 extends Fragment {

    DatabaseReference rootReference;
    DatabaseReference productsReference;
    TextView FarmerProductList;
    RecyclerView mRecyclerView;
    ProductItemAdapter mAdapter;

    public TabFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final List<Product> productList = new ArrayList<>();
        //FarmerProductList = getView().findViewById(R.id.farmer_product_list);
        mRecyclerView = getView().findViewById(R.id.by_farmer_recyclerview);

        //Get the images that were uploaded from the database
        //Put them in a recycler view and display
        rootReference = FirebaseDatabase.getInstance().getReference();
        productsReference = rootReference.child("Products");

        productsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                        Product product = data.getValue(Product.class);
                        productList.add(product);
                }

                mAdapter = new ProductItemAdapter(getContext(), productList);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                /*
                for(int index=0; index<productList.size(); index++) {
                    Product product = productList.get(index);
                    String name = product.getPname();
                    FarmerProductList.setText(name + "\n");
                    Log.d("Product ", "name: " + name);
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
