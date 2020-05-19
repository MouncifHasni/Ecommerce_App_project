package com.example.ecommerceapp.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ecommerceapp.Activities.ShippingDetailsActivity;
import com.example.ecommerceapp.Adapters.CartItemsAdapter;
import com.example.ecommerceapp.Models.MyCartItemModel;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {


    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartrecyclerView;
    private Button continueBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        cartrecyclerView = view.findViewById(R.id.mycart_items_recyclerview);
        continueBtn = view.findViewById(R.id.continue_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartrecyclerView.setLayoutManager(linearLayoutManager);

        List<MyCartItemModel> myCartItemModelsList = new ArrayList<>();
        myCartItemModelsList.add(new MyCartItemModel(R.drawable.red_zone,"Red Zone",200 ,350,"1"));
        myCartItemModelsList.add(new MyCartItemModel(R.drawable.redmail_icon,"Red Mail",50 ,550,"2"));
        myCartItemModelsList.add(new MyCartItemModel(R.drawable.home_icon,"Yeeaaaaah Home sadoisadoan",200 ,150,"1"));

        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(myCartItemModelsList);
        cartrecyclerView.setAdapter(cartItemsAdapter);
        cartItemsAdapter.notifyDataSetChanged();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shippingActivity = new Intent(getContext(), ShippingDetailsActivity.class);
                startActivity(shippingActivity);
            }
        });

        return view;
    }

}
