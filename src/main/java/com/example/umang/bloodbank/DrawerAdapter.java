package com.example.umang.bloodbank;

/**
 * Created by UMANG on 4/22/2016.
**/

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    Context context;
    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java
    private String name;        //String Resource for header View Name
    private int profile;        //int Resource for header view profile picture
    private String phone;       //String Resource for header view email

    DrawerAdapter(String Titles[], int Icons[], String Name, String Phone, int Profile, Context passedContext) { // MyAdapter Constructor with titles and icons parameter
        // titles, icons, name, email, profile pic are passed from the main activity as we

        mNavTitles = Titles;                //have seen earlier
        mIcons = Icons;
        name = Name;
        phone = Phone;
        profile = Profile;                     //here we assign those passed values to the values we declared here
        this.context = passedContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v, viewType, context); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object
        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header, parent, false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v, viewType, context); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created
        }

        return null;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.Holderid == 1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            if (position == 3) {

                holder.textView.setText(mNavTitles[position - 1]);
                holder.imageView.setVisibility(View.INVISIBLE);
//                final float scale = context.getResources().getDisplayMetrics().density;
//                int pixels_item_name = (int) (56 * scale + 0.5f);
//                holder.textView.setPadding(0,0,50,0);
                holder.textView.setTypeface(Fonts.getFont(context, Constants.AVENIR_REGULAR));
            } else {
                holder.textView.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
                holder.textView.setTypeface(Fonts.getFont(context, Constants.AVENIR_REGULAR));
                holder.imageView.setImageResource(mIcons[position - 1]);// Settimg the image with array of our icons
            }
        } else {

//            holder.profile.setImageResource(profile);           // Similarly we set the resources for header view
            holder.Name.setText(name);
            holder.Name.setTypeface(Fonts.getFont(context, Constants.AVENIR_DEMI));
            holder.phone.setText(phone);
            holder.phone.setTypeface(Fonts.getFont(context, Constants.AVENIR_REGULAR));

        }
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return mNavTitles.length + 1; // the number of items in the list will be +1 the titles including the header view.
    }

    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int Holderid;

        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView Name;
        TextView phone;

        Context contxt;

        public ViewHolder(View itemView, int ViewType, Context c) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            contxt = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            if (ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            } else if (ViewType == TYPE_HEADER) {


                Name = (TextView) itemView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
                phone = (TextView) itemView.findViewById(R.id.phone);       // Creating Text View object from header.xml for email
//                profile = (ImageView) itemView.findViewById(R.id.circleView);// Creating Image view object from header.xml for profile pic
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }

        }

        @Override
        public void onClick(View v) {
        }
    }

}