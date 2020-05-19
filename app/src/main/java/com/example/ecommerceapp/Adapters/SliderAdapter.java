package com.example.ecommerceapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ecommerceapp.Models.sliderModel;
import com.example.ecommerceapp.R;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private List<sliderModel> sliderModelList;

    public SliderAdapter(List<sliderModel> sliderModel) {
        this.sliderModelList = sliderModel;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.sliderlayout,container,false);
        ImageView banner = view.findViewById(R.id.banner_slider);
        banner.setImageResource(sliderModelList.get(position).getBanner());
        container.addView(view,0);
        return view;
    }

    @Override
    public int getCount() {
        return sliderModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
