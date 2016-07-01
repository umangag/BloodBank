package com.example.umang.bloodbank;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by UMANG on 4/28/2016.
 */
public class DonorDetails implements Parcelable {
    String first_name, last_name, gender, phone_number, address, available_timings, blood_group, dob;

    public DonorDetails(String first_name, String last_name, String gender, String phone_number, String address,
                        String available_timings, String blood_group, String dob) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.phone_number = phone_number;
        this.address = address;
        this.available_timings = available_timings;
        this.blood_group = blood_group;
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAddress() {
        return address;
    }

    public String getAvailable_timings() {
        return available_timings;
    }

    public String getBlood_group() {
        return blood_group;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(gender);
        dest.writeString(address);
        dest.writeString(available_timings);
        dest.writeString(dob);
        dest.writeString(phone_number);
        dest.writeString(blood_group);
    }

    public DonorDetails(Parcel source) {
        first_name = source.readString();
        last_name = source.readString();
        gender = source.readString();
        address = source.readString();
        available_timings = source.readString();
        dob = source.readString();
        phone_number = source.readString();
        blood_group = source.readString();

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DonorDetails createFromParcel(Parcel in) {
            return new DonorDetails(in);
        }

        public DonorDetails[] newArray(int size) {
            return new DonorDetails[size];
        }
    };
}
