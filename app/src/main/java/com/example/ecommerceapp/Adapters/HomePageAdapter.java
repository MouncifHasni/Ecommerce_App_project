package com.example.ecommerceapp.Adapters;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerceapp.Models.HomePageModel;
import com.example.ecommerceapp.Models.Horizontal_Product_Model;

import com.example.ecommerceapp.Models.sliderModel;
import com.example.ecommerceapp.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLiDER;
            case 1:
                return HomePageModel.Horizontal_scroll;
            case 2:
                return HomePageModel.Grid_Layout;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case HomePageModel.BANNER_SLiDER:
                View bannerSlider_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_sliding_layout,parent,false);
                return new HorizontalScrollViewholder(bannerSlider_view);
            case HomePageModel.Horizontal_scroll:
                View horizontalScroll_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout,parent,false);
                return new HorizontalScrollViewholder(horizontalScroll_view);
            case HomePageModel.Grid_Layout:
                View Grid_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gride_product_view,parent,false);
                return new HorizontalScrollViewholder(Grid_view);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (homePageModelList.get(position).getType()== HomePageModel.Horizontal_scroll){
                String h_title = homePageModelList.get(position).getTitle();
                List<Horizontal_Product_Model> horizontalScrollModelList = homePageModelList.get(position).getHorizontalProductModelList();
                ((HorizontalScrollViewholder)holder).setHorizontallayout(horizontalScrollModelList,h_title);

        }else if(homePageModelList.get(position).getType()== HomePageModel.Grid_Layout){
                String grid_title = homePageModelList.get(position).getTitle();
                List<Horizontal_Product_Model> GridModelList = homePageModelList.get(position).getHorizontalProductModelList();
                ((HorizontalScrollViewholder)holder).setGridLayout(GridModelList,grid_title);

        }else if(homePageModelList.get(position).getType()== HomePageModel.BANNER_SLiDER){
            List<sliderModel> bannerModelList = homePageModelList.get(position).getSliderModelList();
            ((HorizontalScrollViewholder)holder).setBannerSliderLayout(bannerModelList);
        }


    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class HorizontalScrollViewholder extends RecyclerView.ViewHolder {
        private TextView horizontalScrollTitle;
        private Button viewAllBtn;
        private RecyclerView horizontalScrollRecyclerView;
        //Grid layout
        private TextView gridLayouttitle;
        private Button gridLayoutbtn;
        private GridView gridLayoutGridView;
        //Banner
        private ViewPager bannerSlider;
        private int currentPage = 2;
        private Timer timer;
        final private long delayTime = 3000;
        final private long periodTime = 3000;

        public HorizontalScrollViewholder(@NonNull View itemView) {
            super(itemView);
            horizontalScrollTitle = itemView.findViewById(R.id.horizontal_layout_title);
            viewAllBtn = itemView.findViewById(R.id.horizontal_scroll_layout_btn);
            horizontalScrollRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recyclerview);


        }

        private void setHorizontallayout(List<Horizontal_Product_Model> horizontalProductModelList,String title){
            horizontalScrollTitle.setText(title);
            if(horizontalProductModelList.size()>8){
                viewAllBtn.setVisibility(View.VISIBLE);
            }else{
                viewAllBtn.setVisibility(View.INVISIBLE);
            }
            Horizontal_Scroll_ProductAdapter horizontal_scroll_productAdapter = new Horizontal_Scroll_ProductAdapter(horizontalProductModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalScrollRecyclerView.setLayoutManager(linearLayoutManager);

            horizontalScrollRecyclerView.setAdapter(horizontal_scroll_productAdapter);
            horizontal_scroll_productAdapter.notifyDataSetChanged();
        }
        private void setGridLayout(List<Horizontal_Product_Model> horizontalProductModelList,String title){
            gridLayouttitle = itemView.findViewById(R.id.grid_layout_title);
            gridLayoutbtn = itemView.findViewById(R.id.grid_layout_btn);
            gridLayoutGridView = itemView.findViewById(R.id.grid_layout_gridView);

            gridLayouttitle.setText(title);
            gridLayoutGridView.setAdapter(new GridLayoutProductAdapter(horizontalProductModelList));
        }
        //banner
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
        private void startBannerSlideShow(final List<sliderModel> sliderModelList){
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

        private void setBannerSliderLayout(final List<sliderModel> sliderModelList){
            bannerSlider = itemView.findViewById(R.id.banner_slider_viewpager);
            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
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

            startBannerSlideShow(sliderModelList);
            bannerSlider.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        startBannerSlideShow(sliderModelList);
                    }else{
                        pageLooper();
                        stopBannerSlideShow();
                    }
                    return false;
                }
            });
        }

    }
   /* public class GridProductHolder extends RecyclerView.ViewHolder{
        private TextView gridLayouttitle;
        private Button gridLayoutbtn;
        private GridView gridLayoutGridView;

        public GridProductHolder(@NonNull View itemView) {
            super(itemView);
             gridLayouttitle = itemView.findViewById(R.id.grid_layout_title);
             gridLayoutbtn = itemView.findViewById(R.id.grid_layout_btn);
             gridLayoutGridView = itemView.findViewById(R.id.grid_layout_gridView);
        }
        private void setGridLayout(List<Horizontal_Product_Model> horizontalProductModelList,String title){
            gridLayouttitle.setText(title);
            gridLayoutGridView.setAdapter(new GridLayoutProductAdapter(horizontalProductModelList));
        }
    }*/

}
