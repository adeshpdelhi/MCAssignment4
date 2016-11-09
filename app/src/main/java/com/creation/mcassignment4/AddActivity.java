package com.creation.mcassignment4;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private TaskDbHelper mTaskDbHelper;
    private EditText mNewTitle;
    private EditText mNewDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mNewTitle = (EditText) findViewById(R.id.add_title);
        mNewDescription = (EditText) findViewById(R.id.add_description);
        mTaskDbHelper = new TaskDbHelper(getApplicationContext());
    }

    public void addNewTaskClick(View view){
        Task newTask = new Task(mNewTitle.getText().toString(),mNewDescription.getText().toString());
        addTask(newTask);
        finish();
    }

    public void addTask(Task task){
        SQLiteDatabase taskDb = mTaskDbHelper.getWritableDatabase();
        ContentValues newTaskValues = new ContentValues();
        newTaskValues.put(TaskContract.Task.UUID,task.getId().toString());
        newTaskValues.put(TaskContract.Task.TITLE,task.getTitle());
        newTaskValues.put(TaskContract.Task.DESCRIPTION,task.getDescription());
        taskDb.insert(TaskContract.Task.TABLE,null, newTaskValues);
        Toast.makeText(this,"Task added successfully!",Toast.LENGTH_SHORT).show();
        taskDb.close();
    }

}
