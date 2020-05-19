package com.example.ecommerceapp.Admin.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ecommerceapp.Admin.AdminAdapters.AdminOrderDetailsItemAdapter;
import com.example.ecommerceapp.Admin.AdminModels.adminOrderModel;
import com.example.ecommerceapp.Admin.AdminModels.paymentModel;
import com.example.ecommerceapp.Admin.AdminModels.shippedItemModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminOrderDetailsActivity extends AppCompatActivity {

    private TextView items,price,username,adress,phone,payments;
    private RecyclerView itemsRecyclerview;
    private CheckBox ordered_check,packed_check,shipped_check,delivered_check;
    private Button save_state;
    private String deliveryState = "Ordered";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final adminOrderModel data = getIntent().getParcelableExtra("order_item");
        paymentModel data_payment = getIntent().getParcelableExtra("payments");
        List<shippedItemModel> shippedItemModelList = getIntent().getParcelableArrayListExtra("shipped_items");
        //Init views
        items = findViewById(R.id.admin_order_detail_items);
        price = findViewById(R.id.admin_order_detail_price);
        username = findViewById(R.id.admin_order_detail_username);
        adress = findViewById(R.id.admin_order_detail_adress);
        phone = findViewById(R.id.admin_order_detail_phone);
        payments = findViewById(R.id.admin_order_detail_payments);
        itemsRecyclerview = findViewById(R.id.admin_order_detail_recyclerview);
        save_state = findViewById(R.id.admin_order_detail_save_state_btn);
        ordered_check = findViewById(R.id.admin_order_detail_ordered_checkbox);
        shipped_check = findViewById(R.id.admin_order_detail_shipped_checkbox);
        packed_check = findViewById(R.id.admin_order_detail_packed_checkbox);
        delivered_check = findViewById(R.id.admin_order_detail_delivered_checkbox);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemsRecyclerview.setLayoutManager(linearLayoutManager);

        //username.setText(shippedItemModelList.get(0).getTitle());
        //Set data
        setData(data.getTotalitems()+" items",data.getPrice()+" Dhs",data.getUsername(),data.getAdress(),data.getPhone(),data_payment,data.getDeliverystatus());

        AdminOrderDetailsItemAdapter adapter = new AdminOrderDetailsItemAdapter(shippedItemModelList,getApplicationContext());
        itemsRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        save_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndsendData(data.getId());
            }
        });

    }
    private void setData(String myitems, String myprice, String myusername, String myadress, String myphone, paymentModel mypayments,String deliveryStatus){
        items.setText(myitems);
        price.setText(myprice);
        username.setText(myusername);
        adress.setText(myadress);
        String allPayments = "null";
        if(mypayments.getMastercard().equals("true")){
            allPayments = "Master Card";
        }
        if(mypayments.getPaypal().equals("true") && !allPayments.equals("null")){
            allPayments = allPayments+", Paypal";
        }else if(mypayments.getPaypal().equals("true") && allPayments.equals("null")){
            allPayments = "Paypal";
        }
        if(mypayments.getVisa().equals("true") && !allPayments.equals("null")){
            allPayments = allPayments+", Visa";
        }else if(mypayments.getVisa().equals("true") && allPayments.equals("null")){
            allPayments = "Visa";
        }
        if(deliveryStatus.equals("Ordered")){
            ordered_check.setChecked(true);
        }else if(deliveryStatus.equals("Packed")){
            packed_check.setChecked(true);
            ordered_check.setChecked(true);
        }else if(deliveryStatus.equals("Shipped")){
            packed_check.setChecked(true);
            ordered_check.setChecked(true);
            shipped_check.setChecked(true);
        }else if(deliveryStatus.equals("Delivered")){
            packed_check.setChecked(true);
            ordered_check.setChecked(true);
            shipped_check.setChecked(true);
            delivered_check.setChecked(true);
        }
        payments.setText(allPayments);
        phone.setText(myphone);
    }
    private void getAndsendData(String item_id){
        if(packed_check.isChecked()){
            deliveryState = "Packed";
        }
        if(shipped_check.isChecked()){
            deliveryState = "Shipped";
        }
        if(delivered_check.isChecked()){
            deliveryState = "Delivered";
        }
        String myIP = getResources().getString(R.string.server_ip);

        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        HashMap<String,String> map = new HashMap<>();
        map.put("id",item_id);
        map.put("status",deliveryState);
        i.modifyOrder(map).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
