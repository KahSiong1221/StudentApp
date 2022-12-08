package com.example.studentapp_eoc_soc.gym_portal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.SaDbManager;
import com.example.studentapp_eoc_soc.User;

public class BookConfirm extends AppCompatActivity {
    private GymDatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_confirm);


        Bundle data = getIntent().getExtras();
        int id = data.getInt("id");
        String start = data.getString("start");
        String end = data.getString("end");
        String type = data.getString("type");
        String day = data.getString("day");

        DayTimeslot userBookSlot = new DayTimeslot(day, start, end, type);
        userBookSlot.setGymSlot_id(id);

        String dialog = "Book: " + start + " -> " + end;

        TextView tvConfirm = (TextView) findViewById(R.id.textViewConfirm);
        Button confirmButton = (Button) findViewById(R.id.confirmYes);
        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                GymDatabaseManager dbManager = new GymDatabaseManager(getApplicationContext());
                dbManager.open();
                Cursor cursor;
                cursor = dbManager.getUser();
                cursor.moveToFirst();
                User myUser = new User();
                myUser.setUserId(cursor.getInt(0));
                myUser.setUserName(cursor.getString(1));
                myUser.setEmail(cursor.getString(2));
                myUser.setPhoneNo(cursor.getString(3));
                myUser.setMemCheck(cursor.getInt(4));
                dbManager.addUserBooking(myUser, userBookSlot);
                Toast.makeText(BookConfirm.this, "Slot booked", Toast.LENGTH_LONG).show();
                startActivity(new Intent(BookConfirm.this, BookGymTimeslot.class));
            }
        });
        // REFERENCING FOR DIALOG ALERT
        // https://www.youtube.com/watch?v=EiTLd0FQCTk
        // HAD TO ADD TO ANDROID MANIFEST
        // android:theme="@style/Theme.AppCompat.Dialog.Alert">
        // 1 of 3
        tvConfirm.setText(dialog);
        //REFERENCING FINISHED
    }
}