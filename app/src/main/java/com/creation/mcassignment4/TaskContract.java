package com.creation.mcassignment4;

import android.provider.BaseColumns;

/**
 * Created by adesh on 11/7/16.
 */
final class TaskContract {
    private TaskContract(){}

    public static class Task{
        public static final String TABLE = "Task";
        public static final String UUID = "UUID";
        public static final String TITLE = "Title";
        public static final String DESCRIPTION = "Description";

    }
}
