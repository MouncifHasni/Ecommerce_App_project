package com.example.ecommerceapp.Admin.AdminAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Adapters.OrderDetailsItemsAdapter;
import com.example.ecommerceapp.Admin.AdminModels.shippedItemModel;
import com.example.ecommerceapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminOrderDetailsItemAdapter extends RecyclerView.Adapter<AdminOrderDetailsItemAdapter.ViewHolder> {

    private List<shippedItemModel> shippedItemModelList;
    private Context mctx;

    public AdminOrderDetailsItemAdapter(List<shippedItemModel> shippedItemModelList,Context mctx) {
        this.shippedItemModelList = shippedItemModelList;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public AdminOrderDetailsItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_shiiped_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrderDetailsItemAdapter.ViewHolder holder, int position) {
        String title = shippedItemModelList.get(position).getTitle();
        String price = shippedItemModelList.get(position).getPrice();
        String image = shippedItemModelList.get(position).getUrl();

        holder.setOrderItem(image,title,price+" Dhs");
    }

    @Override
    public int getItemCount() {
        return shippedItemModelList.size();
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

        private void setOrderItem(String myurl,String mytitle,String myprice){
            String myIP = mctx.getResources().getString(R.string.server_ip);
            title.setText(mytitle);
            price.setText(myprice);
            String url = myIP+"image/"+myurl;
            Picasso.get().load(url).into(productimage);
        }
    }
}
