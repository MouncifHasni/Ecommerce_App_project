package com.example.ecommerceapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ecommerceapp.Adapters.ProductSpecificcationAdapter;
import com.example.ecommerceapp.Models.ProductSpecificationModel;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {

    private String condition,quantity,brand;

    public ProductSpecificationFragment(String condition,String quantity,String brand) {
        // Required empty public constructor
        this.condition = condition;
        this.quantity = quantity;
        this.brand = brand;
    }

    private RecyclerView productSpecRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_product_specification, container, false);


        productSpecRecyclerView = view.findViewById(R.id.product_specification_recyclerview);

        LinearLayoutManager linearLayoutmanager = new LinearLayoutManager(view.getContext());
        linearLayoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        productSpecRecyclerView.setLayoutManager(linearLayoutmanager);

        List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
        productSpecificationModelList.add(new ProductSpecificationModel("Condition",condition));
        productSpecificationModelList.add(new ProductSpecificationModel("Quantity",quantity));
        productSpecificationModelList.add(new ProductSpecificationModel("Brand",brand));

        ProductSpecificcationAdapter productSpecificcationAdapter = new ProductSpecificcationAdapter(productSpecificationModelList);
        productSpecRecyclerView.setAdapter(productSpecificcationAdapter);
        productSpecificcationAdapter.notifyDataSetChanged();
        return view;
    }

}
