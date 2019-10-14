package com.samlifttruck.activity.DataGenerators;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.samlifttruck.R;

public class CustomProgressBar {

    AppCompatActivity activity;
    private View pbar;
    public CustomProgressBar(@NonNull AppCompatActivity activty){
        pbar = activty.findViewById(R.id.layout_loading_frame_container);
    }
    public void showProgress(){
        pbar.bringToFront();
        pbar.setVisibility(View.VISIBLE);
    }

    public void dismissProgress(){
        pbar.setVisibility(View.GONE);
    }

}

