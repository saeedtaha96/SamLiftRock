package com.samlifttruck.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.samlifttruck.R;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawer;
    NavigationView nav;
    ImageView btnMenu, imgvMenuKardex;
    Bundle savedInstanceState;
    LinearLayout btnShelfEdit, btnMidtermControl, btnReceiptList, btnDraftList, btnPermList, btnKardex;
    LinearLayout btnMenuProduct, btnMenuHavaleAzMojavez, btnMenuRegCount, btnMenuInventoryRep, btnMenuMidtermControl,
            btnMenuShelfEdit, btnMenuKardex, btnMenuPermList, btnMenuDraftList, btnMenuReceiptList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        setupView();
        setGlide();
        btnMidtermControl.setOnClickListener(this);
        btnShelfEdit.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnMenuProduct.setOnClickListener(this);
        btnDraftList.setOnClickListener(this);
        btnReceiptList.setOnClickListener(this);
        btnPermList.setOnClickListener(this);


    }


    private void setupView() {
        drawer = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);
        btnMenu = findViewById(R.id.toolbar_homepage_menu_btn);
        btnShelfEdit = findViewById(R.id.btn_shelf_edit);
        btnMidtermControl = findViewById(R.id.btn_midterm_control);
        btnMenuProduct = findViewById(R.id.btn_menu_product);
        btnMenuHavaleAzMojavez = findViewById(R.id.btn_menu_havale_az_mojavez);
        btnMenuRegCount = findViewById(R.id.btn_menu_register_counting);
        btnMenuInventoryRep = findViewById(R.id.btn_menu_inventory_report);
        btnMenuMidtermControl = findViewById(R.id.btn_menu_midterm_control);
        btnMenuShelfEdit = findViewById(R.id.btn_menu_shelf_edit);
        btnMenuKardex = findViewById(R.id.btn_menu_kardex);
        btnMenuPermList = findViewById(R.id.btn_menu_permission_list);
        btnMenuDraftList = findViewById(R.id.btn_menu_draft_list);
        btnMenuReceiptList = findViewById(R.id.btn_menu_receipt_list);
        btnReceiptList = findViewById(R.id.btn_list_receipt);
        btnDraftList = findViewById(R.id.btn_list_draft);
        btnPermList = findViewById(R.id.btn_list_permission);
        btnKardex = findViewById(R.id.btn_kardex);
    }

    private void setGlide() {
        /*setGlideMethod(R.drawable.kardex, R.id.img_menu_kardex);
        setGlideMethod(R.drawable.product, R.id.img_menu_product);
        setGlideMethod(R.drawable.shelfedit, R.id.img_menu_shelf_edit);
        setGlideMethod(R.drawable.control, R.id.img_menu_midterm_control);
        setGlideMethod(R.drawable.mojavez, R.id.img_menu_permission_list);
        setGlideMethod(R.drawable.havale, R.id.img_menu_draft_list);
        setGlideMethod(R.drawable.resid, R.id.img_menu_receipt_list);
        setGlideMethod(R.drawable.sodur, R.id.img_menu_havale_az_mojavez);
        setGlideMethod(R.drawable.shomaresh, R.id.img_menu_register_counting);
        setGlideMethod(R.drawable.ordering_icon, R.id.img_menu_inventory_report);*/
        setGlideMethod(R.drawable.sam_liftrock_2, R.id.img_sam_liftrock);
        setGlideMethod(R.drawable.shelfs, R.id.activitY_homepage_shelfedit_btn);
        setGlideMethod(R.drawable.midterm, R.id.activitY_homepage_midterm_btn);
        setGlideMethod(R.drawable.draft_new, R.id.activitY_homepage_draftlist_btn);
        setGlideMethod(R.drawable.mojavez2, R.id.activitY_homepage_permlist_btn);
        setGlideMethod(R.drawable.receipt_new, R.id.activitY_homepage_receiptlist_btn);
        setGlideMethod(R.drawable.cardx2, R.id.activitY_homepage_kardex_btn);
    }

    public void setGlideMethod(int drawable, int id) {
        ImageView img = findViewById(id);
        Glide.with(this).load(getDrawable(drawable)).into(img);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            //Midterm Control
            case R.id.btn_midterm_control:
                startActivity(new Intent(HomePageActivity.this, MidtermControlActivity.class));
                break;

            //Shelf Edit
            case R.id.btn_shelf_edit:
                startActivity(new Intent(HomePageActivity.this, ShelfEditActivity.class));
                break;

            //Menu
            case R.id.toolbar_homepage_menu_btn:
                if (!drawer.isDrawerOpen(GravityCompat.START))
                    drawer.openDrawer(GravityCompat.START);
                else drawer.closeDrawer(GravityCompat.START);

                break;

            //Product
            case R.id.btn_menu_product:

                startActivity(new Intent(HomePageActivity.this, ProdcutActivity.class));
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.btn_list_draft:

                startActivity(new Intent(HomePageActivity.this, DraftListActivity.class));
                break;
            case R.id.btn_list_permission:

                startActivity(new Intent(HomePageActivity.this, PermListActivity.class));
                break;
            case R.id.btn_list_receipt:

                startActivity(new Intent(HomePageActivity.this, ReceiptListActivity.class));
                break;
            default:
                ;


        }
    }


}
