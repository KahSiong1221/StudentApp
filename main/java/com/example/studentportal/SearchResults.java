package com.example.studentportal;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SearchResults extends ListActivity
{
    int[] columns = {R.id.socname};
    String[] queryColumns = {"name"};
    private Button joinButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchres);
        Intent intent = getIntent();
        String sn = intent.getStringExtra("socname");

        DBManager myDB = new DBManager(this);
        myDB.open();

        Cursor socs = myDB.getAllSocs(sn);

        SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(this, R.layout.row, socs, queryColumns, columns);
        setListAdapter(myAdapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Cursor soc = (Cursor)l.getItemAtPosition(position);
        Intent intent = new Intent(SearchResults.this, ClickedActivity.class);
        intent.putExtra("socname", soc.getString(1));


        startActivity(intent);
    }
}
