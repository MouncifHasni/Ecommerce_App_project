package com.example.ecommerceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerceapp.Activities.ProductsActivity;
import com.example.ecommerceapp.Admin.AdminModels.adminCatModel;
import com.example.ecommerceapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private List<adminCatModel> categoryModelList;
    private Context mctx;

    public CategoryAdapter(List<adminCatModel> categoryModelList,Context mctx) {
        this.categoryModelList = categoryModelList;
        this.mctx = mctx;
    }

    @Override
    public int getCount() {
        return categoryModelList.size();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final View view;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout,null);
            final String myIP = mctx.getResources().getString(R.string.server_ip);
            //view.setElevation(0);
            ImageView productImage = view.findViewById(R.id.cat_image);
            final TextView productTitle = view.findViewById(R.id.cat_title);
            final String title = categoryModelList.get(position).getTitle();
            final String url = myIP+"image/"+categoryModelList.get(position).getUrl();
            Picasso.get().load(url).into(productImage);
            productTitle.setText(title);
            productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent categoryIntent = new Intent(view.getContext(), ProductsActivity.class);
                    categoryIntent.putExtra("category_name",title);
                    view.getContext().startActivity(categoryIntent);

                }
            });
        }else{
            view = convertView;
        }

        return view;
    }


}
