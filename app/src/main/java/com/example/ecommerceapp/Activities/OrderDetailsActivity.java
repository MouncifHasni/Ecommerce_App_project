package com.example.ecommerceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ecommerceapp.Adapters.OrderDetailsItemsAdapter;
import com.example.ecommerceapp.Models.OrderDetailsItemsModel;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private RecyclerView orderShippedItemsRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderShippedItemsRecyclerview = findViewById(R.id.order_shipped_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderShippedItemsRecyclerview.setLayoutManager(linearLayoutManager);

        List<OrderDetailsItemsModel> orderDetailsItemsModelList = new ArrayList<>();

        orderDetailsItemsModelList.add(new OrderDetailsItemsModel(R.drawable.red_zone,"Red Zone","300 Dhs"));
        orderDetailsItemsModelList.add(new OrderDetailsItemsModel(R.drawable.redmail_icon,"Red mail","200 Dhs"));
        orderDetailsItemsModelList.add(new OrderDetailsItemsModel(R.drawable.home_icon,"home","20 Dhs"));

        OrderDetailsItemsAdapter orderDetailsItemsAdapter = new OrderDetailsItemsAdapter(orderDetailsItemsModelList);
        orderShippedItemsRecyclerview.setAdapter(orderDetailsItemsAdapter);
        orderDetailsItemsAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
