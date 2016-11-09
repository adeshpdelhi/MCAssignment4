package com.creation.mcassignment4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.UUID;

/**
 * Created by adesh on 11/7/16.
 */
public class Task {
    private UUID mId;
    private String mTitle, mDescription;
    public Task(String title, String description){
        mId = UUID.randomUUID();
        mTitle = title;
        mDescription = description;
    }

    public Task(String id, String title, String description){
        mId = UUID.fromString(id);
        mTitle = title;
        mDescription = description;
    }

    public String getId() {
        return mId.toString();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }




}
