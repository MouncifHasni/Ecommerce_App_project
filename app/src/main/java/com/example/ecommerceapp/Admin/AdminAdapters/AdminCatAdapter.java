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
import android.widget.Toast;

import com.example.ecommerceapp.Admin.AdminActivities.ItemsActivity;
import com.example.ecommerceapp.Admin.AdminActivities.ModifyCatActivity;
import com.example.ecommerceapp.Admin.AdminModels.adminCatModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminCatAdapter extends BaseAdapter {

    private List<adminCatModel> adminCatModelList;
    private Context ctx;
    private int selectedLayout;

    public AdminCatAdapter(List<adminCatModel> adminCatModelList,Context ctx,int selectedLayout) {
        this.adminCatModelList = adminCatModelList;
        this.ctx = ctx;
        this.selectedLayout = selectedLayout;
    }

    @Override
    public int getCount() {
        return adminCatModelList.size();
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
            if(selectedLayout==0){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_cat_item_layout,null);
                final String myIP = ctx.getResources().getString(R.string.server_ip);
                //view.setElevation(0);
                ImageView modifyCat = view.findViewById(R.id.admin_cat_modify);
                ImageView productImage = view.findViewById(R.id.admin_cat_image);
                LinearLayout removecatBtn = view.findViewById(R.id.admin_remove_cat_btn);
                final TextView productTitle = view.findViewById(R.id.admin_cat_title);
                final String title = adminCatModelList.get(position).getTitle();
                final String url_tosend = adminCatModelList.get(position).getUrl();
                final String url = myIP+"image/"+adminCatModelList.get(position).getUrl();
                Picasso.get().load(url).into(productImage);
                productTitle.setText(title);
                removecatBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(myIP)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        IMyService i = retrofit.create(IMyService.class);

                        i.removeCategory(adminCatModelList.get(position).getUrl()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(ctx,"Category removed!" ,Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });

                modifyCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent modifyCatIntent = new Intent(view.getContext(), ModifyCatActivity.class);
                        modifyCatIntent.putExtra("cat_title",title);
                        modifyCatIntent.putExtra("cat_img",url_tosend);
                        view.getContext().startActivity(modifyCatIntent);
                    }
                });
            }else{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout,null);
                final String myIP = ctx.getResources().getString(R.string.server_ip);
                //view.setElevation(0);
                ImageView productImage = view.findViewById(R.id.cat_image);
                final TextView productTitle = view.findViewById(R.id.cat_title);
                final String title = adminCatModelList.get(position).getTitle();
                final String url = myIP+"image/"+adminCatModelList.get(position).getUrl();
                Picasso.get().load(url).into(productImage);
                productTitle.setText(title);

                productImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent modifyCatIntent = new Intent(view.getContext(), ItemsActivity.class);
                        modifyCatIntent.putExtra("cat_title",title);
                        modifyCatIntent.putExtra("cat_url",adminCatModelList.get(position).getUrl());
                        view.getContext().startActivity(modifyCatIntent);
                    }
                });
            }


        }else{
            view = convertView;
        }

        return view;
    }

}
