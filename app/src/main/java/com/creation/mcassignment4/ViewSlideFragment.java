package com.creation.mcassignment4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ViewSlideFragment extends Fragment {

    private Task currentTask;
    private TextView mTitleView;
    private TextView mDescriptionView;

    public ViewSlideFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentTask = (Task)getArguments().getSerializable("TASK");
        View v = inflater.inflate(R.layout.fragment_view_slide, container, false);
        mTitleView = (TextView)v.findViewById(R.id.view_title);
        mDescriptionView = (TextView)v.findViewById(R.id.view_description);
        mTitleView.setText(currentTask.getTitle());
        mDescriptionView.setText(currentTask.getDescription());
        return v;
    }

}
