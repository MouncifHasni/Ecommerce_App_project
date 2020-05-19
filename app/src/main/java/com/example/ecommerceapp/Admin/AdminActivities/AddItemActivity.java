package com.example.ecommerceapp.Admin.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.Admin.AdminModels.paymentModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddItemActivity extends AppCompatActivity {

    private static final int GALLERY_PICK = 1;
    Bitmap mBitmap;
    //
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextInputLayout costItem,titleItem,priceItem,descItem,brandItem,qteItem;
    private boolean costisChecked =false;
    private CheckBox paypal,mastercard,visa;
    private String conditionSelected = "NULL",estDeliverySelected = "NULL",returnsTypeSelected = "NULL";
    private Button saveItemBtn,uploadImg;
    private String myIP;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Item");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myIP = getResources().getString(R.string.server_ip);

        final String cat_url = getIntent().getStringExtra("img_url");
        //init views
        titleItem = findViewById(R.id.add_item_title);
        qteItem = findViewById(R.id.add_item_qte);
        priceItem = findViewById(R.id.add_item_price);
        brandItem = findViewById(R.id.add_item_brand);
        descItem = findViewById(R.id.add_item_desc);
        costItem = findViewById(R.id.add_item_cost);
        radioGroup = findViewById(R.id.add_item_radioGroup);
        visa = findViewById(R.id.add_item_visa);
        mastercard = findViewById(R.id.add_item_mastercard);
        paypal = findViewById(R.id.add_item_paypal);
        saveItemBtn = findViewById(R.id.add_item_save_btn);
        uploadImg = findViewById(R.id.add_item_upload_img_btn);
        imageView = findViewById(R.id.add_item_img);

        final Spinner myconditionSpinner = findViewById(R.id.item_condition_spinner);
        Spinner returnSpinner = findViewById(R.id.add_item_returns_spinner);
        Spinner mdeliverySpinner = findViewById(R.id.add_item_delivery_spinner);

        ArrayAdapter<String> conditionAdapter = new ArrayAdapter<String>(AddItemActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.conditions));
        ArrayAdapter<String> returnadapter = new ArrayAdapter<String>(AddItemActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.returnType));
        ArrayAdapter<String> mdeliveryadapter = new ArrayAdapter<String>(AddItemActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.deliverytime));

        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        returnadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mdeliveryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        myconditionSpinner.setAdapter(conditionAdapter);
        returnSpinner.setAdapter(returnadapter);
        mdeliverySpinner.setAdapter(mdeliveryadapter);


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

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });

        //Send data
        saveItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean r = CheckData();
                if(r){
                    adminItemModel datamodel = getData();
                    try {
                        multipartImageUpload(datamodel,cat_url);
                    } catch (IOException e) {
                        e.printStackTrace();
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
                                        Toast.makeText(AddItemActivity.this,"Please select payment method",Toast.LENGTH_SHORT).show();
                                        return false;
                                    }
                                }else{
                                    costItem.setError("Field can't be empty");
                                    return false;
                                }
                            }else{
                                Toast.makeText(AddItemActivity.this,"Please select product Returns",Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }else{
                            Toast.makeText(AddItemActivity.this,"Please select product Est.delivery",Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }else{
                        Toast.makeText(AddItemActivity.this,"Please select product Condition",Toast.LENGTH_SHORT).show();
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
        String brand = "Not Specified";
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
        adminItemModel product = new adminItemModel(GenerateString(12),title,"8868b4503c72fea9467464dea7fb38de.png",desc,price,lastprice,rating,totalRatings,conditionSelected,qte,brand,payments
        ,returnsTypeSelected,shipping_cost,estDeliverySelected);

        return product;
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
                imageView.setImageBitmap(mBitmap);
                Toast.makeText(AddItemActivity.this,"Image selected",Toast.LENGTH_LONG).show();

            }


        }
    }
    private void multipartImageUpload(adminItemModel datamodel,String cat_url) throws IOException {
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

        String myIP = getResources().getString(R.string.server_ip);
        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("_id",RequestBody.create(MediaType.parse("text/plain"),datamodel.get_id()));
        map.put("file", name);
        map.put("title",RequestBody.create(MediaType.parse("text/plain"),datamodel.getTitle()));
        map.put("url",RequestBody.create(MediaType.parse("text/plain"),cat_url));
        map.put("desc",RequestBody.create(MediaType.parse("text/plain"),datamodel.getDesc()));
        map.put("price",RequestBody.create(MediaType.parse("text/plain"),datamodel.getPrice()));
        map.put("lastprice",RequestBody.create(MediaType.parse("text/plain"),datamodel.getLastprice()));
        map.put("rating",RequestBody.create(MediaType.parse("text/plain"),datamodel.getRating()));
        map.put("totalrating",RequestBody.create(MediaType.parse("text/plain"),datamodel.getTotalrating()));
        map.put("condition",RequestBody.create(MediaType.parse("text/plain"),datamodel.getCondition()));
        map.put("quantity",RequestBody.create(MediaType.parse("text/plain"),datamodel.getQuantity()));
        map.put("brand",RequestBody.create(MediaType.parse("text/plain"),datamodel.getBrand()));
        map.put("paypal",RequestBody.create(MediaType.parse("text/plain"),datamodel.getPayments().getPaypal()));
        map.put("mastercard",RequestBody.create(MediaType.parse("text/plain"),datamodel.getPayments().getMastercard()));
        map.put("visa",RequestBody.create(MediaType.parse("text/plain"),datamodel.getPayments().getVisa()));
        map.put("returntype",RequestBody.create(MediaType.parse("text/plain"),datamodel.getReturntype()));
        map.put("shippingcost",RequestBody.create(MediaType.parse("text/plain"),datamodel.getShippingcost()));
        map.put("deliverytime",RequestBody.create(MediaType.parse("text/plain"),datamodel.getDeliverytime()));


        i.addItem(body,map).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(AddItemActivity.this, "Item Added!", Toast.LENGTH_SHORT).show();
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
}
