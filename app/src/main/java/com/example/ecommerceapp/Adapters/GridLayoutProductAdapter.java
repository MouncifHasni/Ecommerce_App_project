package com.example.ecommerceapp.Adapters;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerceapp.Activities.ProductDetailsActivity;
import com.example.ecommerceapp.Models.Horizontal_Product_Model;
import com.example.ecommerceapp.R;

import java.util.List;

public class GridLayoutProductAdapter extends BaseAdapter {

    List<Horizontal_Product_Model> horizontalProductModelList;

    public GridLayoutProductAdapter(List<Horizontal_Product_Model> horizontalProductModelList) {
        this.horizontalProductModelList = horizontalProductModelList;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final View view;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setElevation(0);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetails = new Intent(parent.getContext(), ProductDetailsActivity.class);
                    parent.getContext().startActivity(productDetails);
                }
            });
            ImageView productImage = view.findViewById(R.id.h_s_product_image);
            TextView productTitle = view.findViewById(R.id.h_s_product_title);
            TextView productPrice = view.findViewById(R.id.h_s_product_price);

            productImage.setImageResource(horizontalProductModelList.get(position).getProductImage());
            productTitle.setText(horizontalProductModelList.get(position).getProductTitle());
            productPrice.setText(horizontalProductModelList.get(position).getProductPrice());

        }else{
            view = convertView;
        }

        return view;
    }
}
