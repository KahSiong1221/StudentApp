package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BookCancel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_cancel);

        Bundle data         = getIntent().getExtras();
        int id        = data.getInt("id");
        String start  = data.getString("start");
        String end        = data.getString("end");
        String type = data.getString("type");
        String day = data.getString("day");

        DayTimeslot userCancelSlot = new DayTimeslot(day,start,end,type);
        userCancelSlot.setGymSlot_id(id);

        String dialog = "Cancel: \n" + day + " " + start + " to " + end;

        TextView tvCancel = (TextView) findViewById(R.id.textViewCancel);
        Button confirmCancelButton = (Button) findViewById(R.id.cancelYes);
        confirmCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GymDatabaseManager dbManager = new GymDatabaseManager(getApplicationContext());
                dbManager.open();
                Cursor cursor;
                cursor = dbManager.getUser();
                cursor.moveToFirst();
                int user_id = (cursor.getInt(0));
                dbManager.deleteBooking(id, user_id);
                Toast.makeText(BookCancel.this, "Slot removed", Toast.LENGTH_LONG).show();
                startActivity(new Intent(BookCancel.this, ViewBooking.class));
            }
        });
        // REFERENCING FOR DIALOG ALERT
        // https://www.youtube.com/watch?v=EiTLd0FQCTk
        // HAD TO ADD TO ANDROID MANIFEST
        // android:theme="@style/Theme.AppCompat.Dialog.Alert">
        // 1 of 3
        tvCancel.setText(dialog);
        //REFERENCING FINISHED
    }
}


