package com.example.ecommerceapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Models.OrderDetailsItemsModel;
import com.example.ecommerceapp.R;

import java.util.List;

public class OrderDetailsItemsAdapter extends RecyclerView.Adapter<OrderDetailsItemsAdapter.ViewHolder> {

    List<OrderDetailsItemsModel> orderDetailsItemsModelList;

    public OrderDetailsItemsAdapter(List<OrderDetailsItemsModel> orderDetailsItemsModelList) {
        this.orderDetailsItemsModelList = orderDetailsItemsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_shiiped_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = orderDetailsItemsModelList.get(position).getTitle();
        String price = orderDetailsItemsModelList.get(position).getPrice();
        int image = orderDetailsItemsModelList.get(position).getProductImage();

        holder.setOrderItem(image,title,price);

    }

    @Override
    public int getItemCount() {
        return orderDetailsItemsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,price;
        private ImageView productimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.order_shipped_title);
            price = itemView.findViewById(R.id.order_shipped_price);
            productimage = itemView.findViewById(R.id.order_shipped_image);
        }

        private void setOrderItem(int resource,String mytitle,String myprice){
            title.setText(mytitle);
            productimage.setImageResource(resource);
            price.setText(myprice);
        }
    }
}
