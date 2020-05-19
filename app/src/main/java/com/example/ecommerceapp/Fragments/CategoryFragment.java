package com.example.ecommerceapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.ecommerceapp.Adapters.CategoryAdapter;
import com.example.ecommerceapp.Adapters.GridLayoutProductAdapter;
import com.example.ecommerceapp.Admin.AdminAdapters.AdminCatAdapter;
import com.example.ecommerceapp.Admin.AdminModels.adminCatModel;
import com.example.ecommerceapp.Models.CategoryModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

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
public class CategoryFragment extends Fragment {

    public CategoryFragment() {
        // Required empty public constructor
    }

    private List<adminCatModel> CatModelList;
    private GridView categoryGridLAyout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        String myIP = getResources().getString(R.string.server_ip);
        categoryGridLAyout = view.findViewById(R.id.cat_grid_layout);

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
                CatModelList= new ArrayList<>();
                CatModelList = response.body();

                CategoryAdapter adapter = new CategoryAdapter(CatModelList,getContext());
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
