package com.example.ecommerceapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Models.ProductSpecificationModel;
import com.example.ecommerceapp.R;

import java.util.List;

public class ProductSpecificcationAdapter extends RecyclerView.Adapter<ProductSpecificcationAdapter.ViewHolder> {

    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductSpecificcationAdapter(List<ProductSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @NonNull
    @Override
    public ProductSpecificcationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecificcationAdapter.ViewHolder holder, int position) {
        String specTitle = productSpecificationModelList.get(position).getSpecificationTitle();
        String specVal = productSpecificationModelList.get(position).getSpecificationValue();
        holder.setSpecifications(specTitle,specVal);
    }

    @Override
    public int getItemCount() {
        return productSpecificationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.specification_title);
            value = itemView.findViewById(R.id.specification_value);
        }

        private void setSpecifications(String mytitle,String val){
            title.setText(mytitle);
            value.setText(val);
        }
    }
}
