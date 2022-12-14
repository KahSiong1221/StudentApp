package com.example.studentapp_eoc_soc.gym_portal;

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

import java.util.Calendar;

public class BuyConfirm extends AppCompatActivity {
    private GymDatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_confirm);


        Bundle data         = getIntent().getExtras();
        int id        = data.getInt("id");
        String type  = data.getString("type");
        String price        = data.getString("price");


        Membership userMembership = new Membership(type, price);
        userMembership.setMembership_id(id);

        String dialog = "Confirm buy: " + type ;


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
                User myUser= new User();
                myUser.setUserId(cursor.getInt(0));
                myUser.setUserName(cursor.getString(1));
                myUser.setEmail(cursor.getString(2));
                myUser.setPhoneNo(cursor.getString(3));
                myUser.setMemCheck(cursor.getInt(4));

                int memChk = cursor.getInt(4);


                if (id == 1){
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DATE, 30);
                    String future = String.valueOf(c.getTime());
                    String expiry[] = future.split(" ");
                    future = expiry[2] + " " + expiry[1] + ", " + expiry[5];

                    //check if user has no memberships
                    if (memChk == 0){
                        myUser.setMemCheck(1);
                        dbManager.updatePerson(myUser.getUserId(), myUser.getMemCheck());
                        dbManager.addUserMembership(myUser , userMembership, future);
                        Toast.makeText(BuyConfirm.this, "Purchased: " + type +", Thank you!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(BuyConfirm.this, "Already a member", Toast.LENGTH_LONG).show();
                    }
                }

                else if (id == 2){
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DATE, 90);
                    String future = String.valueOf(c.getTime());
                    String expiry[] = future.split(" ");
                    future = expiry[2] + " " + expiry[1] + ", " + expiry[5];

                    //check if user has no memberships
                    if (memChk == 0){
                        myUser.setMemCheck(1);
                        dbManager.updatePerson(myUser.getUserId(), myUser.getMemCheck());
                        dbManager.addUserMembership(myUser , userMembership, future);
                        Toast.makeText(BuyConfirm.this, "Purchased: " + type +", Thank you!", Toast.LENGTH_LONG).show();
                    }
                    else if (memChk == 1){
                        Toast.makeText(BuyConfirm.this, "Already a member", Toast.LENGTH_LONG).show();
                    }
                }

                else if (id == 3){
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DATE, 180);
                    String future = String.valueOf(c.getTime());
                    String expiry[] = future.split(" ");
                    future = expiry[2] + " " + expiry[1] + ", " + expiry[5];

                    //check if user has no memberships
                    if (memChk == 0){
                        myUser.setMemCheck(1);
                        dbManager.updatePerson(myUser.getUserId(), myUser.getMemCheck());
                        dbManager.addUserMembership(myUser , userMembership, future);
                        Toast.makeText(BuyConfirm.this, "Purchased: " + type +", Thank you!", Toast.LENGTH_LONG).show();
                    }
                    else if (memChk == 1){
                        Toast.makeText(BuyConfirm.this, "Already a member", Toast.LENGTH_LONG).show();
                    }
                }

                else if (id == 4){
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DATE, 360);
                    String future = String.valueOf(c.getTime());
                    String expiry[] = future.split(" ");
                    future = expiry[2] + " " + expiry[1] + ", " + expiry[5];

                    //check if user has no memberships
                    if (memChk == 0){
                        myUser.setMemCheck(1);
                        dbManager.updatePerson(myUser.getUserId(), myUser.getMemCheck());
                        dbManager.addUserMembership(myUser , userMembership, future);
                        Toast.makeText(BuyConfirm.this, "Purchased: " + type +", Thank you!", Toast.LENGTH_LONG).show();
                    }
                    else if (memChk == 1){
                        Toast.makeText(BuyConfirm.this, "Already a member", Toast.LENGTH_LONG).show();
                    }
                }

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