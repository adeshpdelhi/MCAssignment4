package com.creation.mcassignment4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ViewSlideActivity extends AppCompatActivity {
    private static final String TAG = "ViewSlideActivity";
    private String EXTRA_TASK_ID = "TASK_ID";
    private Task task;
    private TaskDbHelper mTaskDbHelper;
    private ArrayList<Task> mTasks;
    private int Index = 0;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slide);
        mTaskDbHelper = new TaskDbHelper(getApplication());
        String ID=getIntent().getStringExtra(EXTRA_TASK_ID);
        mTasks = new ArrayList();
        updateTasksList();
        for(int i=0;i<mTasks.size();i++)
        {
            if(mTasks.get(i).getId().equals(ID))
            {
                Index = i;
                break;
            }
        }
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new ViewSlidePagerAdapter(getSupportFragmentManager(), mTasks);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(Index);
    }


    private void updateTasksList(){
        SQLiteDatabase taskDb = mTaskDbHelper.getReadableDatabase();
        String columns[]={ TaskContract.Task.UUID, TaskContract.Task.TITLE, TaskContract.Task.DESCRIPTION };
        Cursor c = taskDb.query(TaskContract.Task.TABLE, columns, null, null,null,null, null );
        if(c!=null & c.moveToFirst()){
            mTasks.clear();
            while(!c.isAfterLast()) {
                String id = c.getString(
                        c.getColumnIndexOrThrow(TaskContract.Task.UUID)
                );
                String title = c.getString(
                        c.getColumnIndexOrThrow(TaskContract.Task.TITLE)
                );
                String description = c.getString(
                        c.getColumnIndexOrThrow(TaskContract.Task.DESCRIPTION)
                );
                mTasks.add(new Task(id,title,description));
                c.moveToNext();
            }
        }
        else
            Log.e(TAG, "Error reading database in getTasks");
        c.close();

    }

    private class ViewSlidePagerAdapter extends FragmentStatePagerAdapter{
        private ArrayList<Task> mTasks;
        public ViewSlidePagerAdapter(FragmentManager fragmentManager, ArrayList<Task> tasksList){
            super(fragmentManager);
            mTasks = tasksList;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle b = new Bundle();
            b.putSerializable("TASK", mTasks.get(position));
            ViewSlideFragment fragment = new ViewSlideFragment();
            fragment.setArguments(b);
            return fragment;
        }

        @Override
        public int getCount() {
            return mTasks.size();
        }

    }

    private Task getTask(String ID){
        Task newTask = null;
        SQLiteDatabase taskDb = mTaskDbHelper.getReadableDatabase();
        String columns[]={ TaskContract.Task.UUID, TaskContract.Task.TITLE, TaskContract.Task.DESCRIPTION };
        String selection = TaskContract.Task.UUID + " = ?";
        String[] args = { ID };
        Cursor c = taskDb.query(TaskContract.Task.TABLE, columns, selection, args,null,null, null );
        if(c!=null & c.moveToFirst()){

                String id = c.getString(
                        c.getColumnIndexOrThrow(TaskContract.Task.UUID)
                );
                String title = c.getString(
                        c.getColumnIndexOrThrow(TaskContract.Task.TITLE)
                );
                String description = c.getString(
                        c.getColumnIndexOrThrow(TaskContract.Task.DESCRIPTION)
                );
            newTask = new Task(id,title,description);
        }
        else
            Log.e(TAG, "Error reading database in getTasks");
        c.close();
    return newTask;
    }


}
