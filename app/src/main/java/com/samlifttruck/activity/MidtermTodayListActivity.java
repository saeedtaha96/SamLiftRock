package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.MidtermTodayListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;

import ir.oveissi.materialsearchview.MaterialSearchView;

public class MidtermTodayListActivity extends AppCompatActivity {

    RecyclerView rvPermList;
    MidtermTodayListAdapter PermListAdapter;
    ImageButton btnSearch;
    MaterialSearchView msv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midterm_today_list);
        rvPermList = findViewById(R.id.rvqwerty);

        btnSearch = findViewById(R.id.activity_midterm_imgv_search);

        PermListAdapter = new MidtermTodayListAdapter(DataGenerator.getReceiptList());
        rvPermList.setLayoutManager(new GridLayoutManager(this, 2));
        //   rvDraftList.setItemAnimator(new DefaultItemAnimator());
        rvPermList.setAdapter(PermListAdapter);


    }

}
