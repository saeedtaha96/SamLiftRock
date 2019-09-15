package com.samlifttruck.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Adapters.CountingRegListAdapter;
import com.samlifttruck.activity.DataGenerators.DataGenerator;

import ir.oveissi.materialsearchview.MaterialSearchView;

public class CountingRegListActivity extends AppCompatActivity {

    RecyclerView rv;
    CountingRegListAdapter adapter;
    ImageButton btnSearch;
    MaterialSearchView msv;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_reg_list);

        setToolbarText();

        rv = findViewById(R.id.activity_counting_reg_list_recyclerview);
        btnSearch = findViewById(R.id.activity_midterm_imgv_search);
        msv = findViewById(R.id.searchview_material);
        btnConfirm = findViewById(R.id.activity_counting_reg_list_btn_confirm);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msv.showSearch();
            }
        });
      //  Configuration config = getResources().getConfiguration();
       // if (config.smallestScreenWidthDp >= 600) {
            // sw600dp code goes here
       //     rv.setLayoutManager(new GridLayoutManager(this, 2));
       // } else {
            // fall-back code goes here
            rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
      //  }
        adapter = new CountingRegListAdapter(DataGenerator.getDraftList());
        rv.setAdapter(adapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scrolling up
                    btnConfirm.animate().translationY(300f).setDuration(300);
                } else {
                    btnConfirm.animate().translationY(0).setDuration(200);
                }
            }
        });

    }

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.txt_counting_reg_list));
    }

    public void onBackBtnClick(View view) {
        finish();
    }
}
