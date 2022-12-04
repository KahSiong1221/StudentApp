package com.example.studentapp_eoc_soc.soc_portal;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.studentapp_eoc_soc.R;

public class SocSearchActivity extends ListActivity {
    //columns to be displayed and queried for simplecursoradapter
    int[] columns = {R.id.socname};
    String[] queryColumns = {"name"};

    private Button joinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soc_search);

        //pass the searched variable
        Intent intent = getIntent();
        String sn = intent.getStringExtra("socname");

        //open database
        SocDbManager myDB = new SocDbManager(this);
        myDB.open();

        //get all societies that match search and return as a cursor
        Cursor socs = myDB.getAllSocs(sn);

        //set simplecursoradapter on new cursor
        SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(
                this,
                R.layout.soc_search_row,
                socs,
                queryColumns,
                columns
        );
        setListAdapter(myAdapter);
    }

    //setting listener on list items
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        //getting the appropriate data for the clicked society from the cursor
        Cursor soc = (Cursor)l.getItemAtPosition(position);
        //go to the ClickedActivity page, passing the society name of the selected society
        Intent intent = new Intent(SocSearchActivity.this, JoinSocActivity.class);
        intent.putExtra("socname", soc.getString(1));

        startActivity(intent);
        finish();
    }
}