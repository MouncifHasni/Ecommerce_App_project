package com.example.ecommerceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Activities.ProductDetailsActivity;
import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<adminItemModel> productsList;
    private Context mctx;
    private String category;

    public ProductsAdapter(List<adminItemModel> productsList,Context mctx,String category) {
        this.productsList = productsList;
        this.mctx = mctx;
        this.category = category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_single_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String myIP = mctx.getResources().getString(R.string.server_ip);
        final String title = productsList.get(position).getTitle();
        final String price = productsList.get(position).getPrice()+" Dhs";
        String img = productsList.get(position).getUrl();
        String rating = productsList.get(position).getRating();
        String quantity = productsList.get(position).getQuantity();

        final String url = myIP+"image/"+img;
        holder.setProductData(title,price,"Quantity : "+quantity,rating,url);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productDetails = new Intent(mctx, ProductDetailsActivity.class);
                productDetails.putExtra("item_category",category);
                productDetails.putExtra("item_id",productsList.get(position).get_id());
                productDetails.putExtra("item_title",title);
                productDetails.putExtra("item_img",productsList.get(position).getUrl());
                productDetails.putExtra("item_lastprice",productsList.get(position).getLastprice());
                productDetails.putExtra("item_totalrating",productsList.get(position).getTotalrating());
                productDetails.putExtra("item_rating",productsList.get(position).getRating());
                productDetails.putExtra("item_price",productsList.get(position).getPrice());
                productDetails.putExtra("item_qte",productsList.get(position).getQuantity());
                productDetails.putExtra("item_shipping_cost",productsList.get(position).getShippingcost());
                productDetails.putExtra("item_brand",productsList.get(position).getBrand());
                productDetails.putExtra("item_condition",productsList.get(position).getCondition());
                productDetails.putExtra("item_delivery",productsList.get(position).getDeliverytime());
                productDetails.putExtra("item_return",productsList.get(position).getReturntype());
                productDetails.putExtra("item_desc",productsList.get(position).getDesc());
                productDetails.putExtra("paypal",productsList.get(position).getPayments().getPaypal());
                productDetails.putExtra("mastercard",productsList.get(position).getPayments().getMastercard());
                productDetails.putExtra("visa",productsList.get(position).getPayments().getVisa());
                mctx.startActivity(productDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productimage;
        private TextView title,price,quantity,rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productimage = itemView.findViewById(R.id.client_product_image);
            title = itemView.findViewById(R.id.client_product__title);
            quantity = itemView.findViewById(R.id.client_product_quantity);
            rating = itemView.findViewById(R.id.client_product_rating);
            price = itemView.findViewById(R.id.client_product__price);
        }

        private void setProductData(String mytitle,String myprice,String myqte,String myrating,String img){
            title.setText(mytitle);
            price.setText(myprice);
            quantity.setText(myqte);
            rating.setText(myrating);
            Picasso.get().load(img).into(productimage);
        }
    }
}
