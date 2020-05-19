package com.example.ecommerceapp.Admin.AdminFragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Admin.AdminModels.adminInfoModel;
import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.grpc.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminProfileFragment extends Fragment {


    public AdminProfileFragment() {
        // Required empty public constructor
    }

    private static final int GALLERY_PICK = 1;
    /////////
    private Button modifyBtn;
    private TextView usernameProfile,emailProfile,passProfile,big_email,big_username;
    private ImageView changeImage,profileImage;
    Bitmap mBitmap;

    private adminInfoModel admin_data;
    private String myIP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_admin_profile, container, false);

        myIP = getResources().getString(R.string.server_ip);

        modifyBtn = view.findViewById(R.id.admin_profile_modify_btn);
        usernameProfile = view.findViewById(R.id.admin_profile_info_uername);
        emailProfile = view.findViewById(R.id.admin_profile_info_email);
        passProfile = view.findViewById(R.id.admin_profile_info_password);
        changeImage = view.findViewById(R.id.admin_profile_modify_img);
        profileImage = view.findViewById(R.id.admin_profile_img);
        big_email = view.findViewById(R.id.admin_profile_email);
        big_username = view.findViewById(R.id.admin_profile_username);
        checkpermission();
        setData();
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);

            }
        });

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleModifyDialog();
            }
        });

        return view;
    }

    private void handleModifyDialog(){
        View view = getLayoutInflater().inflate(R.layout.admin_profile_modify_dialoge,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view).show();

        Button savebtn = view.findViewById(R.id.admin_modify_dialoge_saveBtn);
        final TextInputLayout email = view.findViewById(R.id.admin_modify_dialoge_email);
        email.getEditText().setText(MainActivity.main_email);
        final TextInputLayout username = view.findViewById(R.id.admin_modify_dialoge_username);
        final TextInputLayout pass = view.findViewById(R.id.admin_modify_dialoge_pass);
        final TextInputLayout comfirmpass = view.findViewById(R.id.admin_modify_dialoge_comfirmpass);
        final CheckBox checkBox= view.findViewById(R.id.admin_modify_dialoge_checkbox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pass.setVisibility(View.VISIBLE);
                    comfirmpass.setVisibility(View.VISIBLE);
                }else{
                    pass.setVisibility(View.GONE);
                    comfirmpass.setVisibility(View.GONE);
                }
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(myIP)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                IMyService i = retrofit.create(IMyService.class);

                if(checkBox.isChecked()){
                    boolean r = Checkdata(username,pass,comfirmpass);
                    if(r){
                        //send data
                        HashMap<String,String> map = new HashMap<>();
                        map.put("email",MainActivity.main_email);
                        map.put("username",username.getEditText().getText().toString());
                        map.put("password",pass.getEditText().getText().toString());
                        i.modifyProfile(map).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                }else{
                    if(!TextUtils.isEmpty(username.getEditText().getText().toString())){
                        //send data
                        HashMap<String,String> map = new HashMap<>();
                        map.put("email",MainActivity.main_email);
                        map.put("username",username.getEditText().getText().toString());

                        i.modifyProfile(map).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });

                    }else{
                        username.setError("Field can't be empty");
                    }
                }
            }
        });

    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private void checkpermission(){
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat
                    .shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);


            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            Uri imageUri = data.getData();
            String filePath = getPathFromURI(imageUri);
            if (filePath != null) {
                mBitmap = BitmapFactory.decodeFile(filePath);
                profileImage.setImageBitmap(mBitmap);
                try{
                    multipartImageUpload();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    private void multipartImageUpload() throws IOException {
        File filesDir = getContext().getFilesDir();
        File file = new File(filesDir, "image" + ".png");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        fos = new FileOutputStream(file);

        fos.write(bitmapdata);
        fos.flush();
        fos.close();


        RequestBody reqFile = RequestBody.create(MediaType.parse("image/"+MainActivity.main_email), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "file");

        String myIP = getResources().getString(R.string.server_ip);
        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);

        i.modifyAdminImage(body, name).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


        /*Call<ResponseBody> req = apiService.postImage(body, name);
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        textView.setText("Uploaded Successfully!");
                        textView.setTextColor(Color.BLUE);
                    }

                    Toast.makeText(getApplicationContext(), response.code() + " ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    textView.setText("Uploaded Failed!");
                    textView.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

                }
            });*/



    }

    private boolean Checkdata(TextInputLayout username, TextInputLayout pass, TextInputLayout comfirmpass){
        if(!TextUtils.isEmpty(username.getEditText().getText().toString())){
            if(!TextUtils.isEmpty(pass.getEditText().getText().toString())){
                if(!TextUtils.isEmpty(comfirmpass.getEditText().getText().toString())){
                    if(pass.getEditText().getText().length()>=8){
                        if(pass.getEditText().getText().toString().equals(comfirmpass.getEditText().getText().toString())){
                            return true;
                        }else{
                            comfirmpass.setError("Password not matched!");
                            return false;
                        }
                    }else{
                        pass.setError("Password mength must be more than 8 characters");
                        return false;
                    }
                }else{
                    comfirmpass.setError("Field can't be empty");
                    return false;
                }
            }else{
                pass.setError("Field can't be empty");
                return false;
            }
        }else{
            username.setError("Field can't be empty");
            return false;
        }
    }
    private void setData(){
        //Init services
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        i.getAdminInfo(MainActivity.main_email).enqueue(new Callback<adminInfoModel>() {
            @Override
            public void onResponse(Call<adminInfoModel> call, Response<adminInfoModel> response) {
                admin_data = response.body();
                emailProfile.setText(admin_data.getEmail());
                usernameProfile.setText(admin_data.getUsername());
                big_email.setText(admin_data.getEmail());
                big_username.setText(admin_data.getUsername());
                String url = myIP+"image/"+admin_data.getAvatar();
                if(!admin_data.getAvatar().equals("default")){
                    Picasso.get().load(url).into(profileImage);
                }

            }

            @Override
            public void onFailure(Call<adminInfoModel> call, Throwable t) {

            }
        });
    }

}
