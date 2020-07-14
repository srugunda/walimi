package com.example.navigationdrawermateriallibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {

    // Member variables.
    private ArrayList<FoodItem> mFoodArrayList;
    private Context mContext;

    FoodItemAdapter(Context context, ArrayList<FoodItem> foodData) {
        this.mFoodArrayList = foodData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public FoodItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemAdapter.ViewHolder holder, int position) {
        // Get current sport.
        FoodItem currentFoodItem = mFoodArrayList.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentFoodItem);
    }

    @Override
    public int getItemCount() {
        return mFoodArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private TextView mFoodName;
        private TextView mFoodPrice;
        private ImageView mFoodImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Initialize the views. The list item is passed in and used to get the individual id elements
            mFoodName=itemView.findViewById(R.id.food_name);
            mFoodPrice=itemView.findViewById(R.id.price);
            mFoodImage=itemView.findViewById(R.id.foodImage);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(FoodItem currentFoodItem){
            // Populate the textviews with data.
            mFoodName.setText(currentFoodItem.getFoodName());
            mFoodPrice.setText(currentFoodItem.getFoodPrice());
            Glide.with(mContext).load(currentFoodItem.getImage()).into(mFoodImage);
        }

        @Override
        public void onClick(View v) {
            /*
            FoodItem currentFoodItem = mFoodArrayList.get(getAdapterPosition());

            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,mFoodImage,"imageMain");

            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("title", currentSport.getTitle());
            intent.putExtra("image", currentSport.getBannerImage());
            mContext.startActivity(intent, activityOptionsCompat.toBundle());
             */
        }
    }
}
