package com.samlifttruck.activity.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samlifttruck.R;
import com.samlifttruck.activity.DataGenerators.Utility;
import com.samlifttruck.activity.Models.ReceiptListModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MidtermTodayListAdapter extends RecyclerView.Adapter<MidtermTodayListAdapter.MyViewHolder> {

    private List<ReceiptListModel> list;
    Context context;
    private int workgroupID;
    private SharedPreferences pref;

    public MidtermTodayListAdapter(Context context, List<ReceiptListModel> draft) {
        this.context = context;
        this.list = (draft == null) ? new ArrayList<ReceiptListModel>() : draft;
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
        holder.bind(list.get(position));
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
        return list.size();
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

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_Inventory = itemView.findViewById(R.id.relative_midterm_today_list_linear);
            shomareFanni = itemView.findViewById(R.id.activity_today_list_tv_shomare_fanni);
            productName = itemView.findViewById(R.id.activity_today_list_tv_product_name);
            inventory = itemView.findViewById(R.id.activity_today_list_inventory);
            currCount = itemView.findViewById(R.id.activity_today_list_current_counting);
            btnReject = itemView.findViewById(R.id.today_list_reject);
        }

        void bind(final ReceiptListModel item) {


            shomareFanni.setText(item.getCondition());
            productName.setText(item.getReceiptNum());
            inventory.setText(item.getReceiptType());
            currCount.setText(item.getDate());

            if (workgroupID == 56) {
                ll_Inventory.setVisibility(View.GONE);
            }

            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), list.size());
                }
            });
        }
    }
}