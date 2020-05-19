package com.example.ecommerceapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Admin.AdminHomeActivity;
import com.example.ecommerceapp.retrofit.IMyService;
import com.example.ecommerceapp.retrofit.RetrofitClient;
import com.google.android.material.textfield.TextInputLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSignInFragment extends Fragment {

    public static  final String Shared_Prefs = "sharedPrefs";
    public static  final String Shared_Prefs2 = "sharedPrefs2";

    public AdminSignInFragment() {
        // Required empty public constructor
    }

    private TextView forgotPass;
    private FrameLayout parentFrameLayout;
    private TextInputLayout email,pass;
    private ProgressBar progressBar;
    private Button signInbtn;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    //Mongodb
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private IMyService iMyService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_sign_in, container, false);

        //Init services
        Retrofit retrofitClient = RetrofitClient.getInstance(getContext());
        iMyService = retrofitClient.create(IMyService.class);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Admin Sign In");
        setHasOptionsMenu(true);

        //init views
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);
        email = view.findViewById(R.id.admin_signin_email);
        pass = view.findViewById(R.id.admin_signin_pass);
        signInbtn = view.findViewById(R.id.admin_signIn_btn);
        progressBar = view.findViewById(R.id.admin_signin_progress);
        forgotPass = view.findViewById(R.id.admin_forgotpass_txt);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ResetPasswordFragment(1));
            }
        });
        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(email.getEditText().getText().toString())){
                    email.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pass.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(pass.getEditText().getText().toString())){
                    pass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_color)));
                email.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_color)));
                int r = CheckInputs();
                if(r==1){
                    CheckEmailAndPass();
                }
            }
        });

    }

    private void loginUser(final String myemail, String mypass){
        if(!TextUtils.isEmpty(myemail) && !TextUtils.isEmpty(mypass)){
            compositeDisposable.add(iMyService.loginAdmin(myemail,mypass)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String reponse) throws Exception {
                            Toast.makeText(getContext(),""+reponse,Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                            saveData(myemail);

                            Intent loginIntent = new Intent(getContext(), AdminHomeActivity.class);
                            startActivity(loginIntent);
                            getActivity().finish();
                        }
                    }));
        }

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_fromright,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private int CheckInputs(){
        if(!TextUtils.isEmpty(email.getEditText().getText().toString())){
            if(!TextUtils.isEmpty(pass.getEditText().getText().toString())){
                email.setError(null);
                pass.setError(null);
                return 1;
            }else{
                pass.setError("Field can't be empty");
                email.setError(null);
                return 0;
            }
        }else{
            email.setError("Field can't be empty");
            pass.setError(null);
            return 0;
        }
    }
    private void CheckEmailAndPass(){
        if(email.getEditText().getText().toString().matches(emailPattern)){
            if(pass.getEditText().getText().length()>=8){
                progressBar.setVisibility(View.VISIBLE);
                loginUser(email.getEditText().getText().toString(),pass.getEditText().getText().toString());

            }else{
                Toast.makeText(getActivity(),"Incorrect password!",Toast.LENGTH_SHORT).show();
            }
        }else{
            email.setError("Invalid email");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== android.R.id.home){
            Intent loginIntent = new Intent(getContext(),MainHomeActivity.class);
            startActivity(loginIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    public void saveData(String email){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Shared_Prefs,MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getContext().getSharedPreferences(Shared_Prefs2,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor.putString(MainActivity.TEXT,"admin");
        editor2.putString(MainActivity.TEXT2,email);
        editor.apply();
        editor2.apply();
        loadData();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Shared_Prefs,MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getContext().getSharedPreferences(Shared_Prefs2,MODE_PRIVATE);
        MainActivity.username = sharedPreferences.getString(MainActivity.TEXT,"NULL");
        MainActivity.main_email = sharedPreferences2.getString(MainActivity.TEXT2,"NULL");

    }

}
