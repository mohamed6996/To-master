package com.example.to;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.to.data.Contract;
import com.example.to.data.Helper;


public class EditorActivity extends AppCompatActivity {
    private EditText mNameEditText,edt_content;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mNameEditText = (EditText) findViewById(R.id.editText);
        edt_content = (EditText) findViewById(R.id.edtContent);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPet();
                finish();
            }
        });


    }




    private void insertPet() {
        String nameString = mNameEditText.getText().toString().trim();
        String contentString = edt_content.getText().toString().trim();


        Helper mDbHelper = new Helper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.ToDoEntry.COLUMN_TITLE, nameString);
        values.put(Contract.ToDoEntry.COLUMN_DESCRIPTION, contentString);


        getContentResolver().insert(Contract.ToDoEntry.CONTENT_URI,values);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertPet();
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
