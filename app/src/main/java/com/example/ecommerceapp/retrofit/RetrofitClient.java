package com.example.ecommerceapp.retrofit;

import android.content.Context;

import com.example.ecommerceapp.R;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private static Retrofit instance;

    public static Retrofit getInstance(Context ctx){
        String myIP = ctx.getResources().getString(R.string.server_ip);
        if(instance== null)
            instance = new Retrofit.Builder()
                    .baseUrl(myIP)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return instance;

    }
}


