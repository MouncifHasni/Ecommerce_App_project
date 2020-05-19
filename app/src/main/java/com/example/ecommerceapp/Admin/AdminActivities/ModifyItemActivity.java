package com.example.ecommerceapp.Admin.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.Admin.AdminModels.paymentModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModifyItemActivity extends AppCompatActivity {

    private static final int GALLERY_PICK = 1;
    Bitmap mBitmap;
    //
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextInputLayout costItem,titleItem,priceItem,descItem,brandItem,qteItem;
    private boolean costisChecked =false;
    private CheckBox paypal,mastercard,visa;
    private String conditionSelected = "NULL",estDeliverySelected = "NULL",returnsTypeSelected = "NULL";
    private Button saveItemBtn,upload_imgBtn;
    private ImageView itemImage;
    private String item_id,item_price;
    private boolean imageChanged = false;
    private String myIP,item_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Modify Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myIP = getResources().getString(R.string.server_ip);
        //get data inent
        item_id = getIntent().getStringExtra("item_id");
        item_category = getIntent().getStringExtra("item_category");
        String item_title = getIntent().getStringExtra("item_title");
        item_price = getIntent().getStringExtra("item_price");
        String item_url = getIntent().getStringExtra("item_img");
        String item_qte = getIntent().getStringExtra("item_qte");
        String item_brand = getIntent().getStringExtra("item_brand");
        String item_condition = getIntent().getStringExtra("item_condition");
        String item_delivery = getIntent().getStringExtra("item_delivery");
        String item_return = getIntent().getStringExtra("item_return");
        String item_shipping_cost = getIntent().getStringExtra("item_shipping_cost");
        String item_desc = getIntent().getStringExtra("item_desc");
        String item_paypal = getIntent().getStringExtra("paypal");
        String item_mastercard = getIntent().getStringExtra("mastercard");
        String item_visa = getIntent().getStringExtra("visa");

        //init views
        itemImage = findViewById(R.id.modify_item_img);
        titleItem = findViewById(R.id.modify_item_title);
        qteItem = findViewById(R.id.modify_item_qte);
        priceItem = findViewById(R.id.modify_item_price);
        brandItem = findViewById(R.id.modify_item_brand);
        descItem = findViewById(R.id.modify_item_desc);
        costItem = findViewById(R.id.modify_item_cost);
        radioGroup = findViewById(R.id.modify_item_radioGroup);
        visa = findViewById(R.id.modify_item_visa);
        mastercard = findViewById(R.id.modify_item_mastercard);
        paypal = findViewById(R.id.modify_item_paypal);
        saveItemBtn = findViewById(R.id.modify_item_save_btn);
        upload_imgBtn = findViewById(R.id.modify_item_upload_img_btn);

        //set data *********************
        Picasso.get().load(item_url).into(itemImage);
        titleItem.getEditText().setText(item_title);
        qteItem.getEditText().setText(item_qte);
        priceItem.getEditText().setText(item_price);
        descItem.getEditText().setText(item_desc);
        if(item_shipping_cost.equals("Free")){
            radioGroup.check(R.id.modify_item_radio_free);
        }else{
            costItem.setVisibility(View.VISIBLE);
            radioGroup.check(R.id.modify_item_radio_cost);
            costItem.getEditText().setText(item_shipping_cost);
        }
        if(item_brand!="Not specified"){
            brandItem.getEditText().setText(item_brand);
        }
        if(item_mastercard.equals("true")){
            mastercard.setChecked(true);
        }
        if(item_paypal.equals("true")){
            paypal.setChecked(true);
        }
        if(item_visa.equals("true")){
            visa.setChecked(true);
        }

        //**********************

        final Spinner myconditionSpinner = findViewById(R.id.modify_item_condition_spinner);
        Spinner returnSpinner = findViewById(R.id.modify_item_returns_spinner);
        Spinner mdeliverySpinner = findViewById(R.id.modify_item_delivery_spinner);

        ArrayAdapter<String> conditionAdapter = new ArrayAdapter<String>(ModifyItemActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.conditions));
        ArrayAdapter<String> returnadapter = new ArrayAdapter<String>(ModifyItemActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.returnType));
        ArrayAdapter<String> mdeliveryadapter = new ArrayAdapter<String>(ModifyItemActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.deliverytime));

        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        returnadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mdeliveryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myconditionSpinner.setAdapter(conditionAdapter);
        returnSpinner.setAdapter(returnadapter);
        mdeliverySpinner.setAdapter(mdeliveryadapter);

        setSpinners(myconditionSpinner,item_condition,0);
        setSpinners(mdeliverySpinner,item_delivery,1);
        setSpinners(returnSpinner,item_return,2);


        myconditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1 ){
                    conditionSelected = "New";

                }else if(position == 2){
                    conditionSelected = "Used";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        returnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1 ){
                    returnsTypeSelected = "Not accepted";

                }else if(position == 2){
                    returnsTypeSelected = "Return withing 15 days";
                }else if(position ==3){
                    returnsTypeSelected = "Return withing 30 days";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mdeliverySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1 ){
                    estDeliverySelected = "Shipped withing 7 days";
                }else if(position == 2){
                    estDeliverySelected = "Shipped withing 15 days";
                }else if(position ==3){
                    estDeliverySelected = "Shipped withing 30 days";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        descItem.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (descItem.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });

        //Check inputs
        titleItem.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(titleItem.getEditText().getText().toString())){
                    titleItem.setError(null);
                }
            }
        });
        qteItem.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(qteItem.getEditText().getText().toString())){
                    qteItem.setError(null);
                }
            }
        });
        descItem.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(descItem.getEditText().getText().toString())){
                    descItem.setError(null);
                }
            }
        });
        priceItem.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(priceItem.getEditText().getText().toString())){
                    priceItem.setError(null);
                }
            }
        });
        costItem.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(costItem.getEditText().getText().toString())){
                    costItem.setError(null);
                }
            }
        });

        //Send data
        upload_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });

        saveItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean r = CheckData();
                if(r){
                    adminItemModel send_data = getData();
                    if(imageChanged == true){
                        try {
                            multipartImageUpload(send_data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        //Init services
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(myIP)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        IMyService i = retrofit.create(IMyService.class);
                        HashMap<String,String> map = new HashMap<>();
                        map.put("id",send_data.get_id());
                        map.put("title",send_data.getTitle());
                        map.put("desc",send_data.getDesc());
                        map.put("price",send_data.getPrice());
                        map.put("cat_title",item_category);
                        map.put("lastprice",send_data.getLastprice());
                        map.put("condition",send_data.getCondition());
                        map.put("quantity",send_data.getQuantity());
                        map.put("brand",send_data.getBrand());
                        map.put("paypal",send_data.getPayments().getPaypal());
                        map.put("mastercard",send_data.getPayments().getMastercard());
                        map.put("visa",send_data.getPayments().getVisa());
                        map.put("returntype",send_data.getReturntype());
                        map.put("shippingcost",send_data.getShippingcost());
                        map.put("deliverytime",send_data.getDeliverytime());

                        i.modifyItemtOthers(map).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                        Toast.makeText(ModifyItemActivity.this, "Item Modified!", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
    }

    public void CheckRadio(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        if(radioButton.getText().equals("Cost")){
            costItem.setVisibility(View.VISIBLE);
            costisChecked = true;
        }else{
            costItem.setVisibility(View.GONE);
            costisChecked = false;
        }

    }
    private boolean CheckData(){
        if(!TextUtils.isEmpty(titleItem.getEditText().getText().toString())){
            if(!TextUtils.isEmpty(qteItem.getEditText().getText().toString())){
                if(!TextUtils.isEmpty(priceItem.getEditText().getText().toString())){
                    if(conditionSelected != "NULL"){
                        if(estDeliverySelected != "NULL"){
                            if(returnsTypeSelected != "NULL"){
                                if(!costisChecked || costisChecked && !TextUtils.isEmpty(costItem.getEditText().getText().toString())){
                                    if(paypal.isChecked() || !mastercard.isChecked() || !visa.isChecked()){
                                        if(!TextUtils.isEmpty(descItem.getEditText().getText().toString())){
                                            return true;
                                        }else{
                                            descItem.setError("Field can't be empty");
                                            return false;
                                        }
                                    }else{
                                        Toast.makeText(ModifyItemActivity.this,"Please select payment method",Toast.LENGTH_SHORT).show();
                                        return false;
                                    }
                                }else{
                                    costItem.setError("Field can't be empty");
                                    return false;
                                }
                            }else{
                                Toast.makeText(ModifyItemActivity.this,"Please select product Returns",Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }else{
                            Toast.makeText(ModifyItemActivity.this,"Please select product Est.delivery",Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }else{
                        Toast.makeText(ModifyItemActivity.this,"Please select product Condition",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else{
                    priceItem.setError("Field can't be empty");
                    return false;
                }
            }else{
                qteItem.setError("Field can't be empty");
                return false;
            }

        }else{
            titleItem.setError("Field can't be empty");
            return false;
        }
    }
    private adminItemModel getData(){
        String title = titleItem.getEditText().getText().toString();
        String qte = qteItem.getEditText().getText().toString();
        String price = priceItem.getEditText().getText().toString();
        String desc = descItem.getEditText().getText().toString();
        String brand = "Not specified";
        String shipping_cost = "Free";
        if(costisChecked){
            shipping_cost = costItem.getEditText().getText().toString();
        }
        if(!TextUtils.isEmpty(brandItem.getEditText().getText().toString())){
            brand = brandItem.getEditText().getText().toString();
        }
        String paypalState="false",visaState="false",mastercardState="false";
        if(paypal.isChecked()){
            paypalState = "true";
        }
        if(mastercard.isChecked()){
            mastercardState = "true";
        }
        if(visa.isChecked()){
            visaState = "true";
        }
        paymentModel payments = new paymentModel(paypalState,mastercardState,visaState);
        String lastprice="0",rating="0.0",totalRatings="0";
        if(!item_price.equals(price)){
            lastprice = item_price;
        }
        adminItemModel product = new adminItemModel(item_id,title,"8868b4503c72fea9467464dea7fb38de.png",desc,price,lastprice,rating,totalRatings,conditionSelected,qte,brand,payments
                ,returnsTypeSelected,shipping_cost,estDeliverySelected);

        return product;
    }
    private void setSpinners(Spinner myspinner,String value,int spinner){
        if(spinner == 0){
            conditionSelected= value;
            if(value.equals("New")){
                myspinner.setSelection(1);
            }else{
                myspinner.setSelection(2);
            }
        }else if(spinner == 1){
            estDeliverySelected = value;
            if(value.equals("Shipped withing 7 days")){
                myspinner.setSelection(1);
            }else if(value.equals("Shipped withing 15 days")){
                myspinner.setSelection(2);
            }else{
                myspinner.setSelection(3);
            }
        }else{
            returnsTypeSelected = value;
            if(value.equals("Not accepted")){
                myspinner.setSelection(1);
            }else if(value.equals("Return withing 15 days")){
                myspinner.setSelection(2);
            }else{
                myspinner.setSelection(3);
            }
        }
    }
    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            Uri imageUri = data.getData();
            String filePath = getPathFromURI(imageUri);
            if (filePath != null) {
                mBitmap = BitmapFactory.decodeFile(filePath);
                itemImage.setImageBitmap(mBitmap);
                imageChanged = true;
            }


        }
    }
    private void multipartImageUpload(adminItemModel datamodel) throws IOException {
        File filesDir = this.getFilesDir();
        File file = new File(filesDir, "image" + ".png");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        fos = new FileOutputStream(file);

        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "file");


        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("id",RequestBody.create(MediaType.parse("text/plain"),datamodel.get_id()));
        map.put("cat_title",RequestBody.create(MediaType.parse("text/plain"),item_category));
        map.put("file", name);
        map.put("title",RequestBody.create(MediaType.parse("text/plain"),datamodel.getTitle()));
        map.put("desc",RequestBody.create(MediaType.parse("text/plain"),datamodel.getDesc()));
        map.put("price",RequestBody.create(MediaType.parse("text/plain"),datamodel.getPrice()));
        map.put("lastprice",RequestBody.create(MediaType.parse("text/plain"),datamodel.getLastprice()));
        map.put("condition",RequestBody.create(MediaType.parse("text/plain"),datamodel.getCondition()));
        map.put("quantity",RequestBody.create(MediaType.parse("text/plain"),datamodel.getQuantity()));
        map.put("brand",RequestBody.create(MediaType.parse("text/plain"),datamodel.getBrand()));
        map.put("paypal",RequestBody.create(MediaType.parse("text/plain"),datamodel.getPayments().getPaypal()));
        map.put("mastercard",RequestBody.create(MediaType.parse("text/plain"),datamodel.getPayments().getMastercard()));
        map.put("visa",RequestBody.create(MediaType.parse("text/plain"),datamodel.getPayments().getVisa()));
        map.put("returntype",RequestBody.create(MediaType.parse("text/plain"),datamodel.getReturntype()));
        map.put("shippingcost",RequestBody.create(MediaType.parse("text/plain"),datamodel.getShippingcost()));
        map.put("deliverytime",RequestBody.create(MediaType.parse("text/plain"),datamodel.getDeliverytime()));


        i.modifyItem(body,map).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(ModifyItemActivity.this, "Item Modified!", Toast.LENGTH_SHORT).show();
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
