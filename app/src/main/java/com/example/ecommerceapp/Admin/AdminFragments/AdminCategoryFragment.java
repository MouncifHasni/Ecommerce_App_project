package com.example.ecommerceapp.Admin.AdminFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Adapters.CategoryAdapter;
import com.example.ecommerceapp.Admin.AdminActivities.addCatActivity;
import com.example.ecommerceapp.Admin.AdminAdapters.AdminCatAdapter;
import com.example.ecommerceapp.Admin.AdminModels.adminCatModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.example.ecommerceapp.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.bson.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminCategoryFragment extends Fragment {


    public AdminCategoryFragment() {
        // Required empty public constructor
    }
    private LinearLayout add_catBtn;
    private List<adminCatModel> adminCatModelList;
    private GridView categoryGridLAyout;
    private AdminCatAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_category, container, false);

        add_catBtn = view.findViewById(R.id.add_category_btn);
        categoryGridLAyout = view.findViewById(R.id.admin_cat_grid_layout);

        String myIP = getResources().getString(R.string.server_ip);

        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        i.fetchCategories().enqueue(new Callback<List<adminCatModel>>() {
            @Override
            public void onResponse(Call<List<adminCatModel>> call, Response<List<adminCatModel>> response) {
                //adminCatModelList = response.body();
                adminCatModelList= new ArrayList<>();
                adminCatModelList = response.body();

                adapter = new AdminCatAdapter(adminCatModelList,getContext(),0);
                categoryGridLAyout.setAdapter(adapter);
                adapter.notifyDataSetChanged();;
            }

            @Override
            public void onFailure(Call<List<adminCatModel>> call, Throwable t) {

            }
        });


        add_catBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCatintent = new Intent(getContext(), addCatActivity.class);
                startActivity(addCatintent);
            }
        });


        return view;
    }


}
