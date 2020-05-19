package com.example.ecommerceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Adapters.shippingdetailsItemsAdapter;
import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.Models.AdressModel;
import com.example.ecommerceapp.Models.ClientModel;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

public class ShippingDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView adress,paymentmethod,shippingcost,totalprice;
    private List<adminItemModel> adminItemModelList;
    private Button comfirmBtn;
    private String item_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shipping details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Get intent
        List<AdressModel> adresseList = getIntent().getParcelableArrayListExtra("adresses");
        int pos = getPositionOfPrimaryAdress(adresseList);
        ClientModel data = getIntent().getParcelableExtra("client_data");
        String item_id = getIntent().getStringExtra("item_id");
        String item_title = getIntent().getStringExtra("item_title");
        item_price = getIntent().getStringExtra("item_price");
        String item_url = getIntent().getStringExtra("item_url");
        String item_qte = getIntent().getStringExtra("item_qte");
        String item_shipping_cost = getIntent().getStringExtra("item_shipping_cost");

        adress = findViewById(R.id.shipping_details_adress);
        paymentmethod = findViewById(R.id.shipping_details_payment);
        shippingcost = findViewById(R.id.shipping_details_shippingcost);
        totalprice = findViewById(R.id.shipping_details_total_price);
        recyclerView = findViewById(R.id.shiiping_detail_recyclerview);
        comfirmBtn = findViewById(R.id.shiiping_detail_comfirmbtn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adminItemModelList = new ArrayList<>();
        adminItemModelList.add(new adminItemModel(item_id,item_title,item_url,null,item_price,null,null,
                null,null,item_qte,null,null,null,null,null));
        //Set data
        setActivityData(adresseList.get(pos),item_shipping_cost);


        shippingdetailsItemsAdapter adapter = new shippingdetailsItemsAdapter(adminItemModelList,getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        comfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShippingDetailsActivity.this, MyAdressesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        paymentmethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
    private void setActivityData(AdressModel myadress,String item_shipping_cost){
        adress.setText(myadress.getReceiverusername()+"\n"+myadress.getStreet_adress()+"\n"+myadress.getCity()+
                ", "+myadress.getProvince()+" "+myadress.getZipcode()+"\n"+myadress.getCountry()+"\n"+myadress.getPhone());

        shippingcost.setText(item_shipping_cost);
        totalprice.setText(item_price+" Dhs");
    }

    private int getPositionOfPrimaryAdress(List<AdressModel> adressList){
        int pos = 0;
        for(int i=0;i<adressList.size();i++){
            if(adressList.get(i).getAdress_type().equals("primary")){
                pos = i;
                return pos;
            }
        }
        return pos;
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
