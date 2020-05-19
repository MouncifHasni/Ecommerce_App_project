package com.example.ecommerceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ecommerceapp.Admin.AdminHomeActivity;
import com.example.ecommerceapp.Admin.AdminModels.adminInfoModel;
import com.example.ecommerceapp.Fragments.CategoryFragment;
import com.example.ecommerceapp.Fragments.HomeFragment;
import com.example.ecommerceapp.Fragments.MyCartFragment;
import com.example.ecommerceapp.Fragments.MyFavFragment;
import com.example.ecommerceapp.Fragments.MyOrdersFragment;
import com.example.ecommerceapp.Fragments.MyProfileFragment;
import com.example.ecommerceapp.Models.ClientModel;
import com.example.ecommerceapp.retrofit.IMyService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static  final String Shared_Prefs = "sharedPrefs";
    public static  final String Shared_Prefs2 = "sharedPrefs2";

    private DrawerLayout drawer;
    private static final int HOME_FRAMGMENT = 0;
    private static int currentFragment = -1;
    private NavigationView navigationView;
    private ImageView actionbarLogo;
    private TextView nav_username,nav_email;
    private CircleImageView nav_img;
    private ClientModel client_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionbarLogo = findViewById(R.id.actionbar_logo);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        nav_img = header.findViewById(R.id.client_nav_profile_image);
        nav_username = header.findViewById(R.id.client_nav_profile_username);
        nav_email = header.findViewById(R.id.client_nav_profile_email);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setNavData();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
            currentFragment = 0;
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(currentFragment==HOME_FRAMGMENT) {
            getMenuInflater().inflate(R.menu.home, menu);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== R.id.home_search_icon){
            return true;
        }else if(item.getItemId()== R.id.hom_shop_icon){
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            setFragments(new MyCartFragment(),2);
            navigationView.getMenu().getItem(2).setChecked(true);
            getSupportActionBar().setTitle("My Cart");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getItemId()== R.id.nav_home){
            actionbarLogo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            currentFragment = 0;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
            fragmentTransaction.replace(R.id.Fragment_container,new HomeFragment());
            fragmentTransaction.commit();
        }else if(menuItem.getItemId()== R.id.nav_categories){
            getSupportActionBar().setTitle("Categories");
            setFragments(new CategoryFragment(),1);
        }else if(menuItem.getItemId() == R.id.nav_myCart){
            getSupportActionBar().setTitle("My Cart");
            setFragments(new MyCartFragment(),2);
        } else if(menuItem.getItemId()== R.id.nav_wishlist){
            getSupportActionBar().setTitle("Saved");
            setFragments(new MyFavFragment(),3);
        }else if(menuItem.getItemId()== R.id.nav_myorders){
            getSupportActionBar().setTitle("My Orders");
            setFragments(new MyOrdersFragment(),4);

        }else if(menuItem.getItemId()== R.id.nav_profile){
            getSupportActionBar().setTitle("My Profile");
            setFragments(new MyProfileFragment(),5);

        }else if(menuItem.getItemId()== R.id.nav_setting){

        }else if(menuItem.getItemId()== R.id.nav_signOut){
            saveData();
            Intent intent = new Intent(HomeActivity.this, MainHomeActivity.class);
            startActivity(intent);
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavData(){
        //Init services
        final String myIP = getResources().getString(R.string.server_ip);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(myIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMyService i = retrofit.create(IMyService.class);
        i.getClientInfo(MainActivity.main_email).enqueue(new Callback<ClientModel>() {
            @Override
            public void onResponse(Call<ClientModel> call, Response<ClientModel> response) {
                client_data = response.body();
                nav_email.setText(client_data.getEmail());
                nav_username.setText(client_data.getUsername());
                //Toast.makeText(AdminHomeActivity.this,admin_data.getUsername(),Toast.LENGTH_SHORT).show();
                String url = myIP+"image/"+client_data.getAvatar();
                if(!client_data.getAvatar().equals("default")){
                    Picasso.get().load(url).into(nav_img);
                }
            }

            @Override
            public void onFailure(Call<ClientModel> call, Throwable t) {

            }
        });
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
            fragmentTransaction.replace(R.id.Fragment_container,fragment);
            fragmentTransaction.commit();
        }

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
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Shared_Prefs,MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences(Shared_Prefs2,MODE_PRIVATE);
        MainActivity.username = sharedPreferences.getString(MainActivity.TEXT,"NULL");
        MainActivity.main_email = sharedPreferences2.getString(MainActivity.TEXT2,"NULL");

    }


}
