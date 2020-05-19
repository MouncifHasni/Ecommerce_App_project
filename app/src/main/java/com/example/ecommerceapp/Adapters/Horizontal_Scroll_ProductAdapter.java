package com.example.ecommerceapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Activities.ProductDetailsActivity;
import com.example.ecommerceapp.Models.Horizontal_Product_Model;
import com.example.ecommerceapp.R;

import java.util.List;

public class Horizontal_Scroll_ProductAdapter extends RecyclerView.Adapter<Horizontal_Scroll_ProductAdapter.ViewHolder> {

    private List<Horizontal_Product_Model> horizontal_product_modelList;

    public Horizontal_Scroll_ProductAdapter(List<Horizontal_Product_Model> horizontal_product_modelList) {
        this.horizontal_product_modelList = horizontal_product_modelList;
    }

    @NonNull
    @Override
    public Horizontal_Scroll_ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Horizontal_Scroll_ProductAdapter.ViewHolder holder, int position) {
        int resource = horizontal_product_modelList.get(position).getProductImage();
        String title = horizontal_product_modelList.get(position).getProductTitle();
        String price = horizontal_product_modelList.get(position).getProductPrice();

        holder.setProductImage(resource);
        holder.setProductPrice(price);
        holder.setProductTitle(title);
    }

    @Override
    public int getItemCount() {
        if(horizontal_product_modelList.size()>8){
            return 8;
        }else{
            return horizontal_product_modelList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView productImage;
        private TextView productTitle,productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.h_s_product_image);
            productTitle = itemView.findViewById(R.id.h_s_product_title);
            productPrice = itemView.findViewById(R.id.h_s_product_price);
            itemView.setOnClickListener(this);
        }

        private void setProductImage(int resource){
            productImage.setImageResource(resource);
        }
        private void setProductTitle(String title){
            productTitle.setText(title);
        }
        private void setProductPrice(String price){
            productPrice.setText(price);
        }

        @Override
        public void onClick(View v) {
            Intent productDetails = new Intent(itemView.getContext(), ProductDetailsActivity.class);
            itemView.getContext().startActivity(productDetails);
        }
    }
}
