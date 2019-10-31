package com.samlifttruck.activity.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.samlifttruck.R;
import com.samlifttruck.activity.Utility.SoapCall;
import com.samlifttruck.activity.Utility.Utility;
import com.samlifttruck.activity.Models.MidtermControlModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class MidtermTodayListAdapter extends RecyclerView.Adapter<MidtermTodayListAdapter.MyViewHolder> {

    List<MidtermControlModel> midlist;
    Context context;
    private int workgroupID;
    private SharedPreferences pref;
    List<JSONObject> list = null;

    public MidtermTodayListAdapter(Context context, List<MidtermControlModel> mylist) {
        this.context = context;
        this.midlist = (mylist == null) ? new ArrayList<MidtermControlModel>() : mylist;
        pref = context.getSharedPreferences("myprefs", MODE_PRIVATE);
        if (pref.contains(Utility.LOGIN_WORKGROUP_ID)) {
            workgroupID = pref.getInt(Utility.LOGIN_WORKGROUP_ID, 56);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_midterm_today_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(midlist.get(position));
        //  Configuration config = context.getResources().getConfiguration();
        //  if (config.smallestScreenWidthDp >= 600) {
        // sw600dp code goes here
        setFadeAnimation(holder.itemView);
        //   } else {
        // fall-back code goes here
        //    setScaleAnimation(holder.itemView);
        //  }
    }

    @Override
    public int getItemCount() {
        return midlist.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);
        anim.setDuration(800);
        view.startAnimation(anim);
    }

    // private void setScaleAnimation(View view) {
    //    ScaleAnimation anim = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    //     anim.setDuration(700);
    //    view.startAnimation(anim);
    // }

    // View Holder //
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView shomareFanni, productName, inventory, currCount;
        LinearLayout ll_Inventory;
        Button btnReject;
        int productCode;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_Inventory = itemView.findViewById(R.id.relative_midterm_today_list_linear);
            shomareFanni = itemView.findViewById(R.id.activity_today_list_tv_shomare_fanni);
            productName = itemView.findViewById(R.id.activity_today_list_tv_product_name);
            inventory = itemView.findViewById(R.id.activity_today_list_inventory);
            currCount = itemView.findViewById(R.id.activity_today_list_current_counting);
            btnReject = itemView.findViewById(R.id.today_list_reject);
        }

        void bind(final MidtermControlModel item) {

            shomareFanni.setText(item.getTechNo());
            productName.setText(item.getProductName());
            inventory.setText(item.getInventory());
            currCount.setText(item.getCurrCount());
            productCode = item.getProductCode();


            if (workgroupID == 56) {
                ll_Inventory.setVisibility(View.GONE);
            }

            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iOSDialogBuilder ios = Utility.newIOSdialog(context);
                    ios.setTitle(item.getTechNo())
                            .setSubtitle("اطمینان از حذف شماره فنی فوق دارید؟")
                            .setPositiveListener("بله", new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {
                                    deleteCycleCountItem(productCode, getAdapterPosition());
                                    dialog.dismiss();
                                }
                            }).setNegativeListener("خیر", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            dialog.dismiss();
                        }
                    }).build().show();


                }
            });
        }
    }

    public void deleteCycleCountItem(int productCode, final int position) {
        PropertyInfo p0 = new PropertyInfo();
        p0.setName("passCode");
        p0.setValue(Utility.pw);
        p0.setType(String.class);

        PropertyInfo p1 = new PropertyInfo();
        p1.setName("productCode");
        p1.setValue(productCode);
        p1.setType(Integer.class);

        final SoapCall ss = new SoapCall((AppCompatActivity) context, SoapCall.METHOD_DELETE_CYCLE_COUNT);
        ss.execute(p0, p1);


        SoapCall.execute(new Runnable() {
            @Override
            public void run() {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                try {
                    list = ss.get();
                    AppCompatActivity activity = (AppCompatActivity) context;
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (list != null) {
                                    try {
                                        if (list.get(0).getString("boolean").equals("true")) {
                                            Toast.makeText(context, "مورد با موفقیت حذف شد", Toast.LENGTH_LONG).show();
                                            midlist.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, midlist.size());
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(context, "خطا در حذف آیتم مورد نظر", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}