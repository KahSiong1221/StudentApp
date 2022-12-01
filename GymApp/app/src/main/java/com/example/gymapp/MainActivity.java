package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.login);
        GymDatabaseManager dbManager = new GymDatabaseManager(getApplicationContext());
        dbManager.open();

        loginButton = (Button) findViewById(R.id.buttonlog);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et.getEditableText().toString();
                Cursor cursor = dbManager.user(email);
                startActivity(new Intent(MainActivity.this, Menu.class));

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


