package com.example.ecommerceapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.Adapters.MyOrdersAdapter;
import com.example.ecommerceapp.Models.MyOrdersModel;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {


    public MyOrdersFragment() {
        // Required empty public constructor
    }

    private RecyclerView myordersRecyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        myordersRecyclerview = view.findViewById(R.id.myorders_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myordersRecyclerview.setLayoutManager(linearLayoutManager);

        List<MyOrdersModel> myOrdersModelsList = new ArrayList<>();
        myOrdersModelsList.add(new MyOrdersModel(R.drawable.redmail_icon,"Delevred now","Red mail"));
        myOrdersModelsList.add(new MyOrdersModel(R.drawable.red_zone,"Cancelled","Red zone"));

        MyOrdersAdapter myOrdersAdapter = new MyOrdersAdapter(myOrdersModelsList);
        myordersRecyclerview.setAdapter(myOrdersAdapter);
        myOrdersAdapter.notifyDataSetChanged();

        return view;
    }

}
