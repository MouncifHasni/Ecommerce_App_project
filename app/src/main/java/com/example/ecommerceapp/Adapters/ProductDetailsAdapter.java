package com.example.ecommerceapp.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ecommerceapp.Fragments.ProductDescriptionFragment;
import com.example.ecommerceapp.Fragments.ProductSpecificationFragment;
import com.example.ecommerceapp.Fragments.ShippingAndPaymentFragment;

public class ProductDetailsAdapter extends FragmentPagerAdapter {
    private int totalTabs;
    private String desc,condition,quantity,brand;
    private String est_delivery,shipping_cost,returns_type;
    private String paypal,mastercard,visa;

    public ProductDetailsAdapter(FragmentManager fm,int totalTabs,String desc,String condition,String quantity,String brand,
                                 String est_delivery,String shipping_cost,String returns_type,String paypal,String mastercard,String visa) {
        super(fm);
        this.totalTabs = totalTabs;
        this.desc = desc;
        this.condition = condition;
        this.quantity = quantity;
        this.brand = brand;
        this.est_delivery = est_delivery;
        this.shipping_cost = shipping_cost;
        this.returns_type = returns_type;
        this.paypal = paypal;
        this.mastercard = mastercard;
        this.visa = visa;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment(desc);
                return  productDescriptionFragment;
            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment(condition,quantity,brand);
                return productSpecificationFragment;
            case 2:
                ShippingAndPaymentFragment shippingAndPaymentFragment = new ShippingAndPaymentFragment(est_delivery,shipping_cost,returns_type,
                        paypal,mastercard,visa);
                return shippingAndPaymentFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
