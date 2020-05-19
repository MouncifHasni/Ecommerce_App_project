package com.example.ecommerceapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Models.MyCartItemModel;
import com.example.ecommerceapp.R;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private List<MyCartItemModel> myCartItemModelsList;

    public CartItemsAdapter(List<MyCartItemModel> myCartItemModelsList) {
        this.myCartItemModelsList = myCartItemModelsList;
    }

    @NonNull
    @Override
    public CartItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsAdapter.ViewHolder holder, int position) {
        String title = myCartItemModelsList.get(position).getTitle();
        int price = myCartItemModelsList.get(position).getPrice();
        int lastprice = myCartItemModelsList.get(position).getLastPrice();
        String qte = myCartItemModelsList.get(position).getProductQuantity();
        int resource = myCartItemModelsList.get(position).getProductImage();

        holder.setCartItem(title,price,lastprice,qte,resource);
    }

    @Override
    public int getItemCount() {
        return myCartItemModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,price,lastprice,quantity;
        private ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.mycart_item_title);
            price = itemView.findViewById(R.id.mycart_item_price);
            lastprice = itemView.findViewById(R.id.mycart_lastprice);
            productImage = itemView.findViewById(R.id.mycart_productImage);
            quantity = itemView.findViewById(R.id.mycart_qte);

        }

        private void setCartItem(String mytitle,int myprice,int mylastprice,String qte,int resource){
            title.setText(mytitle);
            price.setText(myprice+" Dhs");
            lastprice.setText(mylastprice +" Dhs");
            quantity.setText(qte);
            productImage.setImageResource(resource);
        }
    }
}
