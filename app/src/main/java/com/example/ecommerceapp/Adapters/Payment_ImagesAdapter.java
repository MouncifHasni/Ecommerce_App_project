package com.example.ecommerceapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Models.PaymentImagesModel;
import com.example.ecommerceapp.R;

import java.util.List;
import java.util.zip.Inflater;

public class Payment_ImagesAdapter extends RecyclerView.Adapter<Payment_ImagesAdapter.ViewHolder> {
    private List<PaymentImagesModel> paymentImagesModelsList;

    public Payment_ImagesAdapter(List<PaymentImagesModel> paymentImagesModelsList) {
        this.paymentImagesModelsList = paymentImagesModelsList;
    }

    @NonNull
    @Override
    public Payment_ImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_images_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Payment_ImagesAdapter.ViewHolder holder, int position) {
        int payment_img = paymentImagesModelsList.get(position).getPayment_image();
        holder.setImagePayment(payment_img);
    }

    @Override
    public int getItemCount() {
        return paymentImagesModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView paymentImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentImage = itemView.findViewById(R.id.payment_single_image);
        }
        private void setImagePayment(int resource){
            paymentImage.setImageResource(resource);
        }
    }
}
