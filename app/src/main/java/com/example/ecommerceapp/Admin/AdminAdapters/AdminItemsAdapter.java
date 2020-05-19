package com.example.ecommerceapp.Admin.AdminAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecommerceapp.Admin.AdminActivities.ModifyCatActivity;
import com.example.ecommerceapp.Admin.AdminActivities.ModifyItemActivity;
import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminItemsAdapter extends BaseAdapter {

    private List<adminItemModel> adminItemModelList;
    private Context ctx;
    private String category;

    public AdminItemsAdapter(List<adminItemModel> adminItemModelList, Context ctx,String category) {
        this.adminItemModelList = adminItemModelList;
        this.ctx = ctx;
        this.category = category;
    }

    @Override
    public int getCount() {
        return adminItemModelList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_single_item_layout,null);

            final String myIP = ctx.getResources().getString(R.string.server_ip);
            //view.setElevation(0);
            TextView productprice = view.findViewById(R.id.admin_item_price);
            ImageView modifyitem = view.findViewById(R.id.admin_item_modify);
            ImageView productImage = view.findViewById(R.id.admin_item_image);
            LinearLayout removecatBtn = view.findViewById(R.id.admin_remove_item_btn);
            final TextView productTitle = view.findViewById(R.id.admin_item_title);
            final String title = adminItemModelList.get(position).getTitle();
            final String price = adminItemModelList.get(position).getPrice()+ " Dhs";
            final String url = myIP+"image/"+adminItemModelList.get(position).getUrl();
            Picasso.get().load(url).into(productImage);
            productTitle.setText(title);
            productprice.setText(price);
            removecatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(myIP)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    IMyService i = retrofit.create(IMyService.class);
                    i.removeItem(category,adminItemModelList.get(position).get_id()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            });
            modifyitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent modifItemIntent = new Intent(view.getContext(), ModifyItemActivity.class);
                    modifItemIntent.putExtra("item_category",category);
                    modifItemIntent.putExtra("item_id",adminItemModelList.get(position).get_id());
                    modifItemIntent.putExtra("item_title",title);
                    modifItemIntent.putExtra("item_img",url);
                    modifItemIntent.putExtra("item_price",adminItemModelList.get(position).getPrice());
                    modifItemIntent.putExtra("item_qte",adminItemModelList.get(position).getQuantity());
                    modifItemIntent.putExtra("item_shipping_cost",adminItemModelList.get(position).getShippingcost());
                    modifItemIntent.putExtra("item_brand",adminItemModelList.get(position).getBrand());
                    modifItemIntent.putExtra("item_condition",adminItemModelList.get(position).getCondition());
                    modifItemIntent.putExtra("item_delivery",adminItemModelList.get(position).getDeliverytime());
                    modifItemIntent.putExtra("item_return",adminItemModelList.get(position).getReturntype());
                    modifItemIntent.putExtra("item_desc",adminItemModelList.get(position).getDesc());
                    modifItemIntent.putExtra("paypal",adminItemModelList.get(position).getPayments().getPaypal());
                    modifItemIntent.putExtra("mastercard",adminItemModelList.get(position).getPayments().getMastercard());
                    modifItemIntent.putExtra("visa",adminItemModelList.get(position).getPayments().getVisa());
                    view.getContext().startActivity(modifItemIntent);
                }
            });

        }else{
            view = convertView;
        }

        return view;
    }
}
