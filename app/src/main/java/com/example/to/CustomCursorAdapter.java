package com.example.to;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.to.data.Contract;


public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.Vh> {

    Cursor mCursor;
    private Context mContext;


    public CustomCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
        return new Vh(v);
    }

    @Override
    public void onBindViewHolder(Vh holder, int position) {



        int descriptionIndex = mCursor.getColumnIndex(Contract.ToDoEntry.COLUMN_TITLE);
        int contentIndex = mCursor.getColumnIndex(Contract.ToDoEntry.COLUMN_DESCRIPTION);
        int picked_hour_index = mCursor.getColumnIndex(Contract.ToDoEntry.COLUMN_HOUR);
        int picked_minute_index = mCursor.getColumnIndex(Contract.ToDoEntry.COLUMN_MINUTE);


        mCursor.moveToPosition(position); // get to the right location in the cursor


        String description = mCursor.getString(descriptionIndex);
        String content = mCursor.getString(contentIndex);
        int picked_hour = mCursor.getInt(picked_hour_index);
        int picked_minute = mCursor.getInt(picked_minute_index);



        if (content != null) {
            holder.textView.setText(description + content);

        } else {
            holder.textView.setText(description);
        }

        if (picked_hour > 0 && picked_minute > 0){

         //   Toast.makeText(mContext, "" + picked_hour + picked_minute, Toast.LENGTH_LONG).show();

            Calendar curunt_calender = Calendar.getInstance();
            curunt_calender.setTimeInMillis(System.currentTimeMillis());

            final Calendar cal = new GregorianCalendar();
            cal.add(Calendar.DAY_OF_YEAR, curunt_calender.get(Calendar.DAY_OF_YEAR));
            cal.set(Calendar.HOUR_OF_DAY, picked_hour);
            cal.set(Calendar.MINUTE, picked_minute);
            cal.set(Calendar.SECOND, curunt_calender.get(Calendar.SECOND));
            cal.set(Calendar.MILLISECOND, curunt_calender.get(Calendar.MILLISECOND));
            cal.set(Calendar.DATE, curunt_calender.get(Calendar.DATE));
            cal.set(Calendar.MONTH, curunt_calender.get(Calendar.MONTH));

            long time = cal.getTimeInMillis();


            setAlarm(time);
        }



    }

    private void setAlarm(long time) {
        Intent intent = new Intent(mContext, AlarmToastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(mContext.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }



    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class Vh extends RecyclerView.ViewHolder {
        TextView textView;

        public Vh(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.rowItem);
        }
    }
}
