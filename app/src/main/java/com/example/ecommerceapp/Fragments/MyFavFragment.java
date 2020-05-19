package com.example.ecommerceapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.Adapters.MyFavItemsAdapter;
import com.example.ecommerceapp.Models.MyFavModel;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFavFragment extends Fragment {


    public MyFavFragment() {
        // Required empty public constructor
    }

    private RecyclerView myfavRecyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_fav, container, false);

        myfavRecyclerview = view.findViewById(R.id.myfav_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myfavRecyclerview.setLayoutManager(linearLayoutManager);

        List<MyFavModel> myFavModelsList = new ArrayList<>();

        myFavModelsList.add(new MyFavModel(R.drawable.red_zone,"red zone","150 Dhs","300 Dhs","3.7","(200)ratings"));
        myFavModelsList.add(new MyFavModel(R.drawable.red_zone,"red zone","150 Dhs","300 Dhs","3.7","(200)ratings"));


        MyFavItemsAdapter adapter = new MyFavItemsAdapter(myFavModelsList);
        myfavRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

}
