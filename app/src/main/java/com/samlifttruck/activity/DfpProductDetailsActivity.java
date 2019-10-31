package com.samlifttruck.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.samlifttruck.R;
import com.samlifttruck.activity.Fragments.DfpProductsListFragment;

public class DfpProductDetailsActivity extends AppCompatActivity {
    int businessId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dfp_product_details);

        setToolbarText();


        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("businessId")) {
                businessId = getIntent().getExtras().getInt("businessId");
            }
        }
        DfpProductsListFragment fragment = DfpProductsListFragment.newInstance(businessId);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_dfp_products_container, fragment).commit();


    }

    private void setToolbarText() {
        TextView tvAppbar = findViewById(R.id.toolbar_text);
        tvAppbar.setText(getString(R.string.txt_products));
    }
}
