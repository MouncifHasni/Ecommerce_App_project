package com.example.ecommerceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ecommerceapp.Adapters.MyadressesAdapter;
import com.example.ecommerceapp.Fragments.MyCartFragment;
import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.Models.AdressModel;
import com.example.ecommerceapp.Models.ClientModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyAdressesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<AdressModel> adressModelList;
    private String myIP;
    private MyadressesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adresses);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My adresses");

        recyclerView = findViewById(R.id.myadresses_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize
        myIP = getResources().getString(R.string.server_ip);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        i.getClientInfo(MainActivity.main_email).enqueue(new Callback<ClientModel>() {
            @Override
            public void onResponse(Call<ClientModel> call, Response<ClientModel> response) {
                ClientModel client_data = response.body();
                adressModelList = client_data.getAdress();
                int pos = getPositionOfPrimaryAdress(adressModelList);
                adapter = new MyadressesAdapter(adressModelList,getApplicationContext(),pos);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ClientModel> call, Throwable t) {

            }
        });





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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.myadresses_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else if(item.getItemId()== R.id.ajout_icon){
            Intent intent = new Intent(MyAdressesActivity.this, Add_addressActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
