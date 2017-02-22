package com.example.to;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.to.data.Contract;

/**
 * Created by lenovo on 2/22/2017.
 */

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


        int idIndex = mCursor.getColumnIndex(Contract.ToDoEntry._ID);
        int descriptionIndex = mCursor.getColumnIndex(Contract.ToDoEntry.COLUMN_TITLE);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
     //   final int id = mCursor.getInt(idIndex);
        String description = mCursor.getString(descriptionIndex);
      //  int priority = mCursor.getInt(priorityIndex);

        //Set values
        //   holder.itemView.setTag(id);
        //   holder.taskDescriptionView.setText(description);
        holder.textView.setText(description);

        // Programmatically set the text and color for the priority TextView
     //   String priorityString = "" + priority; // converts int to String
     //   holder.priorityView.setText(priorityString);

     //   GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
     //   int priorityColor = getPriorityColor(priority);
    //    priorityCircle.setColor(priorityColor);

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