package com.example.student_app.gym_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.student_app.R;

public class GymInfo extends AppCompatActivity {
    ImageButton menuButton;
    ImageButton userButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_info);
        menuButton = (ImageButton) findViewById(R.id.menuButton);
        userButton = (ImageButton) findViewById(R.id.userButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GymInfo.this, Menu.class));
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GymInfo.this, UserActivity.class));
            }
        });
    }
}
