package com.example.ecommerceapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Admin.AdminAdapters.Admin_home_Adapter;
import com.example.ecommerceapp.Admin.AdminFragments.AdminCategoryFragment;
import com.example.ecommerceapp.Admin.AdminFragments.AdminHomeFragment;
import com.example.ecommerceapp.Admin.AdminFragments.AdminItemsFragment;
import com.example.ecommerceapp.Admin.AdminFragments.AdminOrdersFragment;
import com.example.ecommerceapp.Admin.AdminFragments.AdminProfileFragment;
import com.example.ecommerceapp.Admin.AdminModels.adminInfoModel;
import com.example.ecommerceapp.Admin.AdminModels.adminItemModel;
import com.example.ecommerceapp.Fragments.CategoryFragment;
import com.example.ecommerceapp.Fragments.HomeFragment;
import com.example.ecommerceapp.Fragments.MyCartFragment;
import com.example.ecommerceapp.Fragments.MyFavFragment;
import com.example.ecommerceapp.Fragments.MyOrdersFragment;
import com.example.ecommerceapp.Fragments.MyProfileFragment;
import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.MainHomeActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.retrofit.IMyService;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static  final String Shared_Prefs = "sharedPrefs";
    public static  final String Shared_Prefs2 = "sharedPrefs2";


    private DrawerLayout drawer;
    private NavigationView navigationView;
    private static int currentFragment = -1;
    private ImageView actionbarLogo;
    private CircleImageView nav_img;
    private TextView nav_username,nav_email;
    private adminInfoModel admin_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionbarLogo = findViewById(R.id.admin_actionbar_logo);
        drawer = findViewById(R.id.admin_drawer_layout);
        navigationView = findViewById(R.id.admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);;
        View header = navigationView.getHeaderView(0);
        nav_img = header.findViewById(R.id.admin_nav_profile_image);
        nav_username = header.findViewById(R.id.admin_nav_profile_username);
        nav_email = header.findViewById(R.id.admin_nav_profile_email);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        setNavData();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_Fragment_container,
                    new AdminHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.admin_nav_home);
            currentFragment = 0;
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getItemId()== R.id.admin_nav_home){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            actionbarLogo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            currentFragment = 0;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
            fragmentTransaction.replace(R.id.admin_Fragment_container,new AdminHomeFragment());
            fragmentTransaction.commit();

        }else if(menuItem.getItemId()== R.id.admin_nav_categories){
            getSupportActionBar().setTitle("Categories");
            setFragments(new AdminCategoryFragment(),1);
        }else if(menuItem.getItemId() == R.id.admin_nav_myItems){
            getSupportActionBar().setTitle("Select Category");
            setFragments(new AdminItemsFragment(),2);
        }else if(menuItem.getItemId()== R.id.admin_nav_myorders){
            getSupportActionBar().setTitle("Orders");
            setFragments(new AdminOrdersFragment(),3);
        }else if(menuItem.getItemId()== R.id.admin_nav_profile){
            getSupportActionBar().setTitle("Profile");
            setFragments(new AdminProfileFragment(),4);
        }else if(menuItem.getItemId()== R.id.admin_nav_setting){

        }else if(menuItem.getItemId()== R.id.admin_nav_signOut){
            saveData();
            Intent intent = new Intent(AdminHomeActivity.this, MainHomeActivity.class);
            startActivity(intent);
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void onBackpresed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    private void setFragments(Fragment fragment, int nb){
        if(currentFragment!=nb){
            actionbarLogo.setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            invalidateOptionsMenu();
            currentFragment = nb;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
            fragmentTransaction.replace(R.id.admin_Fragment_container,fragment);
            fragmentTransaction.commit();
        }

    }
    private void setNavData(){
        //Init services
        final String myIP = getResources().getString(R.string.server_ip);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        i.getAdminInfo(MainActivity.main_email).enqueue(new Callback<adminInfoModel>() {
            @Override
            public void onResponse(Call<adminInfoModel> call, Response<adminInfoModel> response) {
                admin_data = response.body();
                nav_email.setText(admin_data.getEmail());
                nav_username.setText(admin_data.getUsername());
                //Toast.makeText(AdminHomeActivity.this,admin_data.getUsername(),Toast.LENGTH_SHORT).show();
                String url = myIP+"image/"+admin_data.getAvatar();
                if(!admin_data.getAvatar().equals("default")){
                    Picasso.get().load(url).into(nav_img);
                }

            }

            @Override
            public void onFailure(Call<adminInfoModel> call, Throwable t) {

            }
        });
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Shared_Prefs,MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences(Shared_Prefs2,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor.putString(MainActivity.TEXT,"NULL");
        editor2.putString(MainActivity.TEXT2,"NULL");
        editor.apply();
        editor2.apply();
        loadData();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(Shared_Prefs,MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences(Shared_Prefs2,MODE_PRIVATE);
        MainActivity.username = sharedPreferences.getString(MainActivity.TEXT,"NULL");
        MainActivity.main_email = sharedPreferences2.getString(MainActivity.TEXT2,"NULL");

    }

}
