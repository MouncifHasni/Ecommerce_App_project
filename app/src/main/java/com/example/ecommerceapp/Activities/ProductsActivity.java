package com.example.ecommerceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ecommerceapp.Adapters.MyOrdersAdapter;
import com.example.ecommerceapp.Adapters.ProductsAdapter;
import com.example.ecommerceapp.Admin.AdminAdapters.Admin_home_Adapter;
import com.example.ecommerceapp.Admin.AdminModels.adminCatModel;
import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<adminItemModel> productsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String mycat = getIntent().getStringExtra("category_name");
        final String myIP = getResources().getString(R.string.server_ip);


        recyclerView = findViewById(R.id.clients_products_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);

        i.fetchItems(mycat).enqueue(new Callback<adminCatModel>() {
            @Override
            public void onResponse(Call<adminCatModel> call, Response<adminCatModel> response) {
                productsList = new ArrayList<>();
                adminCatModel data = response.body();
                if(data.getItems()!=null){
                    productsList = data.getItems();
                    ProductsAdapter adapter = new ProductsAdapter(productsList,getApplicationContext(),mycat);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<adminCatModel> call, Throwable t) {

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
