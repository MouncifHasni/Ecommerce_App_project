package com.example.ecommerceapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerceapp.Adapters.Payment_ImagesAdapter;
import com.example.ecommerceapp.Models.PaymentImagesModel;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingAndPaymentFragment extends Fragment {

    private String est_delivery,shipping_cost,returns_type;
    private String paypal,mastercard,visa;

    public ShippingAndPaymentFragment(String est_delivery,String shipping_cost,String returns_type,String paypal,String mastercard,String visa) {
        // Required empty public constructor
        this.est_delivery = est_delivery;
        this.shipping_cost = shipping_cost;
        this.returns_type = returns_type;
        this.paypal = paypal;
        this.mastercard = mastercard;
        this.visa = visa;
    }

    private RecyclerView shippingRecyclerview;
    private TextView delivery,cost,returnsTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipping_and_payment, container, false);

        shippingRecyclerview = view.findViewById(R.id.payment_images_recyclerview);
        delivery = view.findViewById(R.id.shipping_est_delivery_time);
        cost = view.findViewById(R.id.shipping_shipping_cost);
        returnsTxt = view.findViewById(R.id.shipping_returns_txt);

        delivery.setText(est_delivery);
        cost.setText(shipping_cost);
        returnsTxt.setText(returns_type);

        List<PaymentImagesModel> paymentImagesModelList = new ArrayList<>();
        if(paypal.equals("true")){
            paymentImagesModelList.add(new PaymentImagesModel(R.drawable.paypal1));
        }
        if(mastercard.equals("true")){
            paymentImagesModelList.add(new PaymentImagesModel(R.drawable.mastercard));
        }
        if(visa.equals("true")){
            paymentImagesModelList.add(new PaymentImagesModel(R.drawable.visa));
        }


        Payment_ImagesAdapter payment_imagesAdapter = new Payment_ImagesAdapter(paymentImagesModelList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        shippingRecyclerview.setLayoutManager(linearLayoutManager);

        shippingRecyclerview.setAdapter(payment_imagesAdapter);
        payment_imagesAdapter.notifyDataSetChanged();

        return view;
    }

}
