package com.example.ecommerceapp;


import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.retrofit.IMyService;
import com.example.ecommerceapp.retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    private FrameLayout parentFrameLayout;
    private TextInputLayout email,username,pass,comfirmpass;
    private Button signUpbtn;
    private ProgressBar progressBar;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    //Mongodb
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private IMyService iMyService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_sign_up, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign Up");
        setHasOptionsMenu(true);

        //Init services
        Retrofit retrofitClient = RetrofitClient.getInstance(getContext());
        iMyService = retrofitClient.create(IMyService.class);

        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);
        email = view.findViewById(R.id.signup_email_txt);
        username = view.findViewById(R.id.signup_username_txt);
        pass = view.findViewById(R.id.signup_pass_txt);
        comfirmpass = view.findViewById(R.id.signup_comfirmpass_txt);
        signUpbtn = view.findViewById(R.id.signUp_btn);
        progressBar = view.findViewById(R.id.signup_progress);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(email.getEditText().getText().toString())){
                    email.setError(null);
                }
            }
        });
        pass.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(pass.getEditText().getText().toString())){
                    pass.setError(null);
                }
            }
        });
        comfirmpass.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(comfirmpass.getEditText().getText().toString())){
                    comfirmpass.setError(null);
                }
            }
        });

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send data to server
                username.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_color)));
                pass.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_color)));
                comfirmpass.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_color)));
                email.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_color)));
                int r = CheckInputs();
                if(r==1){
                    CheckEmailAndPass();
                }
            }
        });
    }

    private void RegisterUser(String myemail,String mypass,String myname){
        if(!TextUtils.isEmpty(myemail) && !TextUtils.isEmpty(mypass) && !TextUtils.isEmpty(myname)){
            compositeDisposable.add(iMyService.registerUser(myname,myemail,mypass)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String reponse) throws Exception {
                            Toast.makeText(getContext(),""+reponse,Toast.LENGTH_SHORT).show();
                            clearFields();
                            progressBar.setVisibility(View.GONE);
                        }
                    }));
        }

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_fromright);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private int CheckInputs(){
        if(!TextUtils.isEmpty(email.getEditText().getText().toString())){
            if(!TextUtils.isEmpty(username.getEditText().getText().toString())){
                if(!TextUtils.isEmpty(pass.getEditText().getText().toString())){
                    if(!TextUtils.isEmpty(comfirmpass.getEditText().getText().toString())){
                        email.setError(null);
                        username.setError(null);
                        pass.setError(null);
                        comfirmpass.setError(null);
                        return 1;
                    }else{
                        comfirmpass.setError("Field can't be empty");
                        email.setError(null);
                        username.setError(null);
                        pass.setError(null);
                        return 0;
                    }
                }else{
                    pass.setError("Field can't be empty");
                    email.setError(null);
                    username.setError(null);
                    comfirmpass.setError(null);
                    return 0;
                }
            }else{
                username.setError("Field can't be empty");
                email.setError(null);
                comfirmpass.setError(null);
                pass.setError(null);
                return 0;
            }
        }else{email.setError("Field can't be empty");
            comfirmpass.setError(null);
            username.setError(null);
            pass.setError(null);
            return 0;
        }
    }
    private void CheckEmailAndPass(){
        if(email.getEditText().getText().toString().matches(emailPattern)){
            if(username.getEditText().getText().toString().length()<=15){
                if(pass.getEditText().getText().length()>=8){
                    if(pass.getEditText().getText().toString().equals(comfirmpass.getEditText().getText().toString())){
                        progressBar.setVisibility(View.VISIBLE);
                        RegisterUser(email.getEditText().getText().toString(),pass.getEditText().getText().toString(),
                                username.getEditText().getText().toString());

                    }else{
                        comfirmpass.setError("Password not matched!");
                        email.setError(null);
                        username.setError(null);
                        pass.setError(null);
                    }
                }else{
                    pass.setError("Password length must be more than 8 characters");
                    email.setError(null);
                    username.setError(null);
                    comfirmpass.setError(null);
                }
            }else{
                username.setError("Username lenth must be less that 15 characters");
                email.setError(null);
                comfirmpass.setError(null);
                pass.setError(null);
            }
        }else{
            email.setError("Invalid Email!");
            comfirmpass.setError(null);
            username.setError(null);
            pass.setError(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== android.R.id.home){
            Intent loginIntent = new Intent(getContext(),MainHomeActivity.class);
            startActivity(loginIntent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void clearFields(){
        username.getEditText().setText("");
        pass.getEditText().setText("");
        comfirmpass.getEditText().setText("");
        email.getEditText().setText("");
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
