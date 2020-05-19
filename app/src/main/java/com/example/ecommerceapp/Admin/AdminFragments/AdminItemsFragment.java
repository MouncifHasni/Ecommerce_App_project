package com.example.ecommerceapp.Admin.AdminFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.ecommerceapp.Admin.AdminActivities.addCatActivity;
import com.example.ecommerceapp.Admin.AdminAdapters.AdminCatAdapter;
import com.example.ecommerceapp.Admin.AdminModels.adminCatModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminItemsFragment extends Fragment {


    public AdminItemsFragment() {
        // Required empty public constructor
    }
    private List<adminCatModel> adminCatModelList;
    private GridView categoryGridLAyout;
    private AdminCatAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_items, container, false);

        String myIP = getResources().getString(R.string.server_ip);
        categoryGridLAyout = view.findViewById(R.id.admin_items_cat_grid_layout);

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
                adapter = new AdminCatAdapter(adminCatModelList,getContext(),1);
                categoryGridLAyout.setAdapter(adapter);
                adapter.notifyDataSetChanged();;
            }

            @Override
            public void onFailure(Call<List<adminCatModel>> call, Throwable t) {

            }
        });


        return view;
    }

}
