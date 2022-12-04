package com.example.libraryapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseBookingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_booking);
        Button exitChooseBookingButton= (Button)findViewById(R.id.exitChooseBookingButton);
        Button viewComputerSlotsButton = (Button)findViewById(R.id.viewComputerButton);
        Button viewRoomSlotsbutton = (Button) findViewById(R.id.viewRoomButton);


        viewComputerSlotsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent computerSlotsIntent = new Intent(ChooseBookingActivity.this, computerSlotsActivity.class);
                startActivity(computerSlotsIntent);
            }
        });


        viewRoomSlotsbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent roomSlotsIntent = new Intent(ChooseBookingActivity.this, roomSlotsActivity.class);
                startActivity(roomSlotsIntent);
            }
        });

        exitChooseBookingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
            }
        });


    }

}
