package com.example.ecommerceapp.Adapters;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Activities.OrderDetailsActivity;
import com.example.ecommerceapp.Models.MyOrdersModel;
import com.example.ecommerceapp.R;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {

    private List<MyOrdersModel> myOrdersModelList;

    public MyOrdersAdapter(List<MyOrdersModel> myOrdersModelList) {
        this.myOrdersModelList = myOrdersModelList;
    }

    @NonNull
    @Override
    public MyOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.ViewHolder holder, int position) {
        String title = myOrdersModelList.get(position).getTitle();
        String status = myOrdersModelList.get(position).getDeliveryStatut();
        int resourse = myOrdersModelList.get(position).getProductImage();
        holder.setMyOrdersItems(resourse,title,status);

    }

    @Override
    public int getItemCount() {
        return myOrdersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage,dot;
        private TextView Statut,title;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.myorders_image);
            title = itemView.findViewById(R.id.myorders_title);
            Statut = itemView.findViewById(R.id.myorders_date_delivry);
            dot = itemView.findViewById(R.id.myorders_dot);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderdetailIntent = new Intent(itemView.getContext(),OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderdetailIntent);
                }
            });

        }

        private void setMyOrdersItems(int resource,String mytitle,String status){
            if(status.equals("Cancelled")){
                dot.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.red)));
            }else{
                dot.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.green)));
            }
            productImage.setImageResource(resource);
            title.setText(mytitle);
            Statut.setText(status);
        }
    }
}
