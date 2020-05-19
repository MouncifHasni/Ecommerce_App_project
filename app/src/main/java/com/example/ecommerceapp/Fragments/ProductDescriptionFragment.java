package com.example.ecommerceapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerceapp.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescriptionFragment extends Fragment {

    private String desc;
    public ProductDescriptionFragment(String desc) {
        // Required empty public constructor
        this.desc = desc;
    }
    private TextView descText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        descText = view.findViewById(R.id.product_description_txt);
        descText.setText(desc);

        return view;
    }

}
