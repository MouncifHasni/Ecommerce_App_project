package com.example.ecommerceapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Admin.AdminAdapters.Admin_home_Adapter;
import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class shippingdetailsItemsAdapter extends RecyclerView.Adapter<shippingdetailsItemsAdapter.ViewHolder> {

    private List<adminItemModel> adminItemModelList;
    private Context mtx;

    public shippingdetailsItemsAdapter(List<adminItemModel> adminItemModelList, Context mtx) {
        this.adminItemModelList = adminItemModelList;
        this.mtx = mtx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipping_details_single_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String myIP = mtx.getResources().getString(R.string.server_ip);
        String title = adminItemModelList.get(position).getTitle();
        String price = adminItemModelList.get(position).getPrice();
        String qte = adminItemModelList.get(position).getQuantity();
        String img = adminItemModelList.get(position).getUrl();

        String url = myIP+"image/"+img;
        holder.setItemdata(title,url,price,qte);
    }

    @Override
    public int getItemCount() {
        return adminItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productquatity,productTitle,productPrice;
        private ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.shipping_details_item_image);
            productquatity = itemView.findViewById(R.id.shipping_details_quantity);
            productTitle = itemView.findViewById(R.id.shipping_details_item_title);
            productPrice = itemView.findViewById(R.id.shipping_details_item_price);

        }

        private void setItemdata(String title,String url,String price,String qte){
            productquatity.setText("Quantity : "+qte);
            productPrice.setText(price+" Dhs");
            productTitle.setText(title);
            Picasso.get().load(url).into(productImage);
        }
    }
}
