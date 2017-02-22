package com.example.to;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.to.data.Contract;
import com.example.to.data.Helper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Helper helper;

    private CustomCursorAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        helper = new Helper(this);

        getLoaderManager().initLoader(1,null,this);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CustomCursorAdapter(this);
        mRecyclerView.setAdapter(mAdapter);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.show:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                Contract.ToDoEntry._ID,
                Contract.ToDoEntry.COLUMN_TITLE,};


        return new CursorLoader(this, Contract.ToDoEntry.CONTENT_URI,
                projection,
                null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        mAdapter.swapCursor(cursor);

      /*  TextView displayView = (TextView) findViewById(R.id.textView);

        try {

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(Contract.ToDoEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(Contract.ToDoEntry.COLUMN_TITLE);


            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName
                ));
            }
        } finally {
            cursor.close();
        }*/

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }
}