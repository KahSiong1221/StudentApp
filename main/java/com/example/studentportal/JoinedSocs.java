package com.example.studentportal;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;




public class JoinedSocs extends ListActivity {

    int[] columns = {R.id.socname, R.id.num};
    String[] queryColumns = {"name", "user_contact"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchres);


        DBManager myDB = new DBManager(this);
        myDB.open();

        int sid = 12345;
        Cursor socs = myDB.getJoinedSocs(sid);

        SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(this, R.layout.joinedrow, socs, queryColumns, columns);
        setListAdapter(myAdapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Cursor soc = (Cursor)l.getItemAtPosition(position);
        Intent intent = new Intent(JoinedSocs.this, ClickedJoined.class);
        intent.putExtra("socname", soc.getString(1));
        intent.putExtra("socid", soc.getInt(0));

        startActivity(intent);
    }
}
