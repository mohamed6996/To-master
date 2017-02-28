package com.example.to;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class EditorActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private EditText mNameEditText, edt_content;
    FloatingActionButton fab, fabTime;
    int year, monthOfYear, dayOfMonth;
    int hourOfDay, minute, second;
    ContentValues values;

   static long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        values = new ContentValues();

        mNameEditText = (EditText) findViewById(R.id.editText);
        edt_content = (EditText) findViewById(R.id.edtContent);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fabTime = (FloatingActionButton) findViewById(R.id.floatingActionButton3);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               insertPet();
                finish();
            }
        });

        fabTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        EditorActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                dpd.setVersion(DatePickerDialog.Version.VERSION_1);
                dpd.setThemeDark(true);
            }
        });


    }


    private void insertPet() {
        String nameString = mNameEditText.getText().toString().trim();
        String contentString = edt_content.getText().toString().trim();


        Helper mDbHelper = new Helper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        values.put(Contract.ToDoEntry.COLUMN_TITLE, nameString);
        values.put(Contract.ToDoEntry.COLUMN_DESCRIPTION, contentString);
        values.put(Contract.ToDoEntry.COLUMN_HOUR,time);

        getContentResolver().insert(Contract.ToDoEntry.CONTENT_URI, values);


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
            //    insertPet();
            //    finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;

        time_picker();

    }

    public void time_picker() {
        Calendar n = Calendar.getInstance();
        TimePickerDialog t = TimePickerDialog.newInstance(EditorActivity.this, n.get(Calendar.HOUR_OF_DAY),
                n.get(Calendar.MINUTE),
                true);
        t.show(getFragmentManager(), "timepickerdialog");
        t.setVersion(TimePickerDialog.Version.VERSION_1);
        t.setThemeDark(true);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;

        setCalender();

    }

    public void setCalender() {
        Calendar curunt_calender = Calendar.getInstance();
        curunt_calender.setTimeInMillis(System.currentTimeMillis());

        final Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.HOUR_OF_DAY, this.hourOfDay);
        cal.set(Calendar.MINUTE, this.minute);
        cal.set(Calendar.SECOND, this.second);

        time =  cal.getTimeInMillis();

       // insertPet(time);


    }



}
