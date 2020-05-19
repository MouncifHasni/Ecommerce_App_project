package com.example.ecommerceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Activities.ModifyAdressActivity;
import com.example.ecommerceapp.Activities.MyAdressesActivity;
import com.example.ecommerceapp.Activities.ShippingDetailsActivity;
import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.Models.AdressModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyadressesAdapter extends RecyclerView.Adapter<MyadressesAdapter.ViewHolder> {

    private List<AdressModel> adressModelList;
    private Context mctx;
    private int primaryPos;

    public MyadressesAdapter(List<AdressModel> adressModelList, Context mctx,int primaryPos) {
        this.adressModelList = adressModelList;
        this.mctx = mctx;
        this.primaryPos = primaryPos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myadress_single_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.setAdressData(adressModelList.get(position));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!adressModelList.get(position).getAdress_type().equals("primary")){
                    adressModelList.get(position).setAdress_type("primary");
                    String myIP = mctx.getResources().getString(R.string.server_ip);
                    //Init services
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(myIP)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    IMyService i = retrofit.create(IMyService.class);
                    HashMap<String,String> map = new HashMap<>();
                    map.put("_id",adressModelList.get(position).get_id());
                    map.put("email", MainActivity.main_email);
                    map.put("adress_type","primary");
                    map.put("lastprimaryadress_id",adressModelList.get(primaryPos).get_id());
                    map.put("lastprimaryadress_type","secondary");
                    i.modifyAdress(map).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            primaryPos = position;
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }

            }
        });
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, ModifyAdressActivity.class);
                intent.putExtra("my_adress",adressModelList.get(position));
                mctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return adressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RadioButton checkBox;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.my_adress_single_item_checkbox);
            textView = itemView.findViewById(R.id.myadress_single_item_adress);
        }
        private void setAdressData(AdressModel myadress){
            textView.setText(myadress.getReceiverusername()+"\n"+myadress.getStreet_adress()+"\n"+myadress.getCity()+
                    ", "+myadress.getProvince()+" "+myadress.getZipcode()+"\n"+myadress.getCountry()+"\n"+myadress.getPhone());
            if(myadress.getAdress_type().equals("primary")){
                checkBox.setChecked(true);
            }
        }

    }
}
