package com.example.ecommerceapp.Admin.AdminFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommerceapp.Adapters.MyOrdersAdapter;
import com.example.ecommerceapp.Admin.AdminAdapters.AdminCatAdapter;
import com.example.ecommerceapp.Admin.AdminAdapters.AdminOrderAdapter;
import com.example.ecommerceapp.Admin.AdminModels.adminCatModel;
import com.example.ecommerceapp.Admin.AdminModels.adminOrderModel;
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
public class AdminOrdersFragment extends Fragment {


    public AdminOrdersFragment() {
        // Required empty public constructor
    }
    private RecyclerView ordersRecyclerview;
    private List<adminOrderModel> adminOrderModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_orders, container, false);

        ordersRecyclerview = view.findViewById(R.id.admin_orders_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ordersRecyclerview.setLayoutManager(linearLayoutManager);

        String myIP = getResources().getString(R.string.server_ip);

        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        i.fetchOrders().enqueue(new Callback<List<adminOrderModel>>() {
            @Override
            public void onResponse(Call<List<adminOrderModel>> call, Response<List<adminOrderModel>> response) {
                adminOrderModelList = new ArrayList<>();
                adminOrderModelList = response.body();

                AdminOrderAdapter myOrdersAdapter = new AdminOrderAdapter(adminOrderModelList,getContext());
                ordersRecyclerview.setAdapter(myOrdersAdapter);
                myOrdersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<adminOrderModel>> call, Throwable t) {

            }
        });


        return view;
    }

}
