package com.example.ecommerceapp.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerceapp.Adapters.GridLayoutProductAdapter;
import com.example.ecommerceapp.Adapters.HomePageAdapter;
import com.example.ecommerceapp.Adapters.Horizontal_Scroll_ProductAdapter;
import com.example.ecommerceapp.Adapters.SliderAdapter;
import com.example.ecommerceapp.Models.HomePageModel;
import com.example.ecommerceapp.Models.Horizontal_Product_Model;
import com.example.ecommerceapp.Models.sliderModel;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    //Banner
    private ViewPager bannerSlider;
    private SliderAdapter sliderAdapter;
    private List<sliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long delayTime = 3000;
    final private long periodTime = 3000;

    //Horizontal scroll
    private TextView horizontalScrollTitle;
    private Button viewAllBtn;
    private RecyclerView horizontalScrollRecyclerView;
    private List<Horizontal_Product_Model> horizontalProductModelList;
    Horizontal_Scroll_ProductAdapter horizontal_scroll_productAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_home,container,false);
        //Banner
        sliderModelList = new ArrayList<>();
        sliderModelList.add(new sliderModel(R.drawable.banner1));
        sliderModelList.add(new sliderModel(R.drawable.lock_icon));
        sliderModelList.add(new sliderModel(R.drawable.red_zone));
        sliderModelList.add(new sliderModel(R.drawable.banner1));
        sliderModelList.add(new sliderModel(R.drawable.redmail_icon));
        //****BannerAdapterSetup(view);
        //horizontal scroll layout
        //****HorizontalscrollLayoutSetup(view);
        //GridLayout Setup
        //****GrideLayoutSetup(view);
        horizontalProductModelList = new ArrayList<>();
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.redmail_icon,"mail","100 dhs"));
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.lock_icon,"lock","150 dhs"));
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.home_icon,"home","70 dhs"));
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.redmail_icon,"mail","100 dhs"));
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.lock_icon,"lock","150 dhs"));
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.home_icon,"home","70 dhs"));

        //Setup All Layouts
        RecyclerView homePageRecyclerview = view.findViewById(R.id.HomePage_recyclerView);
        LinearLayoutManager homePageLayoutManager = new LinearLayoutManager(getContext());
        homePageLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerview.setLayoutManager(homePageLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,horizontalProductModelList,"Daily Deals"));
       homePageModelList.add(new HomePageModel(1,horizontalProductModelList,"Trend"));
        homePageModelList.add(new HomePageModel(2,horizontalProductModelList,"Electronics"));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);

        homePageRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void pageLooper(){
        /*if(currentPage == sliderModelList.size()-2){
            currentPage = 2;
            bannerSlider.setCurrentItem(currentPage,false);
        }
        if(currentPage == 1){
            currentPage = sliderModelList.size()-3;
            bannerSlider.setCurrentItem(currentPage,false);
        }*/
    }
    private void startBannerSlideShow(){
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage>= sliderModelList.size()){
                    currentPage = 1;
                }
                bannerSlider.setCurrentItem(currentPage++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },delayTime,periodTime);
    }
    private void stopBannerSlideShow(){
        timer.cancel();
    }
    private void BannerAdapterSetup(View view){
        bannerSlider = view.findViewById(R.id.banner_slider_viewpager);
        sliderModelList = new ArrayList<>();

        sliderModelList.add(new sliderModel(R.drawable.banner1));
        sliderModelList.add(new sliderModel(R.drawable.lock_icon));
        sliderModelList.add(new sliderModel(R.drawable.red_zone));
        sliderModelList.add(new sliderModel(R.drawable.banner1));
        sliderModelList.add(new sliderModel(R.drawable.redmail_icon));


        sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSlider.setAdapter(sliderAdapter);
        sliderAdapter.notifyDataSetChanged();
        bannerSlider.setClipToPadding(false);
        bannerSlider.setPageMargin(20);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE){
                    pageLooper();
                }
            }
        };
        bannerSlider.addOnPageChangeListener(onPageChangeListener);

        startBannerSlideShow();
        bannerSlider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    startBannerSlideShow();
                }else{
                    pageLooper();
                    stopBannerSlideShow();
                }
                return false;
            }
        });
    }
    private void HorizontalscrollLayoutSetup(View view){
        horizontalScrollTitle = view.findViewById(R.id.horizontal_layout_title);
        viewAllBtn = view.findViewById(R.id.horizontal_scroll_layout_btn);
        horizontalScrollRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerview);

        horizontalProductModelList = new ArrayList<>();
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.redmail_icon,"mail","100 dhs"));
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.lock_icon,"lock","150 dhs"));
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.home_icon,"home","70 dhs"));
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.redmail_icon,"mail","100 dhs"));
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.lock_icon,"lock","150 dhs"));
        horizontalProductModelList.add(new Horizontal_Product_Model(R.drawable.home_icon,"home","70 dhs"));

        horizontal_scroll_productAdapter = new Horizontal_Scroll_ProductAdapter(horizontalProductModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalScrollRecyclerView.setLayoutManager(linearLayoutManager);

        horizontalScrollRecyclerView.setAdapter(horizontal_scroll_productAdapter);
        horizontal_scroll_productAdapter.notifyDataSetChanged();
    }
    private void GrideLayoutSetup(View view){
        TextView gridLayouttitle = view.findViewById(R.id.grid_layout_title);
        Button gridLayoutbtn = view.findViewById(R.id.grid_layout_btn);
        GridView gridLayoutGridView = view.findViewById(R.id.grid_layout_gridView);

        gridLayoutGridView.setAdapter(new GridLayoutProductAdapter(horizontalProductModelList));

    }


}
