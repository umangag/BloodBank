package com.example.umang.bloodbank;

/**
 * Created by UMANG on 4/22/2016.
 **/

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DonorDetailsAdapter extends RecyclerView.Adapter<DonorDetailsAdapter.ViewHolder> {

    ArrayList<DonorDetails> donorDetailsArrayList;
    Context context;

    public DonorDetailsAdapter(ArrayList<DonorDetails> donorDetailsArrayList,Context context) {
        this.donorDetailsArrayList = donorDetailsArrayList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false); //Inflating the layout

        final ViewHolder viewHolder= new ViewHolder(v);
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,FullDetails.class);
                Bundle b=new Bundle();
                b.putParcelable("object",donorDetailsArrayList.get(viewHolder.getAdapterPosition()));
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DonorDetails donorDetails = donorDetailsArrayList.get(position);
        holder.name.setText(donorDetails.getFirst_name() + " " + donorDetails.getLast_name());
        holder.dob.setText(donorDetails.getDob());
        holder.phoneNo.setText(donorDetails.getPhone_number());

    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return donorDetailsArrayList.size(); // the number of items in the list will be +1 the titles including the header view.
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, dob, phoneNo;
        RelativeLayout relativeLayout;


        public ViewHolder(View itemView) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            dob = (TextView) itemView.findViewById(R.id.DOB);
            phoneNo = (TextView) itemView.findViewById(R.id.phone_number);
            relativeLayout= (RelativeLayout) itemView.findViewById(R.id.item_layout);

        }
    }

}