package com.example.to;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.widget.Toast;


import com.example.to.data.Contract;


public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.Vh> {

    Cursor mCursor;
    private Context mContext;

    long picked_hour;


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
        //     int picked_minute_index = mCursor.getColumnIndex(Contract.ToDoEntry.COLUMN_MINUTE);


        mCursor.moveToPosition(position); // get to the right location in the cursor


        String description = mCursor.getString(descriptionIndex);
        String content = mCursor.getString(contentIndex);
        picked_hour = mCursor.getLong(picked_hour_index);


        if (content != null) {
            holder.textView.setText(description + content);

        } else {
            holder.textView.setText(description);
        }


        long time = System.currentTimeMillis();

        if (picked_hour >= time) {

            setAlarm(picked_hour);
        }

    }

    private void setAlarm(long time) {


        Intent intent = new Intent(mContext, AlarmToastReceiver.class);
        final int _id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, _id , intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC, time, pendingIntent);


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
