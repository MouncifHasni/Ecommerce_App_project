package com.example.ecommerceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Adapters.ProductDetailsAdapter;
import com.example.ecommerceapp.Adapters.ProductImagesAdapter;
import com.example.ecommerceapp.HomeActivity;
import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.MainHomeActivity;
import com.example.ecommerceapp.Models.ClientModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailsActivity extends AppCompatActivity {

    private FloatingActionButton addTowishListBtn;
    private boolean isfavorite = false;
    private ViewPager productDetailsviewpager;
    private TabLayout productDetailsTablayout;
    //Rating
    private LinearLayout rateNowLayout;
    //views
    private ImageView productImage;
    private TextView productRating,productTitle,productTotalRatings,productPrice,productLastPrice;
    private View divider;
    private String myIP,item_id,item_price,item_rating,item_totalrating,item_url,item_qte,item_title,item_shipping_cost;
    private TextView rateLayoutTxt,rateLaoutTotalRates;
    //
    private LinearLayout addToCartBtn;
    private Button buyNowbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myIP = getResources().getString(R.string.server_ip);
        //get data inent
        item_id = getIntent().getStringExtra("item_id");
        item_title = getIntent().getStringExtra("item_title");
        item_price = getIntent().getStringExtra("item_price");
        item_url = getIntent().getStringExtra("item_img");
        item_qte = getIntent().getStringExtra("item_qte");
        final String item_brand = getIntent().getStringExtra("item_brand");
        final String item_condition = getIntent().getStringExtra("item_condition");
        final String item_delivery = getIntent().getStringExtra("item_delivery");
        final String item_return = getIntent().getStringExtra("item_return");
        item_shipping_cost = getIntent().getStringExtra("item_shipping_cost");
        final String item_desc = getIntent().getStringExtra("item_desc");
        final String item_paypal = getIntent().getStringExtra("paypal");
        final String item_mastercard = getIntent().getStringExtra("mastercard");
        final String item_visa = getIntent().getStringExtra("visa");
        final String item_lastprice = getIntent().getStringExtra("item_lastprice");
        item_totalrating = getIntent().getStringExtra("item_totalrating");
        item_rating = getIntent().getStringExtra("item_rating");

        addTowishListBtn = findViewById(R.id.add_toWishList);
        productDetailsTablayout = findViewById(R.id.product_details_tabLayout);
        productDetailsviewpager = findViewById(R.id.product_details_viewpager);
        productImage = findViewById(R.id.client_product_details_image);
        productLastPrice = findViewById(R.id.client_product_details_lastPrice);
        productTitle = findViewById(R.id.client_product_details_title);
        productRating = findViewById(R.id.client_product_details_rating);
        productPrice = findViewById(R.id.client_product_details_price);
        productTotalRatings = findViewById(R.id.client_product_details_nbOfrates);
        divider = findViewById(R.id.client_product_details_lastprice_divider);
        rateLayoutTxt = findViewById(R.id.product_details_ratelayout_rate_txt);
        rateLaoutTotalRates = findViewById(R.id.product_details_ratelayout_total_rating);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
        buyNowbtn = findViewById(R.id.buy_now_btn);

        addTowishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isfavorite){
                    isfavorite = false;
                    addTowishListBtn.setImageResource(R.drawable.fav_icon);
                    Intent shippingActivity = new Intent(ProductDetailsActivity.this, Add_addressActivity.class);
                    startActivity(shippingActivity);
                }else{
                    isfavorite = true;
                    addTowishListBtn.setImageResource(R.drawable.isfavorite_icon);
                }
            }
        });

        productDetailsviewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTablayout.getTabCount(),item_desc,
                item_condition,item_qte,item_brand,item_delivery,item_shipping_cost,item_return,item_paypal,item_mastercard,item_visa));
        productDetailsviewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsviewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //Rating
        rateNowLayout = findViewById(R.id.rat_now_container);
        for(int i=0;i<rateNowLayout.getChildCount();i++){
            final int starPosition =i;
            rateNowLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToCart(item_title,item_url,item_desc,item_price,item_lastprice,item_condition,item_qte,
                        item_brand,item_paypal,item_mastercard,item_visa,item_return,item_shipping_cost,item_delivery);
            }
        });

        buyNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBuy();
            }
        });

        //Set DATA
        setProductData(myIP+"image/"+item_url,item_title,item_price+" Dhs",item_lastprice,item_rating,item_totalrating);
    }

    private void addItemToCart(String title,String url,String desc,String price,String lastprice,String condition,String qte,
                               String brand,String paypal,String mastercard,String visa,String returnsttype,String shippingcost,
                               String deliverytime){
        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        HashMap<String,String> map = new HashMap<>();
        map.put("_id",item_id);
        map.put("url",url);
        map.put("email", MainActivity.main_email);
        map.put("title",title);
        map.put("desc",desc);
        map.put("price",price);
        map.put("rating",item_rating);
        map.put("totalrating",item_totalrating);
        map.put("lastprice",lastprice);
        map.put("condition",condition);
        map.put("quantity",qte);
        map.put("brand",brand);
        map.put("paypal",paypal);
        map.put("mastercard",mastercard);
        map.put("visa",visa);
        map.put("returntype",returnsttype);
        map.put("shippingcost",shippingcost);
        map.put("deliverytime",deliverytime);

        i.addToWishList(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(ProductDetailsActivity.this,response.body(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    private void setRating(int pos){
        for(int i=0;i<rateNowLayout.getChildCount();i++){
            ImageView starBtn = (ImageView)rateNowLayout.getChildAt(i);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(i<=pos){
                starBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

            }
        }
        String result = calculRating(item_rating,item_totalrating,pos+1);
        setDialogue(result,item_totalrating);

    }

    private void setDialogue(final String rate, final String totalrates) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rating").setMessage("Send your rate?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float r = Float.parseFloat(totalrates);
                        r = r+1;
                        String newr = String.valueOf(r);
                        item_rating = rate;
                        item_totalrating = newr;
                        Toast.makeText(ProductDetailsActivity.this,rate+" and "+item_totalrating,Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void setProductData(String img,String title,String price,String lastprice,String rating,String totalRating){
        Picasso.get().load(img).into(productImage);
        productTitle.setText(title);
        productRating.setText(rating);
        productPrice.setText(price);
        rateLayoutTxt.setText(rating);
        if(!lastprice.equals("0")){
            productLastPrice.setVisibility(View.VISIBLE);
            productLastPrice.setText(lastprice);
            divider.setVisibility(View.VISIBLE);
        }
        productTotalRatings.setText("("+totalRating+")Ratings");
        rateLaoutTotalRates.setText(totalRating+" ratings");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private String calculRating(String lastrate,String totalRatings,float myrate){
        String result = String.valueOf(myrate);
        if(!totalRatings.equals("0")){
            float a = Float.parseFloat(lastrate);
            float b = myrate;
            float c = Float.parseFloat(totalRatings);
            float d = a+(b/c);
            float r = (d*c)/(c+1);

            result = String.valueOf(r);
            return result;
        }

        return result;
    }

    private void goToBuy(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        i.getClientInfo(MainActivity.main_email).enqueue(new Callback<ClientModel>() {
            @Override
            public void onResponse(Call<ClientModel> call, Response<ClientModel> response) {
                ClientModel data = response.body();
                if(data.getAdress()!=null){
                    Intent intent = new Intent(ProductDetailsActivity.this, ShippingDetailsActivity.class);
                    intent.putExtra("client_data",data);
                    intent.putExtra("item_url",item_url);
                    intent.putExtra("item_id",item_id);
                    intent.putExtra("item_title",item_title);
                    intent.putExtra("item_price",item_price);
                    intent.putExtra("item_qte",item_qte);
                    intent.putExtra("item_shipping_cost",item_shipping_cost);
                    intent.putParcelableArrayListExtra("adresses", (ArrayList<? extends Parcelable>) data.getAdress());
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(ProductDetailsActivity.this, Add_addressActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<ClientModel> call, Throwable t) {

            }
        });
    }

}
