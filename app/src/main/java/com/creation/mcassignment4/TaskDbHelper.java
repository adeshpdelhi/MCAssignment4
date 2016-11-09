package com.creation.mcassignment4;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.creation.mcassignment4.TaskContract.Task;

import java.util.UUID;

public class TaskDbHelper extends SQLiteOpenHelper {
    private static final int DatabaseVersion = 1;
    private static final String DatabaseName = "Task.db";
    private final String createDbEntries = "create table "+ Task.TABLE+ "( "+
            Task.UUID+" text, "+ Task.TITLE+" text, "+Task.DESCRIPTION+" text);";


    private final String deleteDbEntries = "drop table if exists "+Task.TABLE+" ;";

    public TaskDbHelper(Context context) {
        super(context,DatabaseName,null ,DatabaseVersion);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.v("Helper",createDbEntries);
        db.execSQL(createDbEntries);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(deleteDbEntries);
        onCreate(db);
    }

    public Task getTask(UUID Id){
        return null;
    }



}
