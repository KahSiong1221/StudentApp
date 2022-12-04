package com.example.studentapp_eoc_soc;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;




public class SocJoinedActivity extends ListActivity {

    //columns to be displayed and queried for simplecursoradapter
    int[] columns = {R.id.socname, R.id.num};
    String[] queryColumns = {"name", "user_contact"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soc_search);

        //open database
        SocDbManager myDB = new SocDbManager(this);
        myDB.open();

        //get all joined societies for dummy user
        Cursor socs = myDB.getJoinedSocs(MainActivity.user.getUserId());

        //set the simplecursor adapter on the joined societies
        SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(
                this,
                R.layout.joined_soc_row,
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
        //go to the ClickedJoined page, passing the society name of the selected society
        Intent intent = new Intent(SocJoinedActivity.this, LeaveSocActivity.class);
        intent.putExtra("socname", soc.getString(1));

        startActivity(intent);
        finish();
    }
}