package com.example.student_app.gym_portal;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.student_app.R;
import com.example.student_app.SaDbManager;

import java.util.ArrayList;

public class GymSubscription extends ListActivity {

    private ArrayList<Membership> MembershipList = new ArrayList<>();
    ImageButton menuButton;
    ImageButton userButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_subscription);

        menuButton = (ImageButton) findViewById(R.id.menuButton);
        userButton = (ImageButton) findViewById(R.id.userButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GymSubscription.this, Menu.class));
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GymSubscription.this, UserActivity.class));
            }
        });

        SaDbManager dbManager = new SaDbManager(this);
        dbManager.open();


        dbManager.addMemberships(new Membership("30 Day Membership", "$30"));
        dbManager.addMemberships(new Membership("90 Day Membership", "$70"));
        dbManager.addMemberships(new Membership("180 Day Membership", "$120"));
        dbManager.addMemberships(new Membership("360 Day Membership", "$180"));


        Cursor cursor = dbManager.getMemberships();
        MembershipList.clear();

        if (cursor.moveToFirst()) {
            do {
                Membership membership= new Membership();
                membership.setMembership_id(Integer.parseInt(cursor.getString(0)));
                membership.setMemType(cursor.getString(1));
                membership.setPrice(cursor.getString(2));
                // Adding task to list
                MembershipList.add(membership);
            } while (cursor.moveToNext());
        }

        MembershipAdapter membershipAdapter = new MembershipAdapter(this, R.layout.memberships, MembershipList);
        setListAdapter(membershipAdapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id)
    {

        Membership membershipClicked = (Membership) l.getItemAtPosition(position);

        // switching screens requires an intent
        Intent intent = new Intent(getApplicationContext(), BuyConfirm.class);

        // add the data to send to the next screen onto the intent as "extras"
        intent.putExtra("id", membershipClicked.getMembership_id());
        intent.putExtra("type", membershipClicked.getMemType());
        intent.putExtra("price", membershipClicked.getPrice());


        // start the next activity
        startActivity(intent);
    }


    class MembershipAdapter extends ArrayAdapter<Membership> {
        ArrayList<Membership> MembershipList;

        public MembershipAdapter(@NonNull Context context, int textViewResourceId, ArrayList<Membership> membershipOptions) {
            super(context, textViewResourceId, membershipOptions);
            this.MembershipList = membershipOptions;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.memberships, null);
            }

            Membership currentMembership = MembershipList.get(position);

            if (currentMembership != null){
                TextView memType = (TextView) v.findViewById(R.id.membership_type);
                TextView memPrice = (TextView) v.findViewById(R.id.price);

                memType.setText(currentMembership.getMemType());
                memPrice.setText(currentMembership.getPrice());
            }

            return v;
        }
    }
}