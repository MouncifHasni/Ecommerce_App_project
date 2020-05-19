package com.example.ecommerceapp;


import android.content.Intent;
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
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.ecommerceapp.R.drawable.done_icon;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {

    private int parentfrag;

    public ResetPasswordFragment(int parentfrag) {
        this.parentfrag = parentfrag;
    }

    private TextInputLayout email;
    private Button resetPassBtn;
    private ProgressBar progressBar;
    private TextView emailsentTxt;
    private ImageView emailicon;
    private ViewGroup mailsendContainer;
    private FrameLayout parentFrameLayout;


    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Forgot Password");
        setHasOptionsMenu(true);

        //init views
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);
        email = view.findViewById(R.id.resetemail_txt);
        resetPassBtn = view.findViewById(R.id.resetpass_btn);
        progressBar = view.findViewById(R.id.progressBar_resetpass);
        emailicon = view.findViewById(R.id.imageemail);
        emailsentTxt = view.findViewById(R.id.mail_send_txt);
        mailsendContainer = view.findViewById(R.id.Linearlayout_forgotpass);

        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r = CheckInputs();
                if(r == 1){
                    emailsentTxt.setVisibility(View.GONE);
                    TransitionManager.beginDelayedTransition(mailsendContainer);
                    emailicon.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.sendPasswordResetEmail(email.getEditText().getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.GONE);
                                        emailicon.setImageResource(done_icon);
                                        ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0);
                                        scaleAnimation.setDuration(100);
                                        scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                        scaleAnimation.setRepeatMode(Animation.REVERSE);
                                        scaleAnimation.setRepeatCount(1);
                                        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                        emailicon.startAnimation(scaleAnimation);

                                        email.getEditText().getText().clear();
                                        emailsentTxt.setVisibility(View.VISIBLE);
                                        emailsentTxt.startAnimation(scaleAnimation);

                                    }else{
                                        progressBar.setVisibility(View.GONE);
                                        emailsentTxt.setVisibility(View.GONE);
                                        emailicon.setVisibility(View.GONE);
                                        String error = task.getException().getMessage();
                                        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                        resetPassBtn.setEnabled(true);
                                        resetPassBtn.setTextColor(getResources().getColor(R.color.white));
                                    }
                                }
                            });
                }

            }
        });
    }
    private int CheckInputs(){
        if(!TextUtils.isEmpty(email.getEditText().getText())){
            if(email.getEditText().getText().toString().matches(emailPattern)){
                email.setError(null);
                return 1;
            }else {
                email.setError("Invalid Email");
                return 0;
            }
        }else{
            email.setError("Field can't be empty");
            return 0;
        }
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_fromright);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== android.R.id.home){
            if(parentfrag == 0){
                setFragment(new SignInFragment());
            }else{
                setFragment(new AdminSignInFragment());
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
