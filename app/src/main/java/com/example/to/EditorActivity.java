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

import java.util.Calendar;
import java.util.GregorianCalendar;


public class EditorActivity extends AppCompatActivity implements Listner {
    private EditText mNameEditText,edt_content;
    FloatingActionButton fab,fabTime;
    int hour , minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
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
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
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
        values.put(Contract.ToDoEntry.COLUMN_HOUR,hour);
        values.put(Contract.ToDoEntry.COLUMN_MINUTE,minute);

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

    @Override
    public void picked_time(int hour, int minute) {

        this.hour = hour;
        this.minute = minute;
    }

     /*   Calendar curunt_calender = Calendar.getInstance();
        curunt_calender.setTimeInMillis(System.currentTimeMillis());

        final Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, curunt_calender.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, curunt_calender.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, curunt_calender.get(Calendar.MILLISECOND));
        cal.set(Calendar.DATE, curunt_calender.get(Calendar.DATE));
        cal.set(Calendar.MONTH, curunt_calender.get(Calendar.MONTH));

        long time = cal.getTimeInMillis();


        setAlarm(time);

    }

    private void setAlarm(long time) {
        Intent intent = new Intent(this, AlarmToastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }*/
}
