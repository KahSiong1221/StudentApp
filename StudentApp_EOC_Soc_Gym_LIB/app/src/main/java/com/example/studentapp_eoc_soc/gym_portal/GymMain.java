package com.example.studentapp_eoc_soc.gym_portal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.SaDbManager;

public class GymMain extends AppCompatActivity {
    Button loginButton;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_activity_main);
        et = (EditText) findViewById(R.id.login);
        GymDatabaseManager dbManager = new GymDatabaseManager(getApplicationContext());
        dbManager.open();

        loginButton = (Button) findViewById(R.id.buttonlog);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et.getEditableText().toString();
                Cursor cursor = dbManager.user(email);
                startActivity(new Intent(GymMain.this, Menu.class));

                /* Login - use 'jcrispo@gmail.com' to login
                if (cursor.moveToFirst()) {
                    startActivity(new Intent(MainActivity.this, Menu.class));
                }
                else{
                    Toast.makeText(MainActivity.this, "Wrong login details", Toast.LENGTH_LONG).show();
                }
                 */
            }
        });
    }
}
