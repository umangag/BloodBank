package com.example.umang.bloodbank;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by UMANG on 4/28/2016.
 */
public class FullDetails extends AppCompatActivity {

    TextView name, gender, dob, phone_number,address, available_timings, blood_group;

    TextView labelname, labelgender, labeldob, labelphone_number,labeladdress, labelavailable_timings, labelblood_group;

    Button call,mail;

    DonorDetails donorDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fulldetails);

        name= (TextView) findViewById(R.id.name);
        gender= (TextView) findViewById(R.id.gender);
        dob= (TextView) findViewById(R.id.dob);
        phone_number= (TextView) findViewById(R.id.phone_number);
        address= (TextView) findViewById(R.id.address);
        available_timings= (TextView) findViewById(R.id.available_timings);
        blood_group= (TextView) findViewById(R.id.blood_group);

        labelname= (TextView) findViewById(R.id.labelname);
        labelgender= (TextView) findViewById(R.id.labelgender);
        labeldob= (TextView) findViewById(R.id.labeldob);
        labelphone_number= (TextView) findViewById(R.id.labelphonenumber);
        labeladdress= (TextView) findViewById(R.id.labeladdress);
        labelavailable_timings= (TextView) findViewById(R.id.labelavailabletimings);
        labelblood_group= (TextView) findViewById(R.id.labelbllodgroup);

        call= (Button) findViewById(R.id.call);
        mail= (Button) findViewById(R.id.mail);


        donorDetails=getIntent().getExtras().getParcelable("object");
        name.setText(donorDetails.getFirst_name()+" "+donorDetails.getLast_name());
        gender.setText(donorDetails.getGender());
        dob.setText(donorDetails.getDob());
        phone_number.setText(donorDetails.getPhone_number());
        address.setText(donorDetails.getAddress());
        available_timings.setText(donorDetails.getAvailable_timings());
        blood_group.setText(donorDetails.getBlood_group());


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ donorDetails.getPhone_number()));
                startActivity(intent);

            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "care@bloodbank.ml", null));
//                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//                                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });

    }
}
