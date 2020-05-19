package com.example.ecommerceapp.Admin.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ecommerceapp.Admin.AdminAdapters.AdminCatAdapter;
import com.example.ecommerceapp.Admin.AdminAdapters.AdminItemsAdapter;
import com.example.ecommerceapp.Admin.AdminModels.adminCatModel;
import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.MainHomeActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.example.ecommerceapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemsActivity extends AppCompatActivity {

    private List<adminItemModel> adminItemModelList;
    private GridView itemGridLAyout;
    private AdminItemsAdapter adapter;
    private LinearLayout addItemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String myIP = getResources().getString(R.string.server_ip);
        addItemBtn = findViewById(R.id.add_item_btn);
        itemGridLAyout = findViewById(R.id.admin_items_grid_layout);
        final String cat_title = getIntent().getStringExtra("cat_title");
        final String cat_url = getIntent().getStringExtra("cat_url");

        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        i.fetchItems(cat_title).enqueue(new Callback<adminCatModel>() {
            @Override
            public void onResponse(Call<adminCatModel> call, Response<adminCatModel> response) {
                adminItemModelList= new ArrayList<>();
                adminCatModel mycat = response.body();
                if(mycat.getItems()!=null){
                    adminItemModelList = mycat.getItems();
                }

                adapter = new AdminItemsAdapter(adminItemModelList,getBaseContext(),cat_title);
                itemGridLAyout.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<adminCatModel> call, Throwable t) {

            }
        });

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddItemIntent = new Intent(ItemsActivity.this, AddItemActivity.class);
                AddItemIntent.putExtra("img_url",cat_url);
                startActivity(AddItemIntent);
                finish();
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
