package com.example.navigationdrawermateriallibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.navigationdrawermateriallibrary.Model.Product;

import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {
    private List<Product> mProductList;
    private Context mContext;

    ProductItemAdapter(Context context, List<Product> productList){
        this.mProductList=productList;
        this.mContext=context;
    }


    @NonNull
    @Override
    public ProductItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductItemAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemAdapter.ViewHolder holder, int position) {

        Product currentProduct = mProductList.get(position);
        holder.bindTo(currentProduct);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ProductImage;
        private TextView FarmerName;
        private TextView ProductName;
        private TextView ProductPrice;
        private TextView ProductDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ProductImage = itemView.findViewById(R.id.product_Image);
            FarmerName = itemView.findViewById(R.id.farmer_name);
            ProductName = itemView.findViewById(R.id.product_name);
            ProductPrice = itemView.findViewById(R.id.product_price);
            ProductDescription = itemView.findViewById(R.id.product_description);
        }

        void bindTo(Product currentProduct){
            Glide.with(mContext).load(currentProduct.getImage()).into(ProductImage);
            FarmerName.setText(currentProduct.getUserName());
            ProductName.setText(currentProduct.getPname());
            ProductPrice.setText(currentProduct.getPrice());
            ProductDescription.setText(currentProduct.getPrice());
        }

    }
}
