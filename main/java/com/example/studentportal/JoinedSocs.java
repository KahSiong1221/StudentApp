package com.example.studentportal;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;




public class JoinedSocs extends ListActivity {

    //columns to be displayed and queried for simplecursoradapter
    int[] columns = {R.id.socname, R.id.num};
    String[] queryColumns = {"name", "user_contact"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchres);

        //open database
        DBManager myDB = new DBManager(this);
        myDB.open();

        //get all joined societies for dummy user
        int sid = 12345;
        Cursor socs = myDB.getJoinedSocs(sid);

        //set the simplecursor adapter on the joined societies
        SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(this, R.layout.joinedrow, socs, queryColumns, columns);
        setListAdapter(myAdapter);
    }

    //setting listener on list items
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        //getting the appropriate data for the clicked society from the cursor
        Cursor soc = (Cursor)l.getItemAtPosition(position);
        //go to the ClickedJoined page, passing the society name of the selected society
        Intent intent = new Intent(JoinedSocs.this, ClickedJoined.class);
        intent.putExtra("socname", soc.getString(1));
        startActivity(intent);
    }
}
