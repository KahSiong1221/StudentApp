package com.example.studentapp_eoc_soc.gym_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.calendar.EventMainActivity;

public class SidebarMenu extends AppCompatActivity {

    ImageButton gymInfoBut;
    ImageButton gymApp;
    ImageButton calApp;
    ImageButton libApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar_menu);
        gymInfoBut = (ImageButton) findViewById(R.id.gyminfo);
        gymApp = (ImageButton) findViewById(R.id.gymApp);
        calApp = (ImageButton) findViewById(R.id.calApp);


        gymInfoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SidebarMenu.this, GymInfo.class));
            }
        });
        gymApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SidebarMenu.this, Menu.class));
            }
        });

       calApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SidebarMenu.this, EventMainActivity.class));
            }
        });
    }
}