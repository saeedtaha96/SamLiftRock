package com.samlifttruck.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.navigation.NavigationView;
import com.samlifttruck.R;
import com.samlifttruck.activity.DataGenerators.Utility;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {
    private int workgroupID;
    private SharedPreferences pref;
    private DrawerLayout drawer;
    private NavigationView nav;
    TextView navTvLoginUserName;
    private ImageView btnMenu, imgvMenuKardex;
    private Bundle savedInstanceState;
    private LinearLayout btnShelfEdit, btnMidtermControl, btnReceiptList, btnDraftList, btnPermList, btnKardex;
    private LinearLayout btnMenuProduct, btnMenuHavaleAzMojavez, btnMenuRegCount, btnMenuInventoryRep, btnMenuMidtermControl,
            btnMenuShelfEdit, btnMenuKardex, btnMenuPermList, btnMenuDraftList, btnMenuReceiptList, btnMenuLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        pref = getSharedPreferences("myprefs", MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.putBoolean("isLogin", true);
        edt.apply();

        setupView();
        setGlide();

        setupListeners();

        if (pref.contains(Utility.LOGIN_USERNAME)) {
            navTvLoginUserName.setText(pref.getString(Utility.LOGIN_USERNAME, " "));
        }
        if (pref.contains(Utility.LOGIN_WORKGROUP_ID)) {
            workgroupID = pref.getInt(Utility.LOGIN_WORKGROUP_ID, 56);
        }

        if (workgroupID == 56) {
            setDisable(btnMenuProduct);
            setDisable(btnMenuRegCount);
            setDisable(btnMenuKardex);
            setDisable(btnMenuPermList);
            setDisable(btnMenuDraftList);
            setDisable(btnMenuReceiptList);
            setDisable(btnReceiptList);
            setDisable(btnDraftList);
            setDisable(btnPermList);
            setDisable(btnKardex);
        }


    }

    private void setDisable(LinearLayout ll) {
        ll.setEnabled(false);
        ll.setClickable(false);
        ll.setContextClickable(false);
        ll.setBackgroundColor(getColor(R.color.color_gray_border));
    }

    private void setupListeners() {
        btnMidtermControl.setOnClickListener(this);
        btnShelfEdit.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnMenuProduct.setOnClickListener(this);
        btnDraftList.setOnClickListener(this);
        btnReceiptList.setOnClickListener(this);
        btnPermList.setOnClickListener(this);
        btnKardex.setOnClickListener(this);
        btnMenuLogOut.setOnClickListener(this);
        btnMenuRegCount.setOnClickListener(this);
        btnMenuMidtermControl.setOnClickListener(this);
        btnMenuShelfEdit.setOnClickListener(this);
        btnMenuKardex.setOnClickListener(this);
        btnMenuPermList.setOnClickListener(this);
        btnMenuDraftList.setOnClickListener(this);
        btnMenuReceiptList.setOnClickListener(this);
    }


    private void setupView() {
        drawer = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav_view);
        btnMenu = findViewById(R.id.toolbar_homepage_menu_btn);
        btnShelfEdit = findViewById(R.id.btn_shelf_edit);
        btnMidtermControl = findViewById(R.id.btn_midterm_control);
        btnMenuProduct = findViewById(R.id.btn_menu_product);
        //  btnMenuHavaleAzMojavez = findViewById(R.id.btn_menu_havale_az_mojavez);
        btnMenuRegCount = findViewById(R.id.btn_menu_register_counting);
        //   btnMenuInventoryRep = findViewById(R.id.btn_menu_inventory_report);
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
        btnMenuLogOut = findViewById(R.id.btn_menu_exit);
        navTvLoginUserName = findViewById(R.id.nav_username_login_info);
    }

    private void setGlide() {
        setGlideMethod(R.drawable.sam_liftrock_2, R.id.img_sam_liftrock);
        setGlideMethod(R.drawable.ic_menu_shelf_edit, R.id.activitY_homepage_shelfedit_btn);
        setGlideMethod(R.drawable.ic_menu_midterm_control, R.id.activitY_homepage_midterm_btn);
        setGlideMethod(R.drawable.ic_menu_draft_list, R.id.activitY_homepage_draftlist_btn);
        setGlideMethod(R.drawable.ic_menu_perm_list, R.id.activitY_homepage_permlist_btn);
        setGlideMethod(R.drawable.ic_menu_receipt_list, R.id.activitY_homepage_receiptlist_btn);
        setGlideMethod(R.drawable.ic_menu_product_cardex, R.id.activitY_homepage_kardex_btn);
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

                startActivity(new Intent(HomePageActivity.this, ProductActivity.class));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                    }
                }, 300);

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
            case R.id.btn_kardex:

                startActivity(new Intent(HomePageActivity.this, CardexActivity.class));
                break;
            case R.id.btn_menu_exit:
                iOSDialogBuilder ios = Utility.newIOSdialog(HomePageActivity.this);
                ios.setTitle("هشدار");
                ios.setSubtitle(getString(R.string.txt_exit_app_dialog_message));
                ios.setPositiveListener("بله", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        SharedPreferences.Editor edt = pref.edit();
                        edt.putBoolean(Utility.IS_LOGIN, false);
                        edt.putString(Utility.LOGIN_USERNAME," ");
                        edt.putInt(Utility.LOGIN_WORKGROUP_ID,56);
                        edt.putInt(Utility.LOGIN_USER_ID,0);
                        edt.apply();
                        startActivity(new Intent(HomePageActivity.this, LoginActivity.class
                        ));
                        dialog.dismiss();
                        finish();
                    }
                });
                ios.setNegativeListener("خیر", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                });
                ios.build().show();
                break;
            case R.id.btn_menu_register_counting:
                startActivity(new Intent(HomePageActivity.this, CountingRegActivity.class));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                    }
                }, 300);
                break;

            case R.id.btn_menu_kardex:
                startActivity(new Intent(HomePageActivity.this, CardexActivity.class));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                    }
                }, 300);
                break;

            case R.id.btn_menu_draft_list:
                startActivity(new Intent(HomePageActivity.this, DraftListActivity.class));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                    }
                }, 300);

                break;

            case R.id.btn_menu_receipt_list:
                startActivity(new Intent(HomePageActivity.this, ReceiptListActivity.class));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                    }
                }, 300);

                break;

            case R.id.btn_menu_permission_list:
                startActivity(new Intent(HomePageActivity.this, PermListActivity.class));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                    }
                }, 300);

                break;

            case R.id.btn_menu_midterm_control:
                startActivity(new Intent(HomePageActivity.this, MidtermControlActivity.class));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                    }
                }, 300);

                break;

            case R.id.btn_menu_shelf_edit:
                startActivity(new Intent(HomePageActivity.this, ShelfEditActivity.class));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (drawer.isDrawerOpen(GravityCompat.START)) {
                            drawer.closeDrawer(GravityCompat.START);
                        }
                    }
                }, 300);

                break;
            default:


        }
    }


}
