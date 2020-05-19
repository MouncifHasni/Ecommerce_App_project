package com.example.ecommerceapp.Admin.AdminFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommerceapp.Admin.AdminAdapters.AdminCatAdapter;
import com.example.ecommerceapp.Admin.AdminAdapters.AdminItemsAdapter;
import com.example.ecommerceapp.Admin.AdminAdapters.Admin_home_Adapter;
import com.example.ecommerceapp.Admin.AdminModels.adminCatModel;
import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminHomeFragment extends Fragment {


    public AdminHomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private List<adminItemModel> adminItemModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_home, container, false);

        final String myIP = getResources().getString(R.string.server_ip);

        recyclerView = view.findViewById(R.id.admin_home_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);

        i.fetchItems("cat1").enqueue(new Callback<adminCatModel>() {
            @Override
            public void onResponse(Call<adminCatModel> call, Response<adminCatModel> response) {
                adminItemModelList= new ArrayList<>();
                adminCatModel data = response.body();
                if(data.getItems()!=null){
                    adminItemModelList = data.getItems();
                    Admin_home_Adapter adapter = new Admin_home_Adapter(adminItemModelList,getContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<adminCatModel> call, Throwable t) {

            }
        });





        return view;
    }

}
