package com.example.ecommerceapp.Admin.AdminAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.Image;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Activities.OrderDetailsActivity;
import com.example.ecommerceapp.Adapters.MyOrdersAdapter;
import com.example.ecommerceapp.Admin.AdminActivities.AdminOrderDetailsActivity;
import com.example.ecommerceapp.Admin.AdminModels.adminOrderModel;
import com.example.ecommerceapp.Admin.AdminModels.paymentModel;
import com.example.ecommerceapp.Admin.AdminModels.shippedItemModel;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.ViewHolder> {

    private List<adminOrderModel> adminOrderModelList;
    private Context mctx;

    public AdminOrderAdapter(List<adminOrderModel> adminOrderModelList,Context mctx) {
        this.adminOrderModelList = adminOrderModelList;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public AdminOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_order_single_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrderAdapter.ViewHolder holder, final int position) {
        String id = "id : "+adminOrderModelList.get(position).getId();
        String username = adminOrderModelList.get(position).getUsername();
        String price = "Total : "+adminOrderModelList.get(position).getPrice()+" Dhs";
        String totalitems = "("+adminOrderModelList.get(position).getTotalitems()+" items)";
        String deliverystatut = adminOrderModelList.get(position).getDeliverystatus();
        final paymentModel payment = adminOrderModelList.get(position).getPayments();
        final List<shippedItemModel> sippedItems = adminOrderModelList.get(position).getShippeditems();

        holder.setOrderItem(id,username,price,deliverystatut,totalitems);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderdetailIntent = new Intent(mctx, AdminOrderDetailsActivity.class);
                orderdetailIntent.putExtra("order_item",adminOrderModelList.get(position));
                orderdetailIntent.putExtra("payments",payment);
                //orderdetailIntent.putExtra("shipped_items",sippedItems);
                orderdetailIntent.putParcelableArrayListExtra("shipped_items", (ArrayList<? extends Parcelable>) sippedItems);
                mctx.startActivity(orderdetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminOrderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id,username,price,items,deliverystatus;
        private ImageView deliverydot;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.admin_order_id);
            username = itemView.findViewById(R.id.admin_order_username);
            price = itemView.findViewById(R.id.admin_order_price);
            deliverystatus = itemView.findViewById(R.id.admin_order_delivery_statut);
            items = itemView.findViewById(R.id.admin_order_items);
            deliverydot = itemView.findViewById(R.id.admin_order_dot);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderdetailIntent = new Intent(itemView.getContext(), AdminOrderDetailsActivity.class);
                    orderdetailIntent.putExtra("order_item",)
                    itemView.getContext().startActivity(orderdetailIntent);
                }
            });*/

        }

        private void setOrderItem(String myid,String myusername,String myprice,String mydelivery,String myitemsnb){
            id.setText(myid);
            username.setText(myusername);
            price.setText(myprice);
            deliverystatus.setText(mydelivery);
            items.setText(myitemsnb);
            if(!mydelivery.equals("Delivered")){
                deliverydot.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.red_color)));
            }else{
                deliverydot.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.green)));
            }

        }
    }
}
