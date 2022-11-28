package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class UserActivity extends AppCompatActivity {

    ImageButton menuButton;
    TextView fullName;
    TextView memType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        menuButton = (ImageButton) findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this, Menu.class));
            }
        });

        GymDatabaseManager dbManager = new GymDatabaseManager(this);
        dbManager.open();

        Cursor cursor1 = dbManager.getUser();
        cursor1.moveToFirst();
        fullName = (TextView) this.findViewById(R.id.fullName);

        String userName = cursor1.getString(1);
        int memCheck = cursor1.getInt(3);
        fullName.setText(userName);
        cursor1.close();
        if (memCheck == 1)
        {
            Cursor cursor2 = dbManager.getUserMembership();
            cursor2.moveToFirst();
            memType = (TextView)  this.findViewById(R.id.membership_type);
            String memDetails = cursor2.getString(0) + ": " + cursor2.getString(1);
            memType.setText(memDetails);

        }
        else
        {
            memType = (TextView)  this.findViewById(R.id.membership_type);
            memType.setText(R.string.no_mem);
        }
    }
}