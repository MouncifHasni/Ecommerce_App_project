package com.example.ecommerceapp.Admin.AdminAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Admin_home_Adapter extends RecyclerView.Adapter<Admin_home_Adapter.ViewHolder> {

    private List<adminItemModel> adminItemModelList;
    private Context mctx;

    public Admin_home_Adapter(List<adminItemModel> adminItemModelList,Context mctx) {
        this.adminItemModelList = adminItemModelList;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public Admin_home_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_home_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_home_Adapter.ViewHolder holder, int position) {
        String title = adminItemModelList.get(position).getTitle();
        String price = adminItemModelList.get(position).getPrice();
        String image = adminItemModelList.get(position).getUrl();

        holder.setOrderItem(image,title,price+" Dhs");
    }

    @Override
    public int getItemCount() {
        return adminItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,price;
        private ImageView productimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.admin_home_item_title);
            price = itemView.findViewById(R.id.admin_home_item_price);
            productimage = itemView.findViewById(R.id.admin_home_item_image);
        }
        private void setOrderItem(String myurl,String mytitle,String myprice){
            String myIP = mctx.getResources().getString(R.string.server_ip);
            title.setText(mytitle);
            price.setText(myprice);
            String url = myIP+"image/"+myurl;
            Picasso.get().load(url).into(productimage);
        }
    }
}
