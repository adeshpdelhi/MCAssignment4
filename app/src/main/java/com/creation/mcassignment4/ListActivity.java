package com.creation.mcassignment4;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import com.creation.mcassignment4.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";
    private TaskDbHelper mTaskDbHelper;
    private RecyclerView mListRecyclerView;
    private TaskAdapter mTaskAdapter;
    private ArrayList<Task> mTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mTaskDbHelper = new TaskDbHelper(getApplicationContext());
        mListRecyclerView = (RecyclerView) findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTasks = new ArrayList();
        mTaskAdapter = new TaskAdapter(mTasks);
        mListRecyclerView.setAdapter(mTaskAdapter);
        updateListAndUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListAndUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.add_action:
                startActivity(new Intent(this, AddActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void updateListAndUI(){
        updateTasksList();
        mTaskAdapter.notifyDataSetChanged();
    }

    private class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView mTitleView;
        public TaskViewHolder(View itemView){
            super(itemView);
            mTitleView = (TextView) itemView.findViewById(R.id.list_title);
        }

        void setTitle(String title){
            mTitleView.setText(title);
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>{
        private ArrayList<Task> mTasks;
        public TaskAdapter(ArrayList<Task> tasks){
            mTasks = tasks;
        }

        @Override
        public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view = layoutInflater.inflate(R.layout.list_item_task, parent, false);
            return new TaskViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskViewHolder holder, int position) {
            holder.setTitle(mTasks.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

}
