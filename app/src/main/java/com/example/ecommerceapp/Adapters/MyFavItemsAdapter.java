package com.example.ecommerceapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Models.MyFavModel;
import com.example.ecommerceapp.R;

import java.util.List;

public class MyFavItemsAdapter extends RecyclerView.Adapter<MyFavItemsAdapter.ViewHolder> {

    private List<MyFavModel> myFavModelList;

    public MyFavItemsAdapter(List<MyFavModel> myFavModelList) {
        this.myFavModelList = myFavModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfav_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = myFavModelList.get(position).getTitle();
        String price = myFavModelList.get(position).getPrice();
        String lastprice = myFavModelList.get(position).getLastprice();
        String rating = myFavModelList.get(position).getRating();
        String totalrates = myFavModelList.get(position).getTotalRatings();
        int resource = myFavModelList.get(position).getProductImage();

        holder.setMyfavItem(resource,title,price,lastprice,rating,totalrates);

    }

    @Override
    public int getItemCount() {
        return myFavModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView title,price,lastprice,rating,totalratings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.myfav_productImage);
            title = itemView.findViewById(R.id.myfav_item_title);
            price = itemView.findViewById(R.id.myfav_item_price);
            lastprice = itemView.findViewById(R.id.myfav_lastprice);
            rating = itemView.findViewById(R.id.myfav_product_rating);
            totalratings = itemView.findViewById(R.id.myfav_product_nbOfrates);
        }

        private void setMyfavItem(int resource,String mytitle,String myprice,String mylastprice,String myrating,String mytotalrating){
            productImage.setImageResource(resource);
            title.setText(mytitle);
            price.setText(myprice);
            lastprice.setText(mylastprice);
            rating.setText(myrating);
            totalratings.setText(mytotalrating);

        }
    }
}
