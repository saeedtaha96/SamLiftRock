package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.DraftFromPermAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;

public class DraftFromPermActivity extends AppCompatActivity {

    RecyclerView rv;
    DraftFromPermAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_from_perm);

        rv = findViewById(R.id.activity_dfp_rv);
        adapter = new DraftFromPermAdapter(this, DataGenerator.getPermList());
        rv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rv.setAdapter(adapter);

    }

    public void onBackBtnClick(View view) {
        finish();
    }
}
