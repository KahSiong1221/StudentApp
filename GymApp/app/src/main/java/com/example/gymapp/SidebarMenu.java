package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SidebarMenu extends AppCompatActivity {

    ImageButton gymInfoBut;
    ImageButton gymApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar_menu);
        gymInfoBut = (ImageButton) findViewById(R.id.gyminfo);
        gymApp = (ImageButton) findViewById(R.id.gymApp);
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
    }
}