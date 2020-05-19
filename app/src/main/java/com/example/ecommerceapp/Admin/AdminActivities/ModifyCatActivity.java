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
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ModifyCatActivity extends AppCompatActivity {

    private static final int GALLERY_PICK = 1;
    /////////
    Bitmap mBitmap;
    private String myIP;
    private TextInputLayout title;
    private Button upload,save;
    private ImageView imageView;
    private boolean imageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_cat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Modify Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myIP = getResources().getString(R.string.server_ip);

        title = findViewById(R.id.modify_title);
        upload = findViewById(R.id.modify_cat_upload_btn);
        save = findViewById(R.id.modify_cat_save_btn);
        imageView = findViewById(R.id.modify_cat_img);

        String cat_title = getIntent().getStringExtra("cat_title");
        final String imgname = getIntent().getStringExtra("cat_img");
        final String url = myIP+"image/"+imgname;

        Picasso.get().load(url).into(imageView);
        title.getEditText().setText(cat_title);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(title.getEditText().getText().toString())){
                    title.setError("Field can't be empty");
                }else{
                    //upload data
                    if(imageChanged){
                        try {
                            multipartImageUpload(title.getEditText().getText().toString(),imgname);
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
                        map.put("title",title.getEditText().getText().toString());
                        map.put("url",imgname);

                        i.modifyCatOthers(map).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }


                }
            }
        });
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
                imageChanged = true;
                mBitmap = BitmapFactory.decodeFile(filePath);
                imageView.setImageBitmap(mBitmap);

            }


        }
    }
    private void multipartImageUpload(String title,String url) throws IOException {
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

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/"+url+title), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "file");

        String myIP = getResources().getString(R.string.server_ip);
        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);

        i.modifyCat(body, name).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

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
