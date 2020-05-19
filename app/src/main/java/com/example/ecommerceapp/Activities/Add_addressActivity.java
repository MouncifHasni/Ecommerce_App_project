package com.example.ecommerceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.Models.AdressModel;
import com.example.ecommerceapp.Models.ClientModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_addressActivity extends AppCompatActivity {

    private TextInputLayout username,phone,city,country,zipcode,street_adress,province;
    private SwitchCompat switchbtn;
    private Button saveBtn;
    private String myIP;
    private List<AdressModel> adressModel;
    private int pos,candothis=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add adress");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myIP = getResources().getString(R.string.server_ip);
        getAllAdresses();

        username = findViewById(R.id.add_adress_username);
        phone = findViewById(R.id.add_adress_phone);
        zipcode = findViewById(R.id.add_adress_zipcode);
        street_adress = findViewById(R.id.add_adress_street_adress);
        country = findViewById(R.id.add_adress_country);
        city = findViewById(R.id.add_adress_city);
        province = findViewById(R.id.add_adress_province);
        switchbtn = findViewById(R.id.add_adress_switch);
        saveBtn = findViewById(R.id.add_adress_saveBTn);

        username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(username.getEditText().getText().toString())){
                    username.setError(null);
                }
            }
        });
        phone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(phone.getEditText().getText().toString())){
                    phone.setError(null);
                }
            }
        });
        zipcode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(zipcode.getEditText().getText().toString())){
                    zipcode.setError(null);
                }
            }
        });
        province.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(province.getEditText().getText().toString())){
                    province.setError(null);
                }
            }
        });
        country.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(country.getEditText().getText().toString())){
                    country.setError(null);
                }
            }
        });
        city.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(city.getEditText().getText().toString())){
                    city.setError(null);
                }
            }
        });
        street_adress.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(street_adress.getEditText().getText().toString())){
                    street_adress.setError(null);
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckData() == false){
                    final AdressModel myadress = getData();
                    HashMap<String,String> map = new HashMap<>();
                    map.put("_id",myadress.get_id());
                    map.put("email", MainActivity.main_email);
                    map.put("receiverusername",myadress.getReceiverusername());
                    map.put("city",myadress.getCity());
                    map.put("country",myadress.getCountry());
                    map.put("phone",myadress.getPhone());
                    map.put("province",myadress.getProvince());
                    map.put("adress_type",myadress.getAdress_type());
                    map.put("zipcode",myadress.getZipcode());
                    map.put("street_adress",myadress.getStreet_adress());

                    //Init services
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(myIP)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    IMyService i = retrofit.create(IMyService.class);
                    i.addAdress(map).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(myadress.getAdress_type().equals("primary")&& candothis==1){
                                modifylastPrimaryAdress(adressModel.get(pos));
                            }
                            Toast.makeText(Add_addressActivity.this,response.body(),Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });


                }
            }
        });




    }

    private boolean CheckData(){
        boolean result= false;
        if(TextUtils.isEmpty(username.getEditText().getText().toString())){
            result = true;
            username.setError("Field can't be empty");
        }
        if(TextUtils.isEmpty(street_adress.getEditText().getText().toString())){
            result = true;
            street_adress.setError("Field can't be empty");
        }
        if(TextUtils.isEmpty(city.getEditText().getText().toString())){
            result = true;
            city.setError("Field can't be empty");
        }
        if(TextUtils.isEmpty(country.getEditText().getText().toString())){
            result = true;
            country.setError("Field can't be empty");
        }
        if(TextUtils.isEmpty(phone.getEditText().getText().toString())){
            result = true;
            phone.setError("Field can't be empty");
        }
        if(TextUtils.isEmpty(zipcode.getEditText().getText().toString())){
            result = true;
            zipcode.setError("Field can't be empty");
        }
        if(TextUtils.isEmpty(province.getEditText().getText().toString())){
            result = true;
            province.setError("Field can't be empty");
        }
        return result;
    }

    private AdressModel getData(){
        String adress_type = "secondary";
        if(switchbtn.isChecked()){
            adress_type = "primary";
        }
        AdressModel data = new AdressModel(GenerateString(12),username.getEditText().getText().toString(),street_adress.getEditText().getText().toString(),
                country.getEditText().getText().toString(),city.getEditText().getText().toString(),province.getEditText().getText().toString(),
                phone.getEditText().getText().toString(),zipcode.getEditText().getText().toString(),adress_type);

        return data;
    }
    private String GenerateString(int lenght){
        char [] chars ="AZERTYUIOPQSDFGHJKLMWXCVBN1234567890".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for(int i =0;i<lenght;i++){
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);

        return stringBuilder.toString();

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

    private void getAllAdresses(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        i.getClientInfo(MainActivity.main_email).enqueue(new Callback<ClientModel>() {
            @Override
            public void onResponse(Call<ClientModel> call, Response<ClientModel> response) {
                ClientModel client_data = response.body();
                if(client_data.getAdress()!=null){
                    candothis = 1;
                    adressModel = client_data.getAdress();
                    pos = getPositionOfPrimaryAdress(adressModel);
                }

            }

            @Override
            public void onFailure(Call<ClientModel> call, Throwable t) {

            }
        });
    }

    private void modifylastPrimaryAdress(AdressModel myadress){
        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        HashMap<String,String> map = new HashMap<>();
        map.put("_id",myadress.get_id());
        map.put("email", MainActivity.main_email);
        map.put("adress_type","secondary");

        i.modifyAdress(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

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
