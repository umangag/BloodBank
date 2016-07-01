package com.example.umang.bloodbank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by UMANG on 4/28/2016.
 */
public class donordetailslist extends AppCompatActivity {


    RecyclerView recyclerView;
    DonorDetailsAdapter donorDetailsAdapter;
    ArrayList<DonorDetails> donorDetailsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        donorDetailsArrayList = getIntent().getParcelableArrayListExtra("list");

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        donorDetailsAdapter = new DonorDetailsAdapter(donorDetailsArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(donorDetailsAdapter);


    }
}
